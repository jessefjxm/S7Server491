package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.enums.EFriendType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@DatabaseCollection
public class FriendsDBCollection extends ADatabaseCollection<Friend, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger((Class) FriendsDBCollection.class);

    private FriendsDBCollection(final Class<Friend> clazz) {
        super(clazz, "friends");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Friend, "_id"));
    }

    public static FriendsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public boolean save(final Friend friend) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.append("accountId", friend.getAccountId());
            where.append("friendAccountId", friend.getFriendAccountId());
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where);
            if (result == null) {
                final BasicDBObject friendDB = (BasicDBObject) friend.toDBObject();
                this.collection.save(friendDB);
                return true;
            }
        } catch (MongoException ex) {
            FriendsDBCollection.log.error("Error while save friend", ex);
        }
        return false;
    }

    public List<Friend> loadFriends(final long accountId) {
        final List<Friend> friends = new ArrayList<>();
        try {
            final BasicDBObject where = new BasicDBObject("accountId", accountId);
            final DBCursor curs = this.collection.find(where);
            while (curs.hasNext()) {
                final DBObject obj = curs.next();
                friends.add(new Friend((BasicDBObject) obj));
            }
        } catch (MongoException ex) {
            FriendsDBCollection.log.error("Error while load friends", ex);
        }
        return friends;
    }

    public boolean hasFriend(final long accountId, final long friendAccountId) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.put("accountId", accountId);
            where.put("friendAccountId", friendAccountId);
            where.put("friendType", EFriendType.MyFriend.name());
            final BasicDBObject fields = new BasicDBObject("_id", true);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where, fields);
            return result != null;
        } catch (MongoException ex) {
            FriendsDBCollection.log.error("Error while has friend", ex);
            return false;
        }
    }

    public Friend loadFriend(final long accountId, final long friendAccountId) {
        try {
            final BasicDBObject where = new BasicDBObject();
            where.append("accountId", accountId);
            where.append("friendAccountId", friendAccountId);
            final BasicDBObject result = (BasicDBObject) this.collection.findOne(where);
            if (result == null || result.isEmpty()) {
                return null;
            }
            return new Friend(result);
        } catch (MongoException ex) {
            FriendsDBCollection.log.error("Error while load friend", ex);
            return null;
        }
    }

    private static class Holder {
        static final FriendsDBCollection INSTANCE = new FriendsDBCollection(Friend.class);
    }
}
