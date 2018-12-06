// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRegisterServantAuctionGoodsVer2Nak extends SendablePacket<GameClient> {
    private long endTime;

    public SMRegisterServantAuctionGoodsVer2Nak(final long endTime) {
        this.endTime = endTime;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.endTime);
    }
}
