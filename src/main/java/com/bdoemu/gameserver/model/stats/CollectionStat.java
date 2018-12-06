package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class CollectionStat extends BaseRateStat {
    public CollectionStat(final Creature owner) {
        super(owner);
    }

    public int getCollectionRate() {
        return this.getRateTemplate().getCollectionPercent();
    }
}