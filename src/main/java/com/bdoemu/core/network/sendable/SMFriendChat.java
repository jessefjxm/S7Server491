// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMFriendChat extends SendablePacket<GameClient> {
    private final long friendAccountId;
    private final String mesage;

    public SMFriendChat(final long friendAccountId, final String mesage) {
        this.friendAccountId = friendAccountId;
        this.mesage = mesage;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.friendAccountId);
        buffer.writeSS((CharSequence) this.mesage);
    }
}
