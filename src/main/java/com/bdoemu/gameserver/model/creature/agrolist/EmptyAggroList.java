package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.gameserver.model.creature.Creature;

import java.util.List;

public class EmptyAggroList implements IAggroList {
    @Override
    public void addCreature(final Creature creature) {
    }

    @Override
    public void addDmg(final Creature creature, final double dmg) {
    }

    @Override
    public void clear(final boolean dontKeepLastTarget) {
    }

    @Override
    public Creature getMostDamageCreature() {
        return null;
    }

    @Override
    public Creature getTarget() {
        return null;
    }

    @Override
    public void setTarget(final Creature creature) {
    }

    @Override
    public int getTargetGameObjId() {
        return 0;
    }

    @Override
    public AggroInfo getMostDamage() {
        return null;
    }

    @Override
    public CreatureAggroInfo getMostHate() {
        return null;
    }

    @Override
    public List<Creature> getAggroCreatures() {
        return null;
    }

    @Override
    public CreatureAggroInfo getAggroInfo(final Creature creature) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
