// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;

import java.util.List;

public class ItemNpcWorkToWarehouseEvent extends AItemEvent {
    private List<ItemSubGroupT> itemsSubGroup;
    private long workerObjectId;

    public ItemNpcWorkToWarehouseEvent(final Player player, final int townId, final List<ItemSubGroupT> itemsSubGroup, final long workerObjectId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, townId);
        this.itemsSubGroup = itemsSubGroup;
        this.workerObjectId = workerObjectId;
    }

    @Override
    public void onEvent() {
        this.whAddType = 1;
        this.whSenderObjId = this.workerObjectId;
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        for (final ItemSubGroupT itemSubGroupT : this.itemsSubGroup) {
            long count = 1L;
            if (itemSubGroupT.getMinCount() > 0 && itemSubGroupT.getMaxCount() > 0)
                count = Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount());
            this.addWHItem(new Item(itemSubGroupT.getItemId(), count, itemSubGroupT.getEnchantLevel()));
        }
        return super.canAct();
    }
}
