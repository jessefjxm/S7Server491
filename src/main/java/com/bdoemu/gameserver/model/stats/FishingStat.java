package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class FishingStat extends BaseRateStat {
    public FishingStat(final Creature owner) {
        super(owner);
    }

    public int getFishingRate() {
        return this.getRateTemplate().getFishingPercent();
    }
}