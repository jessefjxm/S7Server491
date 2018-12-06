// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SkillAwakeningItemEvent extends AItemEvent {
    private EItemStorageLocation itemStorageLocation;
    private int slotIndex;

    public SkillAwakeningItemEvent(final Player player, final EItemStorageLocation itemStorageLocation, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.itemStorageLocation = itemStorageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (!this.itemStorageLocation.isPlayerInventories()) {
            return false;
        }
        final Item item = this.playerBag.getItemPack(this.itemStorageLocation).getItem(this.slotIndex);
        if (item == null || item.getItemId() != 44195) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.itemStorageLocation);
        return super.canAct();
    }
}
