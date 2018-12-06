package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterStatPoint extends SendablePacket<GameClient> {
    private final GameStats<? extends Creature> stats;
    private final Creature creature;

    public SMSetCharacterStatPoint(final Creature creature) {
        this.creature = creature;
        this.stats = creature.getGameStats();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeD(this.stats.getMoveSpeedRate().getIntValue());
        buffer.writeD(this.stats.getAttackSpeedRate().getIntValue());
        buffer.writeD(this.stats.getCastingSpeedRate().getIntValue());
        buffer.writeD(this.stats.getMoveSpeedRate().getIntMaxValue());
        buffer.writeD(this.stats.getAttackSpeedRate().getIntMaxValue());
        buffer.writeD(this.stats.getCastingSpeedRate().getIntMaxValue());
        buffer.writeD(this.stats.getCriticalRate().getIntValue() > 5 ? 5 : this.stats.getCriticalRate().getIntValue());
        buffer.writeD(this.stats.getCriticalRate().getIntMaxValue() > 5 ? 5 : this.stats.getCriticalRate().getIntMaxValue());
        buffer.writeD(this.stats.getDropItemLuck().getIntValue() > 5 ? 5 : this.stats.getDropItemLuck().getIntValue());
        buffer.writeD(this.stats.getDropItemLuck().getIntMaxValue() > 5 ? 5 : this.stats.getDropItemLuck().getIntMaxValue());
        buffer.writeD(this.stats.getFishingLuck().getIntValue() > 5 ? 5 : this.stats.getFishingLuck().getIntValue());
        buffer.writeD(this.stats.getFishingLuck().getIntMaxValue() > 5 ? 5 : this.stats.getFishingLuck().getIntMaxValue());
        buffer.writeD(this.stats.getCollectionLuck().getIntValue() > 5 ? 5 : this.stats.getCollectionLuck().getIntValue());
        buffer.writeD(this.stats.getCollectionLuck().getIntMaxValue() > 5 ? 5 : this.stats.getCollectionLuck().getIntMaxValue());
    }
}