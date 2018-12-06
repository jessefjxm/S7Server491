// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendGroup;

import java.util.Collection;

public class SMFriendGroupList extends SendablePacket<GameClient> {
    private final Collection<FriendGroup> friendGroups;

    public SMFriendGroupList(final Collection<FriendGroup> friendGroups) {
        this.friendGroups = friendGroups;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.friendGroups.size());
        for (final FriendGroup friendGroup : this.friendGroups) {
            buffer.writeH(friendGroup.getGroupId());
            buffer.writeS((CharSequence) friendGroup.getName(), 62);
        }
    }
}
