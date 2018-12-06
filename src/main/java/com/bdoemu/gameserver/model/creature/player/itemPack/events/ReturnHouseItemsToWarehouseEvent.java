// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.core.network.sendable.SMRefreshWarehouseItem;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.items.Item;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReturnHouseItemsToWarehouseEvent implements IBagEvent {
    private Player player;
    private int regionId;
    private Warehouse warehouse;
    private Collection<HouseInstallation> installations;
    private ConcurrentLinkedQueue<Item> addTasks;

    public ReturnHouseItemsToWarehouseEvent(final Player player, final int regionId, final Collection<HouseInstallation> installations) {
        this.player = player;
        this.regionId = regionId;
        this.installations = installations;
    }

    @Override
    public void onEvent() {
        this.warehouse.addItems(this.addTasks, 0L, 0, false);
        this.player.sendPacket(new SMRefreshWarehouseItem(this.regionId));
    }

    @Override
    public boolean canAct() {
        this.warehouse = this.player.getPlayerBag().getWarehouse(this.regionId);
        this.addTasks = new ConcurrentLinkedQueue<Item>();
        for (final HouseInstallation installation : this.installations) {
            final Item addItem = new Item(installation.getItemId(), 1L, 0);
            addItem.setEndurance(installation.getEndurance());
            this.addTasks.add(addItem);
        }
        return true;
    }
}
