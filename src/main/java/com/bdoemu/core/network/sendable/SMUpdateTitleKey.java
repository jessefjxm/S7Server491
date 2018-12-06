// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMUpdateTitleKey extends SendablePacket<GameClient> {
    private final int titleId;
    private final int sessionId;

    public SMUpdateTitleKey(final int titleId, final int sessionId) {
        this.titleId = titleId;
        this.sessionId = sessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.titleId);
        buffer.writeD(this.sessionId);
    }
}
