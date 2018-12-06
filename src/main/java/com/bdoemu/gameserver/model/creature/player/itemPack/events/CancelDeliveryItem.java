// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMCancelDeliveryItem;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.DeliverItem;

public class CancelDeliveryItem extends AItemEvent {
    private long itemObjId;
    private int originTownId;
    private DeliverItem deliverItem;

    public CancelDeliveryItem(final Player player, final long itemObjId, final int originTownId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, originTownId);
        this.itemObjId = itemObjId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMCancelDeliveryItem(this.deliverItem));
    }

    @Override
    public boolean canAct() {
        return super.canAct();
    }
}
