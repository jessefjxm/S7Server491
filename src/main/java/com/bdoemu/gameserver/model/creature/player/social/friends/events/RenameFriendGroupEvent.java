package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMRenameFriendGroup;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendGroup;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;

public class RenameFriendGroupEvent implements IFriendEvent {
    private Player player;
    private int groupId;
    private String name;
    private FriendHandler handler;
    private FriendGroup friendGroup;

    public RenameFriendGroupEvent(final Player player, final int groupId, final String name) {
        this.player = player;
        this.groupId = groupId;
        this.name = name;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        this.friendGroup.setName(this.name);
        this.player.sendPacket(new SMRenameFriendGroup(this.friendGroup));
    }

    @Override
    public boolean canAct() {
        this.friendGroup = this.handler.getGroupMap().get(this.groupId);
        return this.friendGroup != null;
    }
}
