package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.gameserver.model.creature.Creature;

import java.util.List;

public interface IAggroList {
    void addCreature(final Creature p0);

    void addDmg(final Creature p0, final double p1);

    void clear(final boolean p0);

    Creature getMostDamageCreature();

    Creature getTarget();

    void setTarget(final Creature p0);

    int getTargetGameObjId();

    AggroInfo getMostDamage();

    CreatureAggroInfo getMostHate();

    List<Creature> getAggroCreatures();

    CreatureAggroInfo getAggroInfo(final Creature p0);

    boolean isEmpty();
}
