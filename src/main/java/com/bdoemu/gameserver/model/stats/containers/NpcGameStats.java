package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.npc.Npc;

public class NpcGameStats extends GameStats<Npc> {
    public NpcGameStats(final Npc owner) {
        super(owner);
    }
}