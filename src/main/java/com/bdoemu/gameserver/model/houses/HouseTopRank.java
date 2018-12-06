// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class HouseTopRank {
    private final int houseId;
    private final ConcurrentHashMap<Long, FixedHouseTop> fixedHouses;

    public HouseTopRank(final int houseId, final ConcurrentHashMap<Long, FixedHouseTop> fixedHouses) {
        this.houseId = houseId;
        this.fixedHouses = fixedHouses;
    }

    public int getHouseId() {
        return this.houseId;
    }

    public FixedHouseTop getTopHouse(final long objectId) {
        return this.fixedHouses.get(objectId);
    }

    public boolean removeFixedHouse(final FixedHouseTop fixedHouseTop) {
        return this.fixedHouses.values().remove(fixedHouseTop);
    }

    public boolean removeFixedHouse(final long houseObjId) {
        return this.fixedHouses.remove(houseObjId) != null;
    }

    public FixedHouseTop getTopFixedHouse() {
        final Optional<FixedHouseTop> fixedHouse = this.fixedHouses.values().stream().max((o1, o2) -> Integer.compare(o1.getInteriorPoints(), o2.getInteriorPoints()));
        return fixedHouse.isPresent() ? fixedHouse.get() : null;
    }

    public void updateToTop(final HouseHold houseHold) {
        if (!this.fixedHouses.containsKey(houseHold.getObjectId())) {
            final FixedHouseTop fixedHouseTop = new FixedHouseTop(houseHold.getAccountId(), houseHold.getObjectId(), houseHold.getInteriorPoints());
            this.fixedHouses.put(fixedHouseTop.getHouseObjId(), fixedHouseTop);
        } else {
            this.fixedHouses.get(houseHold.getObjectId()).setInteriorPoints(houseHold.getInteriorPoints());
        }
    }

    public ConcurrentLinkedQueue<FixedHouseTop> getTop15() {
        return this.getTop(15, null);
    }

    public ConcurrentLinkedQueue<FixedHouseTop> getTop15(final Collection<Long> accounts) {
        return this.getTop(15, accounts);
    }

    public ConcurrentLinkedQueue<FixedHouseTop> getTop(final int limit, final Collection<Long> accounts) {
        return this.fixedHouses.values().stream().filter(o -> accounts == null || accounts.contains(o.getAccountId())).sorted((o1, o2) -> Integer.compare(o2.getInteriorPoints(), o1.getInteriorPoints())).limit(limit).collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    public FixedHouseTop getLowTopFixedHouse() {
        final Optional<FixedHouseTop> fixedHouse = this.fixedHouses.values().stream().min(Comparator.comparing(FixedHouseTop::getInteriorPoints));
        return fixedHouse.isPresent() ? fixedHouse.get() : null;
    }

    public int size() {
        return this.fixedHouses.size();
    }
}
