// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterPrivatePoints extends SendablePacket<GameClient> {
    private final Creature creature;

    public SMSetCharacterPrivatePoints(final Creature creature) {
        this.creature = creature;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final GameStats stats = this.creature.getGameStats();
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeQ(stats.getWeight().getLongMaxValue());
        buffer.writeF(1000);
        buffer.writeF(stats.getJumpHeight().getMaxValue());
        buffer.writeF(stats.getVisionRange().getMaxValue());
        buffer.writeD(0);
        buffer.writeH(0);
        // ------------------------------------------------------------
        if (stats.getResistKnockDown().getIntMaxValue() > 1_000_000)
            buffer.writeD(1_000_000);
        else
            buffer.writeD(stats.getResistKnockDown().getIntMaxValue());
        // ------------------------------------------------------------
        if (stats.getResistKnockDown().getIntMaxValue() > 1_000_000)
            buffer.writeD(1_000_000);
        else
            buffer.writeD(stats.getResistStun().getIntMaxValue());
        // ------------------------------------------------------------
        if (stats.getResistKnockDown().getIntMaxValue() > 1_000_000)
            buffer.writeD(1_000_000);
        else
            buffer.writeD(stats.getResistKnockBack().getIntMaxValue());
        // ------------------------------------------------------------
        if (stats.getResistKnockDown().getIntMaxValue() > 1_000_000)
            buffer.writeD(1_000_000);
        else
            buffer.writeD(stats.getResistCapture().getIntMaxValue());
        // ------------------------------------------------------------
    }
}