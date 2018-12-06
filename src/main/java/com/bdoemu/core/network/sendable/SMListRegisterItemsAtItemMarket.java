// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.ItemMarket;

import java.util.Collection;

public class SMListRegisterItemsAtItemMarket extends WriteItemInfo {
    private final Collection<ItemMarket> items;
    private final long currentDate;
    private final byte type;

    public SMListRegisterItemsAtItemMarket(final Collection<ItemMarket> items, final long currentDate, final byte type) {
        this.items = items;
        this.currentDate = currentDate;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC((int) this.type);
        buffer.writeC(1);
        buffer.writeQ(this.currentDate);
        buffer.writeH(this.items.size());
        for (final ItemMarket itemMarket : this.items) {
            buffer.writeQ(itemMarket.getObjectId());
            this.writeItemHeader(buffer, itemMarket.getItem());
            buffer.writeQ(0L);
            buffer.writeQ(itemMarket.getExpirationDate());
            buffer.writeH(itemMarket.getTerritoryKey());
            buffer.writeQ(itemMarket.getTotalCount());
            buffer.writeQ(itemMarket.getCount());
            buffer.writeQ(itemMarket.getLastPrice());
            buffer.writeQ(itemMarket.getRevenue());
            buffer.writeC(itemMarket.isWaiting());
            this.writeItemInfo(buffer, itemMarket.getItem());
        }
    }
}
