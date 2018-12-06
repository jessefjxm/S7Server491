package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAddFriend;
import com.bdoemu.core.network.sendable.SMAcceptAddFriend;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNewRequestFriend;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.databaseCollections.FriendsRequestDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendReq;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;
import com.bdoemu.gameserver.worldInstance.World;

public class AddFriendEvent implements IFriendEvent {
    private Player player;
    private String nameOrFamily;
    private FriendHandler handler;
    private Long friendAccountId;
    private Player playerFriend;
    private boolean hasMeOnFriend;
    private Friend myFriend;
    private boolean isName;

    public AddFriendEvent(final Player player, final String nameOrFamily, final boolean isName) {
        this.hasMeOnFriend = false;
        this.player = player;
        this.nameOrFamily = nameOrFamily;
        this.handler = player.getFriendHandler();
        this.isName = isName;
    }

    @Override
    public void onEvent() {
        if (!this.hasMeOnFriend) {
            final FriendReq friendReq = new FriendReq(this.friendAccountId, this.player.getAccountId());
            if (FriendsRequestDBCollection.getInstance().save(friendReq) && this.playerFriend != null) {
                this.playerFriend.getFriendHandler().getFriendsReqMap().put(friendReq.getReqAccountId(), friendReq);
                this.playerFriend.sendPacket(new SMNewRequestFriend());
            }
        } else {
            if (this.myFriend == null) {
                this.myFriend = new Friend(this.player.getAccountId(), this.playerFriend, this.friendAccountId, EFriendType.MyFriend, -1);
                if (FriendsDBCollection.getInstance().save(this.myFriend)) {
                    this.player.sendPacket(new SMAcceptAddFriend(this.myFriend));
                }
            } else {
                this.myFriend.setFriendType(EFriendType.MyFriend);
                this.myFriend.setGroupId(-1);
                FriendsDBCollection.getInstance().update(this.myFriend);
                this.player.sendPacket(new SMAcceptAddFriend(this.myFriend));
            }
            this.handler.getFriendMap().put(this.friendAccountId, this.myFriend);
        }
    }

    @Override
    public boolean canAct() {
        if (this.nameOrFamily.isEmpty()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoSearchCharacter, CMAddFriend.class));
            return false;
        }
        if (this.isName) {
            this.friendAccountId = PlayersDBCollection.getInstance().getAccountId(this.nameOrFamily);
            if (this.friendAccountId == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoSearchCharacter, CMAddFriend.class));
                return false;
            }
        } else {
            this.friendAccountId = AccountsDBCollection.getInstance().getAccountIdByFamily(this.nameOrFamily);
            if (this.friendAccountId == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoSearchCharacter, CMAddFriend.class));
                return false;
            }
        }
        if (this.handler.hasFriend(this.friendAccountId)) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoSearchCharacter, CMAddFriend.class));
            return false;
        }
        if (FriendsRequestDBCollection.getInstance().hasFriendReq(this.friendAccountId, this.player.getAccountId())) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoFriendAlreadyAdd, CMAddFriend.class));
            return false;
        }
        this.playerFriend = World.getInstance().getPlayerByAccount(this.friendAccountId);
        this.hasMeOnFriend = FriendsDBCollection.getInstance().hasFriend(this.friendAccountId, this.player.getAccountId());
        this.myFriend = this.handler.getFriendMap().get(this.friendAccountId);
        return true;
    }
}
