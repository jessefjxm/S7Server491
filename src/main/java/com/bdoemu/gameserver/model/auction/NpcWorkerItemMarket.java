package com.bdoemu.gameserver.model.auction;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerPassiveSkillT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class NpcWorkerItemMarket extends JSONable {
    private final long objectId;
    private final long accountId;
    private final long registeredDate;
    private final NpcWorker npcWorker;
    private long price;
    private boolean isSold;

    public NpcWorkerItemMarket(final NpcWorker npcWorker, final long accountId) {
        this.isSold = false;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.ItemMarket);
        this.npcWorker = npcWorker;
        this.accountId = accountId;
        this.registeredDate = GameTimeService.getServerTimeInMillis();
        this.price = npcWorker.getPlantWorkerT().getSellPrices()[npcWorker.getLevel()];
        for (final PlantWorkerPassiveSkillT passiveSkillT : npcWorker.getPassiveSkills()) {
            this.price += passiveSkillT.getSkillPrice();
        }
    }

    public NpcWorkerItemMarket(final BasicDBObject basicDBObject) {
        this.isSold = false;
        this.objectId = basicDBObject.getLong("_id");
        this.accountId = basicDBObject.getLong("accountId");
        this.npcWorker = new NpcWorker((BasicDBObject) basicDBObject.get("npcWorker"));
        this.registeredDate = basicDBObject.getLong("registeredDate");
        this.price = basicDBObject.getLong("price");
        this.isSold = basicDBObject.getBoolean("isSold");
    }

    public boolean isSold() {
        return this.isSold;
    }

    public void setSold(final boolean sold) {
        this.isSold = sold;
    }

    public NpcWorker getNpcWorker() {
        return this.npcWorker;
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

    public long getRegisteredDate() {
        return this.registeredDate;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("npcWorker", this.npcWorker.toDBObject());
        builder.append("registeredDate", this.registeredDate);
        builder.append("price", this.price);
        builder.append("isSold", this.isSold);
        return builder.get();
    }
}
