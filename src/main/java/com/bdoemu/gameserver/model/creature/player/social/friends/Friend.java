package com.bdoemu.gameserver.model.creature.player.social.friends;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Friend extends JSONable {
    private final long accountId;
    private final long friendAccountId;
    private final long objectId;
    private EFriendType friendType;
    private long loginDate;
    private long logoutDate;
    private Player friend;
    private int groupId;

    public Friend(final long accountId, final Player friend, final long friendAccountId, final EFriendType friendType, final int groupId) {
        this.groupId = -1;
        this.accountId = accountId;
        this.friendAccountId = friendAccountId;
        this.friend = friend;
        this.friendType = friendType;
        this.groupId = groupId;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Friend);
        if (friend != null) {
            this.loginDate = friend.getAccountData().getLastLogIn();
            this.logoutDate = friend.getAccountData().getLastLogout();
        } else {
            final BasicDBObject timeDBObject = AccountsDBCollection.getInstance().getAccountTimeById(friendAccountId);
            this.logoutDate = timeDBObject.getLong("lastLogout");
            this.loginDate = timeDBObject.getLong("lastLogin");
        }
    }

    public Friend(final BasicDBObject dbObject) {
        this.groupId = -1;
        this.objectId = dbObject.getLong("_id");
        this.accountId = dbObject.getLong("accountId");
        this.friendAccountId = dbObject.getLong("friendAccountId");
        this.groupId = dbObject.getInt("groupId");
        this.friendType = EFriendType.valueOf(dbObject.getString("friendType"));
        this.friend = World.getInstance().getPlayerByAccount(this.friendAccountId);
        if (this.friend != null) {
            this.loginDate = this.friend.getAccountData().getLastLogIn();
            this.logoutDate = this.friend.getAccountData().getLastLogout();
        } else {
            final BasicDBObject timeDBObject = AccountsDBCollection.getInstance().getAccountTimeById(this.friendAccountId);
            this.logoutDate = timeDBObject.getLong("lastLogout");
            this.loginDate = timeDBObject.getLong("lastLogin");
        }
    }

    public long getObjectId() {
        return this.objectId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(final int groupId) {
        this.groupId = groupId;
    }

    public long getLoginDate() {
        return this.loginDate;
    }

    public long getLogoutDate() {
        return this.logoutDate;
    }

    public EFriendType getFriendType() {
        return this.friendType;
    }

    public void setFriendType(final EFriendType friendType) {
        this.friendType = friendType;
    }

    public void onLoginFriend(final Player player) {
        this.friend = player;
        this.loginDate = GameTimeService.getServerTimeInMillis();
    }

    public void onLogoutFriend() {
        this.friend = null;
        this.logoutDate = GameTimeService.getServerTimeInMillis();
    }

    public Player getFriend() {
        return this.friend;
    }

    public int getMaxWp() {
        final Player player = this.getFriend();
        return (player == null) ? -1 : player.getMaxWp();
    }

    public int getMaxContribution() {
        final Player player = this.getFriend();
        return (player == null) ? -1 : player.getExplorePointHandler().getMainExplorePoint().getMaxExplorePoints();
    }

    public long getFriendAccountId() {
        return this.friendAccountId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public String getName() {
        final Player player = this.getFriend();
        return (player == null) ? "" : player.getName();
    }

    public String getFamilyName() {
        return FamilyService.getInstance().getFamily(this.friendAccountId);
    }

    public int getLevel() {
        final Player player = this.getFriend();
        return (player == null) ? 0 : player.getLevel();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("groupId", this.groupId);
        builder.append("friendAccountId", this.friendAccountId);
        builder.append("friendType", this.friendType.name());
        return builder.get();
    }
}
