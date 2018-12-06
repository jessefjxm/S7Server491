// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.List;

public class SMAddCollectInfo extends SendablePacket<GameClient> {
    private static final int maximum = 700;
    private final List<Collect> collections;
    private int subSectorX;
    private int subSectorY;
    private EPacketTaskType packetTaskType;

    public SMAddCollectInfo(final EPacketTaskType packetTaskType, final List<Collect> collections, final int subSectorX, final int subSectorY) {
        if (collections.size() > 700) {
            throw new IllegalArgumentException("Maximum size 700");
        }
        this.collections = collections;
        this.subSectorX = subSectorX;
        this.subSectorY = subSectorY;
        this.packetTaskType = packetTaskType;
    }

    public static int getMaximum() {
        return 700;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.packetTaskType.ordinal());
        buffer.writeH(this.subSectorX);
        buffer.writeH(this.subSectorY);
        buffer.writeH(this.collections.size());
        for (final Collect collect : this.collections) {
            final SpawnPlacementT collectSpawnT = collect.getSpawnPlacement();
            buffer.writeH(collectSpawnT.getIndex());
            buffer.writeH(1);
            buffer.writeD(collectSpawnT.getSectorX());
            buffer.writeD(collectSpawnT.getSectorZ());
            buffer.writeD(collectSpawnT.getSectorY());
            buffer.writeD(collectSpawnT.getStaticId());
            buffer.writeH(collect.getCreatureId());
        }
    }
}
