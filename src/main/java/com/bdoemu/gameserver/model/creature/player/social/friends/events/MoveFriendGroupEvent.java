package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMMoveFriendGroup;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;

public class MoveFriendGroupEvent implements IFriendEvent {
    private Player player;
    private long friendObjectId;
    private int groupId;
    private FriendHandler handler;
    private Friend friend;

    public MoveFriendGroupEvent(final Player player, final long friendObjectId, final int groupId) {
        this.player = player;
        this.friendObjectId = friendObjectId;
        this.groupId = groupId;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        this.friend.setGroupId(this.groupId);
        FriendsDBCollection.getInstance().update(this.friend);
        this.player.sendPacket(new SMMoveFriendGroup(this.friendObjectId, this.groupId));
    }

    @Override
    public boolean canAct() {
        this.friend = this.handler.getFriendMap().get(this.friendObjectId);
        return this.friend != null && this.friend.getFriendType().isMyFriend() && (this.groupId == -1 || this.handler.getGroupMap().containsKey(this.groupId));
    }
}
