// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMWorkerChangeWorkingState;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMWorkerChangeWorkingState extends ReceivablePacket<GameClient> {
    private long workerObjId;
    private int workingState;

    public CMWorkerChangeWorkingState(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.workerObjId = this.readQ();
        this.workingState = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final NpcWorker npcWorker = player.getNpcWorkerController().getNpcWorker(this.workerObjId);
            if (npcWorker == null || npcWorker.getWorkingState() > this.workingState) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNpcWorkerNotExist, this.opCode));
                return;
            }
            npcWorker.setWorkingState(this.workingState);
            npcWorker.setUpdateTime(GameTimeService.getServerTimeInSecond());
            player.sendPacket(new SMWorkerChangeWorkingState(npcWorker));
        }
    }
}
