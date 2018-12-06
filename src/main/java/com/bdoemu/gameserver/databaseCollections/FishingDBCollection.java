package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.DatabaseFactory;
import com.bdoemu.gameserver.model.creature.player.fishing.FishingTopRank;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishingDBCollection {
    private static final Logger log = LoggerFactory.getLogger(FishingDBCollection.class);
    private static final String COLLECTION_NAME = "fishing";

    public static void updateFishingRanking(final FishingTopRank fishingTopRank) {
        try {
            final DB db = DatabaseFactory.getInstance().getDatabase();
            final DBCollection collection = db.getCollection(COLLECTION_NAME);
            final QueryBuilder where = QueryBuilder.start("_id");
            where.is("FishingRanking");
            collection.update(where.get(), new BasicDBObject("$set", fishingTopRank.toDBObject()), true, true);
        } catch (MongoException ex) {
            FishingDBCollection.log.error("Error while update FishingTopRank", ex);
        }
    }

    public static BasicDBList loadFishingTopRanking(final int key) {
        try {
            final DB db = DatabaseFactory.getInstance().getDatabase();
            final DBCollection collection = db.getCollection("fishing");
            final BasicDBObject where = new BasicDBObject("_id", "FishingRanking");
            final BasicDBObject fields = new BasicDBObject();
            fields.put(Integer.toString(key), true);
            final BasicDBObject result = (BasicDBObject) collection.findOne(where, fields);
            if (result != null && !result.isEmpty()) {
                return (BasicDBList) result.get(Integer.toString(key));
            }
        } catch (MongoException ex) {
            FishingDBCollection.log.error("Error while load FishingTopRank", ex);
        }
        return null;
    }

    public static void init(final String name) {
        final DB db = DatabaseFactory.getInstance().getDatabase();
        final DBCollection collection = db.getCollection("fishing");
        final BasicDBObject idObject = (BasicDBObject) collection.findOne(new BasicDBObject("_id", name));
        if (idObject == null) {
            final BasicDBObject list = new BasicDBObject();
            list.put("_id", name);
            collection.insert(list);
        }
    }
}
