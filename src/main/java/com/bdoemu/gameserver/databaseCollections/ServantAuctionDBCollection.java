package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@DatabaseCollection
public class ServantAuctionDBCollection extends ADatabaseCollection<ServantItemMarket, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(ServantAuctionDBCollection.class);

    private ServantAuctionDBCollection(final Class<ServantItemMarket> clazz) {
        super(clazz, "servantItemMarket");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ItemMarket, "_id"));
        final BasicDBObject filter = new BasicDBObject();
        final List<BasicDBObject> obj = new ArrayList<>();
        obj.add(new BasicDBObject("isSold", false));
        obj.add(new BasicDBObject("servant.auctionRegisterType", 3));
        filter.put("$or", obj);
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Vehicle, "servant", "_id"), filter);
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "servant.servantBag.ServantInventory.items", "objectId"), filter);
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "servant.servantBag.ServantEquip.items", "objectId"), filter);
    }

    public static ServantAuctionDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final ServantAuctionDBCollection INSTANCE = new ServantAuctionDBCollection(ServantItemMarket.class);
    }
}
