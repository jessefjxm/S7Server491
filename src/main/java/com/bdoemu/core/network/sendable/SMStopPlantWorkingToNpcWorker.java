// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMStopPlantWorkingToNpcWorker extends SendablePacket<GameClient> {
    private long objectId;
    private int sessionId;
    private int type;

    public SMStopPlantWorkingToNpcWorker(final long objectId, final int sessionId, final int type) {
        this.objectId = objectId;
        this.sessionId = sessionId;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeD(this.sessionId);
        buffer.writeC(this.type);
    }
}
