// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMDeletePositionGuide extends SendablePacket<GameClient> {
    private final int sessionId;
    private final byte markType;

    public SMDeletePositionGuide(final int sessionId, final byte markType) {
        this.sessionId = sessionId;
        this.markType = markType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeC((int) this.markType);
    }
}
