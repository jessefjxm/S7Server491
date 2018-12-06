// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

public class WarehouseDecreaseTask {
    private int itemId;
    private int enchantLevel;
    private long count;
    private int slotIndex;

    public WarehouseDecreaseTask(final int itemId, final int enchantLevel, final long count) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.count = count;
    }

    public long getCount() {
        return this.count;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void setSlotIndex(final int slotIndex) {
        this.slotIndex = slotIndex;
    }
}
