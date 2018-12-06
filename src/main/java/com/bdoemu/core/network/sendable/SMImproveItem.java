// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMImproveItem extends SendablePacket<GameClient> {
    private final EItemStorageLocation itemStorageLocation;
    private final EItemStorageLocation storageLocation;
    private final int itemSlotIndex;
    private final int slotIndex;
    private Item item;

    public SMImproveItem(final EItemStorageLocation itemStorageLocation, final EItemStorageLocation storageLocation, final int itemSlotIndex, final int slotIndex, final Item item) {
        this.itemStorageLocation = itemStorageLocation;
        this.storageLocation = storageLocation;
        this.itemSlotIndex = itemSlotIndex;
        this.slotIndex = slotIndex;
        this.item = item;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.itemStorageLocation.ordinal());
        buffer.writeC(this.itemSlotIndex);
        buffer.writeC(this.storageLocation.ordinal());
        buffer.writeC(this.slotIndex);
        buffer.writeQ(this.item.getCount());
        buffer.writeH(this.item.getItemId());
        buffer.writeH(this.item.getEnchantLevel());
    }
}
