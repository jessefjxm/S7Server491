// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.core.network.sendable.SMExchangeItemSlotInInventory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ExchangeItemSlotEvent implements IBagEvent {
    private Player player;
    private EItemStorageLocation storageType;
    private int oldSlot;
    private int nextSlot;
    private PlayerBag playerBag;
    private ItemPack pack;
    private Item oldItem;

    public ExchangeItemSlotEvent(final Player player, final EItemStorageLocation storageType, final int oldSlot, final int nextSlot) {
        this.player = player;
        this.storageType = storageType;
        this.oldSlot = oldSlot;
        this.nextSlot = nextSlot;
        this.playerBag = player.getPlayerBag();
    }

    @Override
    public void onEvent() {
        this.pack.removeItem(this.oldSlot);
        final Item nextItem = this.pack.removeItem(this.nextSlot);
        this.pack.addItem(this.oldItem, this.nextSlot);
        this.oldItem.setSlotIndex(this.nextSlot);
        this.pack.addItem(nextItem, this.oldSlot);
        if (nextItem != null) {
            nextItem.setSlotIndex(this.oldSlot);
        }
        this.player.sendPacket(new SMExchangeItemSlotInInventory(this.player, this.storageType, this.oldSlot, this.nextSlot));
    }

    @Override
    public boolean canAct() {
        if (this.player.hasTrade() || !this.storageType.isPlayerInventories()) {
            return false;
        }
        this.pack = this.playerBag.getItemPack(this.storageType);
        if (this.pack == null || this.oldSlot > this.pack.getExpandSize()) {
            return false;
        }
        if (this.oldSlot == this.nextSlot || this.oldSlot < this.pack.getDefaultSlotIndex() || this.nextSlot < this.pack.getDefaultSlotIndex()) {
            return false;
        }
        this.oldItem = this.pack.getItem(this.oldSlot);
        return this.oldItem != null;
    }
}
