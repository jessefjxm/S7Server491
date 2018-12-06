// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCompleteServantMating extends SendablePacket<GameClient> {
    private long servantObjId;
    private long count;

    public SMCompleteServantMating(final long servantObjId, final long count) {
        this.servantObjId = servantObjId;
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
        buffer.writeQ(this.count);
    }
}
