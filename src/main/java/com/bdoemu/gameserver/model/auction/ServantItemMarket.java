package com.bdoemu.gameserver.model.auction;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ServantItemMarket extends JSONable {
    private final long objectId;
    private final long accountId;
    private final long registeredDate;
    private final Servant servant;
    private long price;
    private boolean isSold;
    private boolean selfOnly;

    public ServantItemMarket(final Servant servant, final long accountId, final long price, final boolean selfOnly) {
        this.isSold = false;
        this.selfOnly = false;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.ItemMarket);
        this.servant = servant;
        this.accountId = accountId;
        this.selfOnly = selfOnly;
        this.price = price;
        this.registeredDate = GameTimeService.getServerTimeInMillis();
    }

    public ServantItemMarket(final BasicDBObject obj) {
        this.isSold = false;
        this.selfOnly = false;
        this.objectId = obj.getLong("_id");
        this.accountId = obj.getLong("accountId");
        this.registeredDate = obj.getLong("registeredDate");
        this.selfOnly = obj.getBoolean("selfOnly");
        this.price = obj.getLong("price");
        this.servant = new Servant((BasicDBObject) obj.get("servant"));
        this.isSold = obj.getBoolean("isSold");
    }

    public EAuctionRegisterType getAuctionRegisterType() {
        return this.servant.getAuctionRegisterType();
    }

    public long getRegisteredDate() {
        return this.registeredDate;
    }

    public boolean isSelfOnly() {
        return this.selfOnly;
    }

    public boolean isSold() {
        return this.isSold;
    }

    public void setSold(final boolean sold) {
        this.isSold = sold;
    }

    public Servant getServant() {
        return this.servant;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getPrice() {
        return this.price;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("registeredDate", this.registeredDate);
        builder.append("price", this.price);
        builder.append("selfOnly", this.selfOnly);
        builder.append("servant", this.servant.toDBObject());
        builder.append("isSold", this.isSold);
        return builder.get();
    }
}
