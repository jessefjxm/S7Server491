package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMActionPointMyNpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class UseItemToWorkerEvent extends AItemEvent {
    private long objectId;
    private int slotIndex;
    private int actionPoints;
    private long count;
    private NpcWorker npcWorker;

    public UseItemToWorkerEvent(final Player player, final long objectId, final int slotIndex, final long count) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.objectId = objectId;
        this.slotIndex = slotIndex;
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.npcWorker.addActions(this.actionPoints);
        this.player.sendPacket(new SMActionPointMyNpcWorker(this.objectId, this.actionPoints));
    }

    @Override
    public boolean canAct() {
        final Item item = this.playerInventory.getItem(this.slotIndex);
        if (item == null)
            return false;

        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isUseItemToWorker())
            return false;

        if (item.getCount() < this.count)
            return false;

        this.actionPoints = item.getTemplate().getContentsEventParam1();
        this.npcWorker = this.player.getNpcWorkerController().getNpcWorker(this.objectId);
        this.decreaseItem(this.slotIndex, this.count, EItemStorageLocation.Inventory);
        return super.canAct() && this.npcWorker != null && this.npcWorker.getActionPoints() < this.npcWorker.getPlantWorkerT().getActionPoint();
    }
}










