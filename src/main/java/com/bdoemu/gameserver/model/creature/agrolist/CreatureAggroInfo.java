package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.gameserver.model.creature.Creature;

public class CreatureAggroInfo extends AggroInfo {
    private final Creature creature;

    public CreatureAggroInfo(final Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
        return this.creature;
    }
}
