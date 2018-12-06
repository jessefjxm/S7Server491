// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.itemPack.ADBItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class SystemAddItemEvent implements IBagEvent {
    private ADBItemPack pack;
    private int itemId;
    private long count;
    private ItemTemplate template;

    public SystemAddItemEvent(final ADBItemPack pack, final int itemId, final long count) {
        this.pack = pack;
        this.itemId = itemId;
        this.count = count;
    }

    @Override
    public void onEvent() {
        for (int slotIndex = this.pack.getDefaultSlotIndex(); slotIndex < this.pack.getExpandSize() && !this.pack.addItem(new Item(this.template, this.count), slotIndex); ++slotIndex) {
        }
    }

    @Override
    public boolean canAct() {
        this.template = ItemData.getInstance().getItemTemplate(this.itemId);
        return this.template != null && this.pack.hasFreeSlots();
    }
}
