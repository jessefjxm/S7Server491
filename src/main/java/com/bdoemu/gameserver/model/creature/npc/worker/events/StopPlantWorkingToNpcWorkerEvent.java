// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.events;

import com.bdoemu.core.network.sendable.SMStopPlantWorkingToNpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.player.Player;

public class StopPlantWorkingToNpcWorkerEvent implements INpcWorkerEvent {
    private Player player;
    private long objectId;
    private NpcWorkerController npcWorkerController;
    private NpcWorker npcWorker;

    public StopPlantWorkingToNpcWorkerEvent(final Player player, final long objectId) {
        this.player = player;
        this.objectId = objectId;
        this.npcWorkerController = player.getNpcWorkerController();
    }

    @Override
    public void onEvent() {
        this.npcWorker.stopWorking();
        this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 1));
    }

    @Override
    public boolean canAct() {
        this.npcWorker = this.npcWorkerController.getNpcWorker(this.objectId);
        return this.npcWorker != null && this.npcWorker.getStartTime() > 0L && this.npcWorker.getNpcWork().canStopWork();
    }
}
