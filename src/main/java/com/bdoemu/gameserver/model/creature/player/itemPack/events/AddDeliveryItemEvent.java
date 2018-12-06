// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMAddDeliveryItem;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.DeliverItem;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class AddDeliveryItemEvent extends AItemEvent {
    private long itemObjId;
    private long itemCount;
    private int carriageId;
    private int originTownId;
    private int destTownId;
    private DeliverItem deliverItem;
    private Item item;

    public AddDeliveryItemEvent(final Player player, final long itemObjId, final long itemCount, final int carriageId, final int originTownId, final int destTownId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, originTownId);
        this.itemObjId = itemObjId;
        this.itemCount = itemCount;
        this.carriageId = carriageId;
        this.originTownId = originTownId;
        this.destTownId = destTownId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMAddDeliveryItem(this.deliverItem, this.item.getObjectId()));
    }

    @Override
    public boolean canAct() {
        if (this.warehouse == null) {
            return false;
        }
        final Integer index = this.warehouse.getItemIndex(this.itemObjId);
        if (index == null || index < 2) {
            return false;
        }
        this.item = this.warehouse.getItem(index);
        if (this.item == null) {
            return false;
        }
        this.deliverItem = new DeliverItem(new Item(this.item, this.itemCount), this.originTownId, this.destTownId);
        this.decreaseItem(index, this.itemCount, EItemStorageLocation.Warehouse);
        this.decreaseItem(0, 1L, EItemStorageLocation.Warehouse);
        return super.canAct();
    }
}
