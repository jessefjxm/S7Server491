// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMUpdatePalette;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CreatePaletteFromItemEvent extends AItemEvent {
    private EItemStorageLocation storageType;
    private int inventorySlot;
    private Item dyeItem;

    public CreatePaletteFromItemEvent(final Player player, final EItemStorageLocation storageType, final int inventorySlot) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageType = storageType;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.getDyeStorage().addDye(this.dyeItem.getItemId(), 1L);
        this.player.sendPacket(new SMUpdatePalette(this.dyeItem.getItemId(), 1L, false, true));
    }

    @Override
    public boolean canAct() {
        if (!this.storageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageType);
        if (itemPack == null) {
            return false;
        }
        this.dyeItem = itemPack.getItem(this.inventorySlot);
        if (this.dyeItem == null) {
            return false;
        }
        this.decreaseItem(this.inventorySlot, 1L, this.storageType);
        return this.dyeItem.getItemClassify().isDyeAmpule() || this.dyeItem.getItemId() == 17318;
    }
}
