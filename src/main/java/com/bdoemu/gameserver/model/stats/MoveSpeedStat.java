package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class MoveSpeedStat extends BaseRateStat {
    public MoveSpeedStat(final Creature owner) {
        super(owner);
    }

    public int getMoveSpeedRate() {
        return this.getRateTemplate().getMovespeedPercent() + this.currentStatRate;
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