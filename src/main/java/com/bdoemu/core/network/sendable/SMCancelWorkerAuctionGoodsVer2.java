// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCancelWorkerAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private long npcWorkerObjId;

    public SMCancelWorkerAuctionGoodsVer2(final long npcWorkerObjId) {
        this.npcWorkerObjId = npcWorkerObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.npcWorkerObjId);
    }
}
