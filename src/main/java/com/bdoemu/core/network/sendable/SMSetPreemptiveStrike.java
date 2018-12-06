// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetPreemptiveStrike extends SendablePacket<GameClient> {
    private int gameObjectId;
    private long endTime;

    public SMSetPreemptiveStrike(final int gameObjectId, final long endTime) {
        this.gameObjectId = gameObjectId;
        this.endTime = endTime;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeQ(this.endTime);
    }
}
