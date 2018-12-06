// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCancelNextNpcWork extends SendablePacket<GameClient> {
    private final long objectId;

    public SMCancelNextNpcWork(final long objectId) {
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
    }
}
