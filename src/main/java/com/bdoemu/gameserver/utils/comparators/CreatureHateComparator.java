package com.bdoemu.gameserver.utils.comparators;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.IAggroList;

import java.util.Comparator;

public class CreatureHateComparator implements Comparator<Creature> {
    private Creature aggroListOwner;

    public CreatureHateComparator(final Creature aggroListOwner) {
        this.aggroListOwner = aggroListOwner;
    }

    @Override
    public int compare(final Creature creature1, final Creature creature2) {
        final IAggroList aggroInfo = this.aggroListOwner.getAggroList();
        final AggroInfo aggro1 = aggroInfo.getAggroInfo(creature1);
        final AggroInfo aggro2 = aggroInfo.getAggroInfo(creature2);
        return Double.compare((aggro1 != null) ? aggro1.getHate() : 0.0, (aggro2 != null) ? aggro2.getHate() : 0.0);
    }
}
