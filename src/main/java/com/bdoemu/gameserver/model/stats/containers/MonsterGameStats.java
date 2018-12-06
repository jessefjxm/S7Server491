package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.monster.Monster;

public class MonsterGameStats extends GameStats<Monster> {
    public MonsterGameStats(final Monster owner) {
        super(owner);
    }
}