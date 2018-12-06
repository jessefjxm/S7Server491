package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptAddFriend;
import com.bdoemu.core.network.sendable.SMAcceptAddFriend;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.databaseCollections.FriendsRequestDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendReq;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;
import com.bdoemu.gameserver.worldInstance.World;

public class AcceptAddFriendEvent implements IFriendEvent {
    private Player player;
    private long accountId;
    private FriendHandler handler;
    private FriendReq friendReq;
    private Friend myFriend;

    public AcceptAddFriendEvent(final Player player, final long accountId) {
        this.player = player;
        this.accountId = accountId;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        FriendsRequestDBCollection.getInstance().delete(this.friendReq.getObjectId());
        Player playerFriend = World.getInstance().getPlayerByAccount(this.friendReq.getReqAccountId());
        if (this.myFriend == null) {
            this.myFriend = new Friend(this.player.getAccountId(), playerFriend, this.friendReq.getReqAccountId(), EFriendType.MyFriend, -1);
            this.handler.getFriendMap().put(this.friendReq.getReqAccountId(), this.myFriend);
            FriendsDBCollection.getInstance().save(this.myFriend);
            this.player.sendPacket(new SMAcceptAddFriend(this.myFriend));
        } else {
            this.myFriend.setFriendType(EFriendType.MyFriend);
            this.myFriend.setGroupId(-1);
            FriendsDBCollection.getInstance().update(this.myFriend);
            this.player.sendPacket(new SMAcceptAddFriend(this.myFriend));
        }
        if (playerFriend != null) {
            playerFriend.getFriendHandler().onEvent(new PutFriendEvent(playerFriend, this.player));
        } else {
            final Friend friend = FriendsDBCollection.getInstance().loadFriend(this.friendReq.getReqAccountId(), this.player.getAccountId());
            if (friend != null && !friend.getFriendType().isMyFriend()) {
                friend.setFriendType(EFriendType.MyFriend);
                friend.setGroupId(-1);
                FriendsDBCollection.getInstance().update(friend);
            } else {
                final Friend meOnFriend = new Friend(this.friendReq.getReqAccountId(), this.player, this.player.getAccountId(), EFriendType.MyFriend, -1);
                FriendsDBCollection.getInstance().save(meOnFriend);
            }
        }
    }

    @Override
    public boolean canAct() {
        if (this.handler.hasFriend(this.accountId)) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoFriendNotMoreAdd, CMAcceptAddFriend.class));
            return false;
        }
        this.myFriend = this.handler.getFriendMap().get(this.accountId);
        this.friendReq = this.handler.getFriendsReqMap().remove(this.accountId);
        return this.friendReq != null;
    }
}
