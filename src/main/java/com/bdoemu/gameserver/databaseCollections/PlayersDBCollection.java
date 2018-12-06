package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.FamilyService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@DatabaseCollection
public class PlayersDBCollection extends ADatabaseCollection<Player, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(PlayersDBCollection.class);

    public PlayersDBCollection(final Class<Player> clazz) {
        super(clazz, "players");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.PLAYER, "_id"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "playerBag.Inventory.items", "objectId"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "playerBag.Equipments.items", "objectId"));
    }

    public static PlayersDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public void updateDeletionDate(final long objectId, final long deletionDate) {
        try {
            final BasicDBObject where = new BasicDBObject("_id", objectId);
            final BasicDBObject update = new BasicDBObject("deletionDate", deletionDate);
            this.collection.update(where, new BasicDBObject("$set", update), true, true);
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while updateDeletionDate()", ex);
        }
    }

    public ConcurrentHashMap<Long, Player> loadByAccountId(final long accountId) {
        final BasicDBObject filter = new BasicDBObject("accountId", accountId);
        return super.load(filter);
    }

    public Map<Long, Long> loadDeletionInfo(final long accountId) {
        final Map<Long, Long> deletionInfo = new HashMap<>();
        try {
            final BasicDBObject where = new BasicDBObject("accountId", accountId);
            try (final DBCursor cursor = this.collection.find(where)) {
                while (cursor.hasNext()) {
                    final BasicDBObject playerDB = (BasicDBObject) cursor.next();
                    deletionInfo.put(playerDB.getLong("_id"), playerDB.getLong("deletionDate"));
                }
            }
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while loadDeletionInfo()", ex);
        }
        return deletionInfo;
    }

    public ConcurrentHashMap<Long, BasicDBObject> loadCharacterDataByAccountId(final long accountId) {
        final ConcurrentHashMap<Long, BasicDBObject> result = new ConcurrentHashMap<>();
        try {
            final BasicDBObject where = new BasicDBObject("accountId", accountId);
            try (final DBCursor cursor = this.collection.find(where)) {
                while (cursor.hasNext()) {
                    final BasicDBObject playerDB = (BasicDBObject) cursor.next();
                    result.put(playerDB.getLong("_id"), playerDB);
                }
            }
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while loadCharacterDataByAccountId()", ex);
        }
        return result;
    }

    public BasicDBObject getLifeExperience(final long id) {
        final BasicDBObject where = new BasicDBObject();
        where.put("_id", id);
        final BasicDBObject fields = new BasicDBObject();
        fields.put("accountId", true);
        fields.put("lifeExperience", true);
        return (BasicDBObject) this.collection.findOne(where, fields);
    }

    public void updateLifeExperience(final BasicDBObject basicDBObject) {
        try {
            final BasicDBObject where = new BasicDBObject("_id", basicDBObject.get("_id"));
            final BasicDBObject update = new BasicDBObject("lifeExperience", basicDBObject.get("lifeExperience"));
            this.collection.update(where, new BasicDBObject("$set", update), true, true);
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while updateLifeExperience()", ex);
        }
    }

    public Long getAccountId(final String name) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.put("name", Pattern.compile("^" + name + "$", Pattern.CASE_INSENSITIVE));
            final BasicDBObject fields = new BasicDBObject("accountId", true);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where, fields);
            if (result != null) {
                return result.getLong("accountId");
            }
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while getAccountId()", ex);
        }
        return null;
    }

    public Long getPlayerId(final String name) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.put("name", Pattern.compile("^" + name + "$", Pattern.CASE_INSENSITIVE));
            final BasicDBObject fields = new BasicDBObject("_id", true);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where, fields);
            if (result != null) {
                return result.getLong("_id");
            }
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while getPlayerId()", ex);
        }
        return null;
    }

    public String getFamilyByName(final String name) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.put("name", Pattern.compile("^" + name + "$", Pattern.CASE_INSENSITIVE));
            final BasicDBObject fields = new BasicDBObject("accountId", true);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where, fields);
            if (result != null) {
                return FamilyService.getInstance().getFamily(result.getLong("accountId"));
            }
        } catch (MongoException ex) {
            PlayersDBCollection.log.error("Error while getFamilyByName()", ex);
        }
        return null;
    }

    private static class Holder {
        static final PlayersDBCollection INSTANCE = new PlayersDBCollection(Player.class);
    }
}
