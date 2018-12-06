// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.FloatFish;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMUpdateFloatFishing extends SendablePacket<GameClient> {
    private Collection<FloatFish> fishes;
    private EPacketTaskType packetTaskType;

    public SMUpdateFloatFishing(final Collection<FloatFish> fishes, final EPacketTaskType packetTaskType) {
        this.fishes = fishes;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.fishes.size());
        for (final FloatFish fish : this.fishes) {
            buffer.writeH(fish.getTemplate().getFishingGroupKey());
            buffer.writeF(fish.getLocation().getX());
            buffer.writeF(fish.getLocation().getZ());
            buffer.writeF(fish.getLocation().getY());
            buffer.writeD(fish.getTemplate().getPointSize());
            buffer.writeD(fish.getTemplate().getAvailableFishingLevel());
            buffer.writeH(fish.getTemplate().getSpawnCharacterKey());
            buffer.writeD(fish.getTemplate().getSpawnActionIndex());
            buffer.writeC(0);
            buffer.writeQ(fish.getDespawnTime());
        }
    }
}
