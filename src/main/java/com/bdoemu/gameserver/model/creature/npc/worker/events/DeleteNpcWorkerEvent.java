// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMDeleteMyNpcWorker;
import com.bdoemu.core.network.sendable.SMDeleteMyNpcWorker;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.player.Player;

public class DeleteNpcWorkerEvent implements INpcWorkerEvent {
    private Player player;
    private long objectId;
    private NpcWorkerController npcWorkerController;

    public DeleteNpcWorkerEvent(final Player player, final long objectId) {
        this.player = player;
        this.objectId = objectId;
        this.npcWorkerController = player.getNpcWorkerController();
    }

    @Override
    public void onEvent() {
        this.player.sendPacket(new SMDeleteMyNpcWorker(this.objectId));
    }

    @Override
    public boolean canAct() {
        final NpcWorker worker = this.npcWorkerController.getNpcWorker(this.objectId);
        if (worker == null) {
            return false;
        }
        if (worker.getNpcWork() != null) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoNpcWorkerIsWorkingAlready, CMDeleteMyNpcWorker.class));
            return false;
        }
        return this.npcWorkerController.removeNpcWorker(worker);
    }
}
