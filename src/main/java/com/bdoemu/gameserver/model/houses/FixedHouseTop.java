// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import com.bdoemu.gameserver.service.FamilyService;
import com.mongodb.BasicDBObject;

public class FixedHouseTop {
    private long accountId;
    private long houseObjId;
    private int interiorPoints;

    public FixedHouseTop(final long accountId, final long houseObjId, final int interiorPoints) {
        this.accountId = accountId;
        this.houseObjId = houseObjId;
        this.interiorPoints = interiorPoints;
    }

    public FixedHouseTop(final BasicDBObject dbObject) {
        this.accountId = dbObject.getLong("accountId");
        this.houseObjId = dbObject.getLong("_id");
        this.interiorPoints = dbObject.getInt("interiorPoints");
    }

    public String getFamily() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public int getInteriorPoints() {
        return this.interiorPoints;
    }

    public void setInteriorPoints(final int interiorPoints) {
        this.interiorPoints = interiorPoints;
    }

    public long getHouseObjId() {
        return this.houseObjId;
    }

    public long getAccountId() {
        return this.accountId;
    }
}
