// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeT;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class HouseLargeCraft extends JSONable {
    private int houseId;
    private int craftId;
    private long[] itemCounts;

    public HouseLargeCraft(final int houseId, final int craftId) {
        this.itemCounts = new long[6];
        this.houseId = houseId;
        this.craftId = craftId;
        final ItemExchangeSourceT itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(craftId);
        int i = 0;
        for (final ItemExchangeT itemTemplate : itemExchangeSourceT.getExchangeItems()) {
            this.itemCounts[i++] = itemTemplate.getCount();
        }
    }

    public HouseLargeCraft(final BasicDBObject dbObject) {
        this.itemCounts = new long[6];
        this.houseId = dbObject.getInt("houseId");
        this.craftId = dbObject.getInt("craftId");
        final BasicDBList countsDB = (BasicDBList) dbObject.get("itemCounts");
        for (int i = 0; i < countsDB.size(); ++i) {
            this.itemCounts[i] = (long) countsDB.get(i);
        }
    }

    public long[] getItemCounts() {
        return this.itemCounts;
    }

    public int getHouseId() {
        return this.houseId;
    }

    public int getCraftId() {
        return this.craftId;
    }

    public boolean updateItem(final int index) {
        if (this.itemCounts[index] <= 0L) {
            return false;
        }
        final long[] itemCounts = this.itemCounts;
        --itemCounts[index];
        return true;
    }

    public boolean isFinish() {
        for (final long itemCount : this.itemCounts) {
            if (itemCount > 0L) {
                return false;
            }
        }
        return true;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("houseId", (Object) this.houseId);
        builder.append("craftId", (Object) this.craftId);
        builder.append("itemCounts", (Object) this.itemCounts);
        return builder.get();
    }
}
