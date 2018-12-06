package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class CriticalStat extends BaseRateStat {
    public CriticalStat(final Creature owner) {
        super(owner);
    }

    public int getCriticalRate() {
        return this.getRateTemplate().getCriticalPercent();
    }
}