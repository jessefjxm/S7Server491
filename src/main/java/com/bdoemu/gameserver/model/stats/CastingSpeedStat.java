package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class CastingSpeedStat extends BaseRateStat {
    public CastingSpeedStat(final Creature owner) {
        super(owner);
    }

    public int getCastingSpeedRate() {
        return this.getRateTemplate().getCastingspeedPercent() + this.currentStatRate;
    }

    @Override
    protected float getValueBonus() {
        return 5.0f;
    }

    @Override
    protected float getMaxValueBonus() {
        return 5.0f;
    }
}