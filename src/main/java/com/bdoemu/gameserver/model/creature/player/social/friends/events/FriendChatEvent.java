package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMFriendChat;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;

public class FriendChatEvent implements IFriendEvent {
    private Player player;
    private Player playerFriend;
    private long friendAccountId;
    private String message;
    private FriendHandler handler;

    public FriendChatEvent(final Player player, final long friendAccountId, final String message) {
        this.player = player;
        this.friendAccountId = friendAccountId;
        this.message = message;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        this.player.sendPacket(new SMFriendChat(this.player.getAccountId(), this.message));
        if (this.playerFriend != null) {
            this.playerFriend.sendPacket(new SMFriendChat(this.player.getAccountId(), this.message));
        }
    }

    @Override
    public boolean canAct() {
        Friend friend = this.handler.getFriendMap().get(this.friendAccountId);
        if (friend == null) {
            return false;
        }
        this.playerFriend = friend.getFriend();
        return this.playerFriend != null && this.playerFriend.getFriendHandler().hasFriend(this.player.getAccountId());
    }
}
