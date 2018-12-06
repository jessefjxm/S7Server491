// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.CashProductData;
import com.bdoemu.gameserver.model.items.templates.CashItemT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class CashProductBuyCount extends JSONable {
    private final CashItemT cashItemT;
    private int count;
    private long buyDate;

    public CashProductBuyCount(final int productNr, final int count, final long buyDate) {
        this.count = count;
        this.buyDate = buyDate;
        this.cashItemT = CashProductData.getInstance().getTemplate(productNr);
    }

    public CashProductBuyCount(final BasicDBObject dbObject) {
        this.cashItemT = CashProductData.getInstance().getTemplate(dbObject.getInt("productNr"));
        this.count = dbObject.getInt("count");
        this.buyDate = (long) dbObject.getOrDefault((Object) "buyDate", (Object) 0L);
    }

    public CashItemT getCashItemT() {
        return this.cashItemT;
    }

    public int getCount() {
        return this.count;
    }

    public int getProductNr() {
        return this.cashItemT.getProductNo();
    }

    public long getBuyDate() {
        return this.buyDate;
    }

    public void set(final int count) {
        this.count = count;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("productNr", (Object) this.cashItemT.getProductNo());
        builder.append("count", (Object) this.count);
        builder.append("buyDate", (Object) this.buyDate);
        return builder.get();
    }
}
