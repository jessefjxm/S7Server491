// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetTendency extends SendablePacket<GameClient> {
    private final int sessionId;
    private final int exp;

    public SMSetTendency(final int sessionId, final int exp) {
        this.sessionId = sessionId;
        this.exp = exp;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeD(this.exp);
    }
}
