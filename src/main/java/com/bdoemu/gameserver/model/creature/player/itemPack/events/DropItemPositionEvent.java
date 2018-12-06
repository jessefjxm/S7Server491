// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMDropItemPosition;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.enums.EItemType;

import java.util.Map;

public class DropItemPositionEvent extends AItemEvent {
    private DeadBody deadBody;

    public DropItemPositionEvent(final Player player, final DeadBody deadBody) {
        super(player, player, player, EStringTable.eErrNoItemIsDroppedByDead, EStringTable.eErrNoItemIsDroppedByDead, 0);
        this.deadBody = deadBody;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendBroadcastItSelfPacket(new SMDropItemPosition(this.deadBody));
    }

    @Override
    public boolean canAct() {
        Integer dropIndex = null;
        for (final Map.Entry<Integer, Item> entry : this.playerInventory.getItemMap().entrySet()) {
            final Item item = entry.getValue();
            if (item.getTemplate().getItemType() == EItemType.Normal && item.getTemplate().isDropable() && Rnd.get(1000000) <= 100000) {
                dropIndex = entry.getKey();
                final long count = (item.getCount() < 3L) ? item.getCount() : Rnd.get(1, 3);
                this.decreaseItem(dropIndex, count, EItemStorageLocation.Inventory);
                this.deadBody.setDropBag(new DropBag(item, count, this.deadBody.getGameObjectId(), EDropBagType.DeadBody));
                break;
            }
        }
        return dropIndex != null && super.canAct();
    }
}
