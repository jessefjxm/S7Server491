package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.items.ReservationItemMarket;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@DatabaseCollection
public class ItemMarketReservationsDBCollection extends ADatabaseCollection<ReservationItemMarket, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(ItemMarketReservationsDBCollection.class);

    private ItemMarketReservationsDBCollection(final Class<ReservationItemMarket> clazz) {
        super(clazz, "itemMarketReservations");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ItemMarket, "_id"));
    }

    public static ItemMarketReservationsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public ConcurrentHashMap<Long, List<ReservationItemMarket>> loadReservationItemMarket() {
        final ConcurrentHashMap<Long, List<ReservationItemMarket>> items = new ConcurrentHashMap<>();
        try {
            if (this.collection.count() > 0L) {
                final DBCursor curs = this.collection.find();
                while (curs.hasNext()) {
                    final DBObject obj = curs.next();
                    final ReservationItemMarket itemMarket = new ReservationItemMarket((BasicDBObject) obj);
                    items.computeIfAbsent(itemMarket.getAccountId(), k -> new ArrayList());
                    items.get(itemMarket.getAccountId()).add(itemMarket);
                }
            }
        } catch (MongoException ex) {
            ItemMarketReservationsDBCollection.log.error("Error while load reservations", ex);
        }
        return items;
    }

    private static class Holder {
        static final ItemMarketReservationsDBCollection INSTANCE = new ItemMarketReservationsDBCollection(ReservationItemMarket.class);
    }
}
