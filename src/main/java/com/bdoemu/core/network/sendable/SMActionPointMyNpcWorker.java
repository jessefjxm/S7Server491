// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMActionPointMyNpcWorker extends SendablePacket<GameClient> {
    private final long objectId;
    private final int actionPoints;

    public SMActionPointMyNpcWorker(final long objectId, final int actionPoints) {
        this.objectId = objectId;
        this.actionPoints = actionPoints;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeD(this.actionPoints);
    }
}
