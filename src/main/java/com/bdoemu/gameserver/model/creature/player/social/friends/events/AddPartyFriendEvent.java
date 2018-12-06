package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMNewFriend;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;

public class AddPartyFriendEvent implements IFriendEvent {
    private Player player;
    private Player playerFriend;
    private FriendHandler handler;

    public AddPartyFriendEvent(final Player player, final Player playerFriend) {
        this.player = player;
        this.playerFriend = playerFriend;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        final Friend partyFriend = new Friend(this.player.getAccountId(), this.playerFriend, this.playerFriend.getAccountId(), EFriendType.Party, -2);
        FriendsDBCollection.getInstance().save(partyFriend);
        this.handler.getFriendMap().put(partyFriend.getFriendAccountId(), partyFriend);
        this.player.sendPacket(new SMNewFriend());
    }

    @Override
    public boolean canAct() {
        return !this.handler.getFriendMap().containsKey(this.playerFriend.getAccountId()) && !this.playerFriend.getFriendHandler().hasReqFriend(this.player.getAccountId());
    }
}
