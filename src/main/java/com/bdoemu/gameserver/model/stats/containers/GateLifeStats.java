package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.Gate;

public class GateLifeStats extends LifeStats<Gate> {
    public GateLifeStats(final Gate owner) {
        super(owner, owner.getGameStats().getHp().getIntMaxValue(), owner.getGameStats().getMp().getIntMaxValue());
    }
}