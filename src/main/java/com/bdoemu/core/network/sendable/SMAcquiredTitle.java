// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMAcquiredTitle extends SendablePacket<GameClient> {
    private final int titleId;

    public SMAcquiredTitle(final int titleId) {
        this.titleId = titleId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.titleId);
    }
}
