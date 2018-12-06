package com.bdoemu.gameserver.model.manufactures;

public class ManufactureItem {
    private int itemId;
    private long count;

    public ManufactureItem(final int itemId, final long count) {
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
