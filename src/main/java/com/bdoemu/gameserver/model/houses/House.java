// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.HouseData;
import com.bdoemu.gameserver.model.houses.enums.EHouseReceipeType;
import com.bdoemu.gameserver.model.houses.templates.HouseInfoT;
import com.bdoemu.gameserver.model.houses.templates.HouseTransferT;
import com.bdoemu.gameserver.model.houses.templates.ReceipeForTownT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.List;

public class House extends JSONable {
    private final HouseInfoT houseInfoT;
    private ReceipeForTownT receipeForTownT;
    private long craftDate;
    private int level;

    public House(final HouseInfoT houseInfoT, final ReceipeForTownT receipeForTownT) {
        this.houseInfoT = houseInfoT;
        this.receipeForTownT = receipeForTownT;
        this.level = 1;
    }

    public House(final BasicDBObject dbObject) {
        this.houseInfoT = HouseData.getInstance().getHouse(dbObject.getInt("houseId"));
        this.receipeForTownT = HouseData.getInstance().getRecipe(dbObject.getInt("receipeKey"));
        this.level = dbObject.getInt("level");
        this.craftDate = dbObject.getLong("craftDate");
    }

    public static House newHouse(final int houseId, final int receipeKey) {
        final HouseInfoT houseInfoT = HouseData.getInstance().getHouse(houseId);
        if (houseInfoT == null) {
            return null;
        }
        final ReceipeForTownT receipeForTownT = HouseData.getInstance().getRecipe(receipeKey);
        if (receipeForTownT == null) {
            return null;
        }
        return new House(houseInfoT, receipeForTownT);
    }

    public EHouseReceipeType getHouseReceipeType() {
        return this.receipeForTownT.getHouseReceipeType();
    }

    public int getMaxLevel() {
        return this.houseInfoT.getMaxLevel(this.receipeForTownT.getReceipeKey());
    }

    public ReceipeForTownT getReceipeForTownT() {
        return this.receipeForTownT;
    }

    public void setReceipeForTownT(final ReceipeForTownT receipeForTownT) {
        this.receipeForTownT = receipeForTownT;
    }

    public long getCraftDate() {
        return this.craftDate;
    }

    public void setCraftDate(final long craftDate) {
        this.craftDate = craftDate;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getUpgradeCount() {
        return this.level - 1;
    }

    public HouseInfoT getHouseInfoT() {
        return this.houseInfoT;
    }

    public int getHouseId() {
        return this.houseInfoT.getHouseId();
    }

    public int getReciepeKey() {
        return this.receipeForTownT.getReceipeKey();
    }

    public int getTransferKey() {
        return this.receipeForTownT.getTransferKey();
    }

    public HouseTransferT getTransferT() {
        return HouseData.getInstance().getHouseTransfer(this.getTransferKey(), this.level);
    }

    public List<Integer> getExchangeKeys(final int craftId) {
        for (int i = 0; i < this.level; ++i) {
            if (this.receipeForTownT.getExchangeKeys()[i].contains(craftId)) {
                return this.receipeForTownT.getExchangeKeys()[i];
            }
        }
        return null;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("houseId", (Object) this.getHouseId());
        builder.append("receipeKey", (Object) this.getReciepeKey());
        builder.append("level", (Object) this.getLevel());
        builder.append("craftDate", (Object) this.getCraftDate());
        return builder.get();
    }
}
