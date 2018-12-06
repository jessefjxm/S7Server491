package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DatabaseCollection
public class NpcWorkerAuctionDBCollection extends ADatabaseCollection<NpcWorkerItemMarket, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(NpcWorkerAuctionDBCollection.class);

    private NpcWorkerAuctionDBCollection(final Class<NpcWorkerItemMarket> clazz) {
        super(clazz, "npcWorkerItemMarket");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ItemMarket, "_id"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.NpcWorker, "npcWorker", "_id"), new BasicDBObject("isSold", (Object) false));
    }

    public static NpcWorkerAuctionDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final NpcWorkerAuctionDBCollection INSTANCE = new NpcWorkerAuctionDBCollection(NpcWorkerItemMarket.class);
    }
}
