package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.FixedHouseTop;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

@DatabaseCollection
public class HouseHoldDBCollection extends ADatabaseCollection<HouseHold, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(HouseHoldDBCollection.class);

    private HouseHoldDBCollection(final Class<HouseHold> clazz) {
        super(clazz, "households");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Household, "_id"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.HouseInstallation, "installations", "objectId"));
    }

    public static HouseHoldDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public ConcurrentHashMap<Long, FixedHouseTop> loadTopFixedHouses(final int houseId) {
        final ConcurrentHashMap<Long, FixedHouseTop> fixedHouses = new ConcurrentHashMap<>();
        try {
            final BasicDBObject where = new BasicDBObject("creatureId", houseId);
            final BasicDBObject fields = new BasicDBObject();
            fields.put("_id", true);
            fields.put("accountId", true);
            fields.put("creatureId", true);
            fields.put("interiorPoints", true);
            try (final DBCursor dbCursor = this.collection.find(where, fields)) {
                if (dbCursor.size() > 0) {
                    while (dbCursor.hasNext()) {
                        final BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
                        final FixedHouseTop fixedHouseTop = new FixedHouseTop(dbObject);
                        fixedHouses.put(fixedHouseTop.getHouseObjId(), fixedHouseTop);
                    }
                }
            }
            return fixedHouses;
        } catch (MongoException ex) {
            HouseHoldDBCollection.log.error("Error while calling loadTopFixedHouses", ex);
            return null;
        }
    }

    public ConcurrentHashMap<Long, HouseHold> loadFixedHouses(final long accountId) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", accountId);
        return super.load(filter);
    }

    public ConcurrentHashMap<Long, HouseHold> load(final Player player) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", player.getAccountId());
        return super.load(filter);
    }

    private static class Holder {
        static final HouseHoldDBCollection INSTANCE = new HouseHoldDBCollection(HouseHold.class);
    }
}
