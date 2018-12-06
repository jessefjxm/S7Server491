package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendReq;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@DatabaseCollection
public class FriendsRequestDBCollection extends ADatabaseCollection<FriendReq, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(FriendsRequestDBCollection.class);

    private FriendsRequestDBCollection(final Class<FriendReq> clazz) {
        super(clazz, "friendsRequest");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Friend, "_id"));
    }

    public static FriendsRequestDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public boolean hasFriendReq(final long accountId, final long reqAccountId) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", accountId);
        filter.put("reqAccountId", reqAccountId);
        return !super.load(filter).isEmpty();
    }

    public List<FriendReq> loadFriendsReq(final long accountId) {
        final List<FriendReq> friendsReq = new ArrayList<FriendReq>();
        try {
            final BasicDBObject where = new BasicDBObject("accountId", accountId);
            final DBCursor curs = this.collection.find(where);
            while (curs.hasNext()) {
                final DBObject obj = curs.next();
                friendsReq.add(new FriendReq((BasicDBObject) obj));
            }
        } catch (MongoException ex) {
            FriendsRequestDBCollection.log.error("Error while loadFriendsReq()", ex);
        }
        return friendsReq;
    }

    private static class Holder {
        static final FriendsRequestDBCollection INSTANCE = new FriendsRequestDBCollection(FriendReq.class);
    }
}
