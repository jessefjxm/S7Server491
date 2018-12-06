// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPopServantPriceAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private long servantObjId;

    public SMPopServantPriceAuctionGoodsVer2(final long servantObjId) {
        this.servantObjId = servantObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
    }
}
