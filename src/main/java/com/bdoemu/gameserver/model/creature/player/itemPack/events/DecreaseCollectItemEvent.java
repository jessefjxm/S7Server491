// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMRepeatCollectItem;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

import java.util.Collection;

public class DecreaseCollectItemEvent extends AItemEvent {
    private int slotIndex;
    private EItemStorageLocation srcStorageType;
    private Item useItem;
    private Collection<Item> items;

    public DecreaseCollectItemEvent(final Player player, final EItemStorageLocation srcStorageType, final int slotIndex, final Collection<Item> items) {
        super(player, player, player, EStringTable.eErrNoAcquireCollectItem, EStringTable.eErrNoAcquireCollectItem, 0);
        this.slotIndex = slotIndex;
        this.srcStorageType = srcStorageType;
        this.items = items;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.useItem.getCount() > 0L) {
            this.player.sendPacket(new SMRepeatCollectItem(this.srcStorageType, this.slotIndex));
        }
    }

    @Override
    public boolean canAct() {
        final ItemPack decreaseItemPack = this.playerBag.getItemPack(this.srcStorageType);
        if (decreaseItemPack == null) {
            return false;
        }
        this.useItem = decreaseItemPack.getItem(this.slotIndex);
        final EContentsEventType eventType = this.useItem.getTemplate().getContentsEventType();
        if (eventType == null || !this.useItem.getTemplate().getContentsEventType().isCollectByTool()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.srcStorageType);
        for (final Item addItem : this.items) {
            this.addItem(addItem);
        }
        return super.canAct();
    }
}
