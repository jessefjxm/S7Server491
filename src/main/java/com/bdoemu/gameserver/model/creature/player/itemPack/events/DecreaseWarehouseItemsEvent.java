// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

import java.util.List;

public class DecreaseWarehouseItemsEvent extends AItemEvent {
    private List<WarehouseDecreaseTask> decreaseTasks;

    public DecreaseWarehouseItemsEvent(final Player player, final List<WarehouseDecreaseTask> decreaseTasks, final int regionId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.decreaseTasks = decreaseTasks;
    }

    @Override
    public void onEvent() {
        for (final WarehouseDecreaseTask task : this.decreaseTasks) {
            this.warehouse.decreaseItem(task.getSlotIndex(), task.getCount());
        }
    }

    @Override
    public boolean canAct() {
        for (final WarehouseDecreaseTask task : this.decreaseTasks) {
            final Integer itemIndex = this.warehouse.getItemIndex(task.getItemId(), task.getEnchantLevel());
            if (itemIndex == null) {
                return false;
            }
            if (!this.warehouse.canDecreaseItem(itemIndex, task.getCount())) {
                return false;
            }
            this.decreaseItem(itemIndex, task.getCount(), EItemStorageLocation.Warehouse);
            task.setSlotIndex(itemIndex);
        }
        return super.canAct();
    }
}
