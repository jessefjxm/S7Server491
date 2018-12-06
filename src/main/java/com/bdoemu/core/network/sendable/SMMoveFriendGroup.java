// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMoveFriendGroup extends SendablePacket<GameClient> {
    private final long friendObjectId;
    private final int groupId;

    public SMMoveFriendGroup(final long friendObjectId, final int groupId) {
        this.friendObjectId = friendObjectId;
        this.groupId = groupId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.friendObjectId);
        buffer.writeH(this.groupId);
    }
}
