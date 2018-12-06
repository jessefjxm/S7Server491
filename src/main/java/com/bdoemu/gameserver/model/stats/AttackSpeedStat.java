package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class AttackSpeedStat extends BaseRateStat {
    public AttackSpeedStat(final Creature owner) {
        super(owner);
    }

    public int getAttackSpeedRate() {
        return this.getRateTemplate().getAttackspeedPercent() + this.currentStatRate;
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