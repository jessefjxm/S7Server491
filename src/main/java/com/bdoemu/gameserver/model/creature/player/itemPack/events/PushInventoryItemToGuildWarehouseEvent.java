package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class PushInventoryItemToGuildWarehouseEvent extends AItemEvent {
    private int slotIndex;
    private long count;
    private EItemStorageLocation storageType;

    public PushInventoryItemToGuildWarehouseEvent(final Player player, final Playable srcActor, final EItemStorageLocation storageType, final int slotIndex, final long count, final int townId) {
        super(player, srcActor, player, EStringTable.eErrNoItemIsRemovedToUseWarehouse, EStringTable.eErrNoItemIsRemovedToUseWarehouse, townId);
        this.slotIndex = slotIndex;
        this.count = count;
        this.storageType = storageType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        //System.out.println("Act#1");
        if (!this.storageType.isPlayerInventories() && !this.storageType.isServantInventory()) {
            return false;
        }
        //System.out.println("Act#2");
        if ((this.storageType.isCashInventory() || this.storageType.isServantInventory()) && this.slotIndex < 2) {
            return false;
        }
        //System.out.println("Act#3");
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageType, this.srcActor.getGameObjectId(), 0);
        if (itemPack == null) {
            return false;
        }
        //System.out.println("Act#4");
        final Item item = itemPack.getItem(this.slotIndex);
        if (item == null || (item.isVested() && !item.getTemplate().isUserVested())) {
            return false;
        }
        //System.out.println("Act#5");
        this.decreaseItem(this.slotIndex, this.count, this.storageType);
        this.addGuildWHItem(new Item(item, this.count));
        return super.canAct();
    }
}
