package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.monster.Monster;

public class MonsterLifeStats extends LifeStats<Monster> {
    public MonsterLifeStats(final Monster owner) {
        super(owner, owner.getGameStats().getHp().getIntMaxValue(), owner.getGameStats().getMp().getIntMaxValue());
    }
}