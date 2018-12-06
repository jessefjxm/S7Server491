// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

public class DeliverItem {
    private Item item;
    private int originTownId;
    private int destTownId;

    public DeliverItem(final Item item, final int originTownId, final int destTownId) {
        this.item = item;
        this.originTownId = originTownId;
        this.destTownId = destTownId;
    }

    public int getDestTownId() {
        return this.destTownId;
    }

    public int getOriginTownId() {
        return this.originTownId;
    }

    public Item getItem() {
        return this.item;
    }
}
