package com.bdoemu.gameserver.utils.comparators;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;

import java.util.Comparator;

public class CreatureNearestComparator implements Comparator<Creature> {
    private Location originLocation;

    public CreatureNearestComparator(final Location originLocation) {
        this.originLocation = originLocation;
    }

    @Override
    public int compare(final Creature o1, final Creature o2) {
        return -Double.compare(MathUtils.getDistance(this.originLocation, o1.getLocation()), MathUtils.getDistance(this.originLocation, o2.getLocation()));
    }
}
