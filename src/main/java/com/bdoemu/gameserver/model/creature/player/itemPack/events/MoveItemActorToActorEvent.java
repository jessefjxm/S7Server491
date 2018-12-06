// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMSetCharacterWeight;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class MoveItemActorToActorEvent extends AItemEvent {
    private int srcSlot;
    private long itemCount;
    private EItemStorageLocation srcStorage;
    private EItemStorageLocation dstStorage;

    public MoveItemActorToActorEvent(final Player player, final Playable srcActor, final Playable dstActor, final EItemStorageLocation srcStorage, final EItemStorageLocation dstStorage, final int srcSlot, final long itemCount) {
        super(player, srcActor, dstActor, EStringTable.eErrNoItemPopFromVehicle, EStringTable.eErrNoItemPopFromVehicle, 0);
        this.srcStorage = srcStorage;
        this.dstStorage = dstStorage;
        this.srcSlot = srcSlot;
        this.itemCount = itemCount;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (!this.addTasks.isEmpty()) {
            this.player.sendBroadcastItSelfPacket(new SMSetCharacterWeight(this.dstActor));
        }
    }

    @Override
    public boolean canAct() {
        final Item srcItem = this.srcActor.getInventory().getItem(this.srcSlot);
        if (srcItem == null) {
            return false;
        }
        this.decreaseItem(this.srcSlot, this.itemCount, this.srcStorage);
        this.addItem(new Item(srcItem, this.itemCount));
        return super.canAct();
    }
}
