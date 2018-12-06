// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

public class ItemExchangeT {
    private int itemId;
    private int enchantLevel;
    private int index;
    private long count;

    public ItemExchangeT(final int itemId, final int enchantLevel, final long count, final int index) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.count = count;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
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
}
