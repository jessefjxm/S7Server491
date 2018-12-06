package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.AccountData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@DatabaseCollection
public class AccountsDBCollection extends ADatabaseCollection<AccountData, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(AccountsDBCollection.class);

    private AccountsDBCollection(final Class<AccountData> clazz) {
        super(clazz, "accounts");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "accountBag.Warehouse.*.items", "objectId"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "accountBag.CashInventory.items", "objectId"));
    }

    public static AccountsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public void updateGuildCoolTime(final long accountId, final long guildCoolTime) {
        try {
            final BasicDBObject where = new BasicDBObject("_id", accountId);
            final BasicDBObject update = new BasicDBObject("guildCoolTime", guildCoolTime);
            this.collection.update(where, new BasicDBObject("$set", update), true, true);
        } catch (MongoException ex) {
            AccountsDBCollection.log.error("Error while updateGuildCoolTime()", ex);
        }
    }

    public ConcurrentHashMap<Long, String> loadFamilies() {
        final ConcurrentHashMap<Long, String> families = new ConcurrentHashMap<>();
        final BasicDBObject fields = new BasicDBObject();
        fields.put("_id", true);
        fields.put("family", true);
        try (final DBCursor cursor = this.collection.find(null, fields)) {
            while (cursor.hasNext()) {
                final BasicDBObject basicDBObject = (BasicDBObject) cursor.next();
                families.put(basicDBObject.getLong("_id"), basicDBObject.getString("family"));
            }
        }
        return families;
    }

    public BasicDBObject getAccountTimeById(final long accountId) {
        try {
            final BasicDBObject where = new BasicDBObject("_id", accountId);
            final BasicDBObject fields = new BasicDBObject();
            fields.put("lastLogout", true);
            fields.put("lastLogin", true);
            final BasicDBObject accountResult = (BasicDBObject) this.collection.findOne(where, fields);
            if (accountResult == null || accountResult.isEmpty()) {
                return null;
            }
            return accountResult;
        } catch (MongoException ex) {
            AccountsDBCollection.log.error("Error while getAccountTimeById()", ex);
            return null;
        }
    }

    public int getUpdateCreationCount(final long accountId) {
        try {
            final BasicDBObject idObject = (BasicDBObject) this.collection.findAndModify(new BasicDBObject("_id", accountId), (DBObject) null, (DBObject) null, false, new BasicDBObject("$inc", new BasicDBObject("creationCharacterCount", 1)), true, false);
            return (idObject == null) ? 0 : idObject.getInt("creationCharacterCount");
        } catch (MongoException ex) {
            AccountsDBCollection.log.error("Error while getUpdateCreationCount()", ex);
            return -1;
        }
    }

    public Long getAccountIdByFamily(final String name) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.put("family", Pattern.compile("^" + name + "$", Pattern.CASE_INSENSITIVE));
            final BasicDBObject fields = new BasicDBObject("_id", true);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where, fields);
            if (result != null) {
                return result.getLong("_id");
            }
        } catch (MongoException ex) {
            AccountsDBCollection.log.error("Error while getAccountIdByFamily()", ex);
        }
        return null;
    }

    private static class Holder {
        static final AccountsDBCollection INSTANCE = new AccountsDBCollection(AccountData.class);
    }
}
