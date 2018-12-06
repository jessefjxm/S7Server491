// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendGroup;

public class SMRenameFriendGroup extends SendablePacket<GameClient> {
    private final FriendGroup friendGroup;

    public SMRenameFriendGroup(final FriendGroup friendGroup) {
        this.friendGroup = friendGroup;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.friendGroup.getGroupId());
        buffer.writeS((CharSequence) this.friendGroup.getName(), 62);
    }
}
