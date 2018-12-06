package com.bdoemu.gameserver.model.creature.player.social.friends;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.databaseCollections.FriendsRequestDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.IFriendEvent;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FriendHandler extends JSONable {
    private final CloseableReentrantLock lock;
    private final ConcurrentHashMap<Long, Friend> myFriends;
    private final HashMap<Long, FriendReq> friendsReq;
    private final HashMap<Integer, FriendGroup> groups;
    private boolean hasNewReq;

    public FriendHandler(final long lastLogoutTime, final long accountId) {
        this.lock = new CloseableReentrantLock();
        this.myFriends = new ConcurrentHashMap<>();
        this.friendsReq = new HashMap<>();
        this.groups = new HashMap<>();
        this.hasNewReq = false;
        final List<Friend> _friends = FriendsDBCollection.getInstance().loadFriends(accountId);
        for (final Friend friend : _friends) {
            this.myFriends.put(friend.getFriendAccountId(), friend);
        }
        final List<FriendReq> _friendsReq = FriendsRequestDBCollection.getInstance().loadFriendsReq(accountId);
        for (final FriendReq friendReq : _friendsReq) {
            if (friendReq.getRecievedTime() > lastLogoutTime) {
                this.hasNewReq = true;
            }
            this.friendsReq.put(friendReq.getReqAccountId(), friendReq);
        }
    }

    public FriendHandler(final long lastLogOutDate, final long accountId, final BasicDBObject dbObject) {
        this(lastLogOutDate, accountId);
        final BasicDBList friendGroupDB = (BasicDBList) dbObject.get("friendGroup");
        for (final Object aFriendGroupDB : friendGroupDB) {
            final FriendGroup friendGroup = new FriendGroup((BasicDBObject) aFriendGroupDB);
            this.groups.put(friendGroup.getGroupId(), friendGroup);
        }
    }

    public ConcurrentHashMap<Long, Friend> getFriendMap() {
        return this.myFriends;
    }

    public Collection<Long> getFriendAccounts() {
        return Collections.list(this.myFriends.keys());
    }

    public void onLogOutFriend(final Player player) {
        final Friend friend = this.myFriends.get(player.getAccountId());
        if (friend != null) {
            friend.onLogoutFriend();
        }
    }

    public void onLogInFriend(final Player player) {
        final Friend friend = this.myFriends.get(player.getAccountId());
        if (friend != null) {
            friend.onLoginFriend(player);
        }
    }

    public boolean hasNewReq() {
        return this.hasNewReq;
    }

    public Collection<Friend> getFriends() {
        if (this.myFriends.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.myFriends.values());
    }

    public boolean hasFriend(final long accountId) {
        final Friend friend = this.myFriends.get(accountId);
        return friend != null && friend.getFriendType().isMyFriend();
    }

    public boolean hasReqFriend(final long accountId) {
        return this.friendsReq.containsKey(accountId);
    }

    public HashMap<Long, FriendReq> getFriendsReqMap() {
        return this.friendsReq;
    }

    public boolean hasGroup(final int groupId) {
        return this.groups.containsKey(groupId);
    }

    public HashMap<Integer, FriendGroup> getGroupMap() {
        return this.groups;
    }

    public Collection<FriendReq> getFriendsReqList() {
        if (this.friendsReq.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.friendsReq.values());
    }

    public Collection<FriendGroup> getGroupsList() {
        if (this.groups.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.groups.values());
    }

    public void onEvent(final IFriendEvent event) {
        try (final CloseableReentrantLock tempLock = this.lock.open()) {
            if (event.canAct()) {
                event.onEvent();
            }
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList friendGroupDB = new BasicDBList();
        for (final FriendGroup friendGroup : this.groups.values()) {
            friendGroupDB.add(friendGroup.toDBObject());
        }
        builder.append("friendGroup", friendGroupDB);
        return builder.get();
    }
}
