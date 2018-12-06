package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.items.ItemMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DatabaseCollection
public class ItemMarketDBCollection extends ADatabaseCollection<ItemMarket, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(ItemMarketDBCollection.class);

    private ItemMarketDBCollection(final Class<ItemMarket> clazz) {
        super(clazz, "itemMarket");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ItemMarket, "_id"));
    }

    public static ItemMarketDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final ItemMarketDBCollection INSTANCE = new ItemMarketDBCollection(ItemMarket.class);
    }
}
