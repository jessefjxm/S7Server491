// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMBeginDelayedLogout extends SendablePacket<GameClient> {
    private final long time;

    public SMBeginDelayedLogout(final long time) {
        this.time = time;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.time);
    }
}
