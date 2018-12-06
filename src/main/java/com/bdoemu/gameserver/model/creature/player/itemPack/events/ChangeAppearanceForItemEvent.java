// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ChangeAppearanceForItemEvent extends AItemEvent {
    private int slotIndex;
    private EItemStorageLocation storageType;

    public ChangeAppearanceForItemEvent(final Player player, final int slotIndex, final EItemStorageLocation storageType) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.slotIndex = slotIndex;
        this.storageType = storageType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
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
        final Item item = itemPack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        if (item.getItemId() != 17551) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageType);
        return super.canAct();
    }
}
