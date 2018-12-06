package com.bdoemu.gameserver.model.creature.player.social.friends;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class FriendReq extends JSONable {
    private final long accountId;
    private final long reqAccountId;
    private final long objectId;
    private final long recievedTime;

    public FriendReq(final BasicDBObject dbObject) {
        this.objectId = dbObject.getLong("_id");
        this.accountId = dbObject.getLong("accountId");
        this.reqAccountId = dbObject.getLong("reqAccountId");
        this.recievedTime = dbObject.getLong("recievedTime");
    }

    public FriendReq(final long accountId, final long reqAccountId) {
        this.accountId = accountId;
        this.reqAccountId = reqAccountId;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Friend);
        this.recievedTime = GameTimeService.getServerTimeInMillis();
    }

    public long getRecievedTime() {
        return this.recievedTime;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public String getReqFamily() {
        return FamilyService.getInstance().getFamily(this.reqAccountId);
    }

    public long getReqAccountId() {
        return this.reqAccountId;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("reqAccountId", this.reqAccountId);
        builder.append("recievedTime", this.recievedTime);
        return builder.get();
    }
}
