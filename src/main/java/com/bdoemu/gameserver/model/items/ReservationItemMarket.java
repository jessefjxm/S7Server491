// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ReservationItemMarket extends JSONable {
    private final int itemId;
    private final int enchantLevel;
    private final ETerritoryKeyType territoryKeyType;
    private final long money;
    private final long count;
    private final long accountId;
    private final long objectId;

    public ReservationItemMarket(final int itemId, final int enchantLevel, final ETerritoryKeyType territoryKeyType, final long money, final long count, final long accountId) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.territoryKeyType = territoryKeyType;
        this.money = money;
        this.count = count;
        this.accountId = accountId;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.ItemMarket);
    }

    public ReservationItemMarket(final BasicDBObject basicDBObject) {
        this.objectId = basicDBObject.getLong("_id");
        this.itemId = basicDBObject.getInt("itemId");
        this.enchantLevel = basicDBObject.getInt("enchantLevel");
        this.territoryKeyType = ETerritoryKeyType.valueOf(basicDBObject.getString("territoryKeyType"));
        this.money = basicDBObject.getLong("money");
        this.count = basicDBObject.getLong("count");
        this.accountId = basicDBObject.getLong("accountId");
    }

    public long getCount() {
        return this.count;
    }

    public long getMoney() {
        return this.money;
    }

    public ETerritoryKeyType getTerritoryKeyType() {
        return this.territoryKeyType;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public int getItemId() {
        return this.itemId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("_id", (Object) this.objectId);
        builder.append("itemId", (Object) this.itemId);
        builder.append("enchantLevel", (Object) this.enchantLevel);
        builder.append("territoryKeyType", (Object) this.territoryKeyType.name());
        builder.append("money", (Object) this.money);
        builder.append("count", (Object) this.count);
        builder.append("accountId", (Object) this.accountId);
        return builder.get();
    }
}
