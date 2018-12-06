// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

public class HouseVisit {
    private int houseId;
    private long houseObjectId;

    public int getHouseId() {
        return this.houseId;
    }

    public void setHouseId(final int houseId) {
        this.houseId = houseId;
    }

    public long getHouseObjectId() {
        return this.houseObjectId;
    }

    public void setHouseObjectId(final long houseObjectId) {
        this.houseObjectId = houseObjectId;
    }

    public boolean isInHouse(final int houseId, final long houseObjectId) {
        return this.houseId == houseId && this.houseObjectId == houseObjectId;
    }

    public boolean isInHouse(final long houseObjectId) {
        return this.houseObjectId == houseObjectId;
    }

    public boolean isInHouseAny() {
        return this.houseObjectId != 0L && this.houseId != 0;
    }
}
