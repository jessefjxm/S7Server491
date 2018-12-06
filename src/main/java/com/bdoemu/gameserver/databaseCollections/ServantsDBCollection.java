package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@DatabaseCollection
public class ServantsDBCollection extends ADatabaseCollection<Servant, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(ServantsDBCollection.class);

    private ServantsDBCollection(final Class<Servant> clazz) {
        super(clazz, "servants");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Vehicle, "_id"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "servantBag.ServantInventory.items", "objectId"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "servantBag.ServantEquip.items", "objectId"));
    }

    public static ServantsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public ConcurrentHashMap<Long, Servant> load(final Player player) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", player.getAccountId());
        final List<BasicDBObject> criteria = new ArrayList<BasicDBObject>();
        criteria.add(new BasicDBObject("currentOwnerObjectId", -1));
        criteria.add(new BasicDBObject("currentOwnerObjectId", player.getObjectId()));
        filter.put("$or", criteria);
        return super.load(filter);
    }

    public ConcurrentHashMap<Long, Servant> loadPets(final long accountId) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", accountId);
        filter.put("servantType", EServantType.Pet.ordinal());
        filter.put("servantState", EServantState.Stable.ordinal());
        return super.load(filter);
    }

    public boolean isSummonedByPlayer(final long playerObjectId) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("currentOwnerObjectId", playerObjectId);
        filter.put("servantType", new BasicDBObject("$ne", EServantType.Pet.ordinal()));
        return super.exists(filter);
    }

    private static class Holder {
        static final ServantsDBCollection INSTANCE = new ServantsDBCollection(Servant.class);
    }
}
