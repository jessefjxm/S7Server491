// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

public class CashProductT {
    private final int itemId;
    private final long count;

    public CashProductT(final int itemId, final long count) {
        this.itemId = itemId;
        this.count = count;
    }

    public long getCount() {
        return this.count;
    }

    public int getItemId() {
        return this.itemId;
    }
}
