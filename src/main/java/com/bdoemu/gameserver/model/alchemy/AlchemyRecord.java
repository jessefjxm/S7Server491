package com.bdoemu.gameserver.model.alchemy;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AlchemyRecord extends JSONable {
    private final int[] itemIds;
    private final long[] counts;

    public AlchemyRecord(final BasicDBObject dbObject) {
        this.itemIds = new int[8];
        this.counts = new long[8];
        final BasicDBList itemDBIds = (BasicDBList) dbObject.get("itemIds");
        for (int i = 0; i < itemDBIds.size(); ++i) {
            this.itemIds[i] = (int) itemDBIds.get(i);
        }
        final BasicDBList countsDB = (BasicDBList) dbObject.get("counts");
        for (int j = 0; j < countsDB.size(); ++j) {
            this.counts[j] = (long) countsDB.get(j);
        }
    }

    public AlchemyRecord(final long[][] sts, final int size) {
        this.itemIds = new int[8];
        this.counts = new long[8];
        for (int i = 0; i < size; ++i) {
            this.itemIds[i] = (int) sts[i][0];
            this.counts[i] = sts[i][1];
        }
    }

    public int[] getItemIds() {
        return this.itemIds;
    }

    public long[] getCounts() {
        return this.counts;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("itemIds", this.itemIds);
        builder.append("counts", this.counts);
        return builder.get();
    }
}
