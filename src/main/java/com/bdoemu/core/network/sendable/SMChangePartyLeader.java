// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangePartyLeader extends SendablePacket<GameClient> {
    private final int sessionId;

    public SMChangePartyLeader(final int sessionId) {
        this.sessionId = sessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
    }
}
