// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.ItemMarket;

import java.util.Collection;

public class SMListItemBodyAtItemMarket extends WriteItemInfo {
    private final Collection<ItemMarket> items;
    private final int itemId;
    private final int enchantLevel;
    private final long currentDate;

    public SMListItemBodyAtItemMarket(final Collection<ItemMarket> items, final int itemId, final int enchantLevel, final long currentDate) {
        this.items = items;
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.currentDate = currentDate;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeH(this.itemId);
        buffer.writeH(this.enchantLevel);
        buffer.writeQ(this.currentDate);
        buffer.writeH(this.items.size());
        for (final ItemMarket itemMarket : this.items) {
            buffer.writeQ(itemMarket.getObjectId());
            this.writeItemInfo(buffer, itemMarket.getItem());
            buffer.writeQ(itemMarket.getRegisteredDate());
            buffer.writeQ(itemMarket.getExpirationDate());
            buffer.writeQ(0L);
            buffer.writeQ(0L);
        }
    }
}
