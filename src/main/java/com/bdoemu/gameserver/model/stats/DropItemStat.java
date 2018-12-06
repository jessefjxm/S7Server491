package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.creature.Creature;

public class DropItemStat extends BaseRateStat {
    public DropItemStat(final Creature owner) {
        super(owner);
    }

    public int getDropItemRate() {
        return this.getRateTemplate().getDropItemPercent();
    }
}