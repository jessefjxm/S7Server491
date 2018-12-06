package com.bdoemu.gameserver.model.houses;

import com.bdoemu.gameserver.databaseCollections.HouseHoldDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HouseholdController {
    private final ConcurrentHashMap<Long, HouseHold> households;
    private Player owner;

    public HouseholdController(final Player owner) {
        this.owner = owner;
        this.households = HouseHoldDBCollection.getInstance().load(owner);
    }

    public Collection<HouseHold> getHouseHolds(final EFixedHouseType fixedHouseType) {
        return this.households.values().stream().filter(fixedHouse -> fixedHouse.getFixedHouseType() == fixedHouseType).collect(Collectors.toList());
    }

    public int[] getFixedHouseIds() {
        final int[] array = new int[5];
        int index = 0;
        for (final HouseHold houseHold : this.getHouseHolds(EFixedHouseType.House)) {
            array[index++] = houseHold.getCreatureId();
            if (index >= array.length)
                break;
        }
        return array;
    }

    public final HouseHold getHouseHold(final long objectId) {
        return this.households.get(objectId);
    }

    public final void addHouseHold(final HouseHold houseHold) {
        this.households.put(houseHold.getObjectId(), houseHold);
        HouseHoldDBCollection.getInstance().save(houseHold);
    }

    public final boolean removeHouseHold(final long objectId) {
        final boolean result = this.households.remove(objectId) != null;
        if (result) {
            HouseHoldDBCollection.getInstance().delete(objectId);
        }
        return result;
    }

    public void save() {
        HouseHoldDBCollection.getInstance().update(this.households.values());
    }
}
