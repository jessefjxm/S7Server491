// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMExchangeItemToClass;
import com.bdoemu.gameserver.dataholders.ItemExchangeData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ExchangeItemToClassEvent extends AItemEvent {
    private EItemStorageLocation ticketStorageType;
    private EItemStorageLocation itemStorageType;
    private int ticketSlotIndex;
    private int itemSlotIndex;
    private EClassType classType;
    private Integer itemId;
    private Item addItem;

    public ExchangeItemToClassEvent(final Player player, final EItemStorageLocation ticketStorageType, final int ticketSlotIndex, final EItemStorageLocation itemStorageType, final int itemSlotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.ticketStorageType = ticketStorageType;
        this.ticketSlotIndex = ticketSlotIndex;
        this.itemStorageType = itemStorageType;
        this.itemSlotIndex = itemSlotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMExchangeItemToClass(this.itemId, this.addItem.getEnchantLevel(), this.addItem.getStorageLocation().getId(), this.addItem.getSlotIndex()));
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
        if (eventType == null || !eventType.isItemExchangeToClass()) {
            return false;
        }
        final int classTypeValue = ticket.getTemplate().getContentsEventParam1();
        if (classTypeValue < 0) {
            this.classType = this.player.getClassType();
        } else {
            this.classType = EClassType.valueOf(classTypeValue);
        }
        if (this.classType == null) {
            return false;
        }
        this.itemId = ItemExchangeData.getInstance().getItemId(item.getItemId(), this.classType);
        if (this.itemId == null) {
            return false;
        }
        this.decreaseItem(this.ticketSlotIndex, 1L, this.ticketStorageType);
        this.decreaseItem(this.itemSlotIndex, 1L, this.itemStorageType);
        this.addItem = this.addItem(this.itemId, 1L, item.getEnchantLevel());
        return super.canAct();
    }
}
