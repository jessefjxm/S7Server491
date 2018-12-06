package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAddFriendGroup;
import com.bdoemu.core.network.sendable.SMAddFriendGroup;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendGroup;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;

public class AddFriendGroupEvent implements IFriendEvent {
    private Player player;
    private String name;
    private FriendHandler handler;

    public AddFriendGroupEvent(final Player player, final String name) {
        this.player = player;
        this.name = name;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        final int groupId = 500 + this.handler.getGroupMap().size() + 1;
        final FriendGroup friendGroup = new FriendGroup(groupId);
        this.handler.getGroupMap().put(friendGroup.getGroupId(), friendGroup);
        this.player.sendPacket(new SMAddFriendGroup(friendGroup));
    }

    @Override
    public boolean canAct() {
        if (this.handler.getGroupMap().size() >= 4) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoFriendGroupNotMoreAdd, CMAddFriendGroup.class));
            return false;
        }
        return true;
    }
}
