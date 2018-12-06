package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.Creature;

public abstract class LifeStats<T extends Creature> extends JSONable {
    protected final T owner;

    public LifeStats(final T owner, final double hp, final double mp) {
        this.owner = owner;
        owner.getGameStats().getHp().setCurrentHp(hp);
        owner.getGameStats().getMp().setCurrentMp(mp);
    }
}