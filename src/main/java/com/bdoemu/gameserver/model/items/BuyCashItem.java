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

public class BuyCashItem extends JSONable {
    private final CashItemT cashItemT;
    private int productNr;
    private long count;
    private long price;
    private String name;
    private String family;

    public BuyCashItem(final BasicDBObject basicDBObject) {
        this.productNr = basicDBObject.getInt("productNr");
        this.count = basicDBObject.getLong("count");
        this.cashItemT = CashProductData.getInstance().getTemplate(this.productNr);
    }

    public BuyCashItem(final int productNr, final long count, final long price, final String name, final String family) {
        this.productNr = productNr;
        this.count = count;
        this.price = price;
        this.name = name;
        this.family = family;
        this.cashItemT = CashProductData.getInstance().getTemplate(productNr);
    }

    public boolean addCount(final long count) {
        if (this.count + count < 0L) {
            return false;
        }
        this.count += count;
        return true;
    }

    public CashItemT getCashItemT() {
        return this.cashItemT;
    }

    public int getProductNr() {
        return this.productNr;
    }

    public long getCount() {
        return this.count;
    }

    public String getFamily() {
        return this.family;
    }

    public String getName() {
        return this.name;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("productNr", (Object) this.productNr);
        builder.append("count", (Object) this.count);
        return builder.get();
    }
}
