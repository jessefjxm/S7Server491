// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMixServant extends SendablePacket<GameClient> {
    private long mixServantObjId1;
    private long mixServantObjId2;

    public SMMixServant(final long mixServantObjId1, final long mixServantObjId2) {
        this.mixServantObjId1 = mixServantObjId1;
        this.mixServantObjId2 = mixServantObjId2;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.mixServantObjId1);
        buffer.writeQ(this.mixServantObjId2);
    }
}
