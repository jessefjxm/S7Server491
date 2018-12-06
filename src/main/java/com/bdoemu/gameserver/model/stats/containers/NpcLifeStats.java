package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.npc.Npc;

public class NpcLifeStats extends LifeStats<Npc> {
    public NpcLifeStats(final Npc owner) {
        super(owner, owner.getGameStats().getHp().getIntMaxValue(), owner.getGameStats().getMp().getIntMaxValue());
    }
}