package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class WeightStat extends Stat {
    public WeightStat(final Creature owner) {
        super(owner);
    }

    public void addWeight(final long value) {
        this.value += value;
    }

    public long getWeightPercentage() {
        final long maxValue = this.getLongMaxValue();
        return (maxValue > 0L) ? (this.getLongValue() * 100L / maxValue) : maxValue;
    }
}