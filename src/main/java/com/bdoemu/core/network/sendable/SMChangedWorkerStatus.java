// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;

public class SMChangedWorkerStatus extends SendablePacket<GameClient> {
    private final NpcWorker npcWorker;

    public SMChangedWorkerStatus(final NpcWorker npcWorker) {
        this.npcWorker = npcWorker;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.npcWorker.getObjectId());
        buffer.writeD(this.npcWorker.getLevel());
        buffer.writeD(this.npcWorker.getExp());
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
        for (final int skill : this.npcWorker.getArrPassiveSkills()) {
            buffer.writeH(skill);
        }
        buffer.writeC(this.npcWorker.getUpgradeCount());
    }
}
