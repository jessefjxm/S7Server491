// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMGetServantMatingChildInfo extends SendablePacket<GameClient> {
    private int servantId;

    public SMGetServantMatingChildInfo(final int servantId) {
        this.servantId = servantId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ((long) this.servantId);
    }
}
