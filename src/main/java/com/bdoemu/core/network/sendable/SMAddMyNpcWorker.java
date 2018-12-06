// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMAddMyNpcWorker extends SendablePacket<GameClient> {
    private final Collection<NpcWorker> npcWorkers;
    private final EPacketTaskType packetTaskType;

    public SMAddMyNpcWorker(final Collection<NpcWorker> npcWorkers, final EPacketTaskType packetTaskType) {
        this.npcWorkers = npcWorkers;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.npcWorkers.size());
        for (final NpcWorker npcWorker : this.npcWorkers) {
            buffer.writeQ(npcWorker.getObjectId());
            buffer.writeH(npcWorker.getCharacterKey());
            buffer.writeD(npcWorker.getActionPoints());
            buffer.writeD(npcWorker.getWaypointKey());
            buffer.writeD(npcWorker.getLevel());
            buffer.writeD(npcWorker.getExp());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            for (final int skill : npcWorker.getArrPassiveSkills()) {
                buffer.writeH(skill);
            }
            buffer.writeC(npcWorker.getUpgradeCount());
            buffer.writeC(npcWorker.getState().ordinal());
        }
    }
}
