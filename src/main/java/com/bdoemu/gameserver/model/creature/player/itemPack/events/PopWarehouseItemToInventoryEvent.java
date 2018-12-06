// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMPopWarehouseItemToInventory;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class PopWarehouseItemToInventoryEvent extends AItemEvent {
    private long objectId;
    private long count;
    private Integer index;

    public PopWarehouseItemToInventoryEvent(final Player player, final Playable dstActor, final long objectId, final long count, final int townId) {
        super(player, player, dstActor, EStringTable.eErrNoItemIsCreatedOtPopFromWarehouse, EStringTable.eErrNoItemIsCreatedOtPopFromWarehouse, townId);
        this.objectId = objectId;
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (this.warehouse == null) {
            return false;
        }
        this.index = this.warehouse.getItemIndex(this.objectId);
        if (this.index == null) {
            return false;
        }
        final Item item = this.warehouse.getItem(this.index);
        if (item == null) {
            return false;
        }
        if (item.getTemplate().isCash() && !this.dstActor.isPlayer()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoInventoryPushOnlyPlayer, CMPopWarehouseItemToInventory.class));
            return false;
        }
        this.addItem(new Item(item, this.count));
        this.decreaseItem(this.index, this.count, EItemStorageLocation.Warehouse);
        return super.canAct();
    }
}
