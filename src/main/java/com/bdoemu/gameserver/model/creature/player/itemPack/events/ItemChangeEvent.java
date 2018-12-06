// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMItemChange;
import com.bdoemu.gameserver.dataholders.ItemChangeData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemChangeT;

public class ItemChangeEvent extends AItemEvent {
    private EItemStorageLocation ticketStorageType;
    private EItemStorageLocation itemStorageType;
    private int ticketSlotIndex;
    private int itemSlotIndex;
    private Item addItem;

    public ItemChangeEvent(final Player player, final EItemStorageLocation ticketStorageType, final int ticketSlotIndex, final EItemStorageLocation itemStorageType, final int itemSlotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.ticketStorageType = ticketStorageType;
        this.itemStorageType = itemStorageType;
        this.ticketSlotIndex = ticketSlotIndex;
        this.itemSlotIndex = itemSlotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMItemChange(this.addItem.getStorageLocation().getId(), this.addItem.getSlotIndex()));
    }

    @Override
    public boolean canAct() {
        if (!this.ticketStorageType.isPlayerInventories() || !this.itemStorageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack ticketPack = this.playerBag.getItemPack(this.ticketStorageType);
        final ItemPack itemPack = this.playerBag.getItemPack(this.itemStorageType);
        if (ticketPack == null || itemPack == null) {
            return false;
        }
        final Item ticket = ticketPack.getItem(this.ticketSlotIndex);
        final Item item = itemPack.getItem(this.itemSlotIndex);
        if (ticket == null || item == null) {
            return false;
        }
        final EContentsEventType eventType = ticket.getTemplate().getContentsEventType();
        if (eventType == null || !eventType.isItemChange()) {
            return false;
        }
        final ItemChangeT template = ItemChangeData.getInstance().getTemplate(item.getItemId());
        if (template == null || template.getFromEnchantLevel() != item.getEnchantLevel()) {
            return false;
        }
        this.decreaseItem(this.ticketSlotIndex, 1L, this.ticketStorageType);
        this.decreaseItem(this.itemSlotIndex, 1L, this.itemStorageType);
        this.addItem = this.addItem(template.getToItemId(), 1L, template.getToEnchantLevel());
        return super.canAct();
    }
}
