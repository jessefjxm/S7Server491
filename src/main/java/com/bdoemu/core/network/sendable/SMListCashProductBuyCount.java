// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.CashProductBuyCount;

import java.util.Collection;

public class SMListCashProductBuyCount extends SendablePacket<GameClient> {
    private final Collection<CashProductBuyCount> items;

    public SMListCashProductBuyCount(final Collection<CashProductBuyCount> items) {
        this.items = items;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.items.size());
        for (final CashProductBuyCount cashProductBuyCount : this.items) {
            buffer.writeH(cashProductBuyCount.getProductNr());
            buffer.writeD(cashProductBuyCount.getCount());
            buffer.writeQ(cashProductBuyCount.getBuyDate());
        }
    }
}
