// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMStartServantMating extends SendablePacket<GameClient> {
    private long ownerServantObjId;
    private long matingServantObjId;
    private long matingEndTime;

    public SMStartServantMating(final long ownerServantObjId, final long matingServantObjId, final long matingEndTime) {
        this.ownerServantObjId = ownerServantObjId;
        this.matingServantObjId = matingServantObjId;
        this.matingEndTime = matingEndTime;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.ownerServantObjId);
        buffer.writeQ(this.matingServantObjId);
        buffer.writeQ(this.matingEndTime);
        buffer.writeD(0);
    }
}
