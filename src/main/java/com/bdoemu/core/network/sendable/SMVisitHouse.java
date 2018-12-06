// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVisitHouse extends SendablePacket<GameClient> {
    private final int sessionId;
    private final int houseId;
    private final long objectId;

    public SMVisitHouse(final int sessionId, final int houseId, final long objectId) {
        this.sessionId = sessionId;
        this.houseId = houseId;
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeH(this.houseId);
        buffer.writeQ(this.objectId);
    }
}
