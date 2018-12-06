package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMNewFriend;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;

public class PutFriendEvent implements IFriendEvent {
    private Player owner;
    private Player player;
    private Friend meOnFriend;

    public PutFriendEvent(final Player owner, final Player player) {
        this.owner = owner;
        this.player = player;
    }

    @Override
    public void onEvent() {
        if (this.meOnFriend == null) {
            this.meOnFriend = new Friend(this.owner.getAccountId(), this.player, this.player.getAccountId(), EFriendType.MyFriend, -1);
            FriendsDBCollection.getInstance().save(this.meOnFriend);
            this.owner.getFriendHandler().getFriendMap().put(this.meOnFriend.getFriendAccountId(), this.meOnFriend);
            this.owner.sendPacket(new SMNewFriend());
        } else {
            this.meOnFriend.setFriendType(EFriendType.MyFriend);
            this.meOnFriend.setGroupId(-1);
            FriendsDBCollection.getInstance().update(this.meOnFriend);
            this.owner.sendPacket(new SMNewFriend());
        }
    }

    @Override
    public boolean canAct() {
        this.meOnFriend = this.owner.getFriendHandler().getFriendMap().get(this.player.getAccountId());
        return true;
    }
}
