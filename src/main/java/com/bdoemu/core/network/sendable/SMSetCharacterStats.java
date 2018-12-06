// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterStats extends SendablePacket<GameClient> {
    private final Creature creature;

    public SMSetCharacterStats(final Creature creature) {
        this.creature = creature;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final GameStats stats = this.creature.getGameStats();
        buffer.writeC(1);
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeQ((long) stats.getWeight().getIntMaxValue());
        buffer.writeD(1);
        buffer.writeF(stats.getHPRegen().getValue());
        buffer.writeD(stats.getMPRegen().getIntValue());
        buffer.writeD(0);
        buffer.writeD(1000000);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
