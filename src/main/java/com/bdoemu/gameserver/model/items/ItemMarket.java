// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ItemMarket extends JSONable {
    private Item item;
    private long accountId;
    private long objectId;
    private long price;
    private long revenue;
    private long totalCount;
    private long lastPrice;
    private long registredDate;
    private long expirationDate;
    private long partyId;
    private int territoryKey;

    public ItemMarket(final long accountId, final long objectId, final Item item, final long count, final long price, final int territoryKey, final long partyId) {
        this.accountId = accountId;
        this.partyId = partyId;
        this.objectId = objectId;
        this.item = item;
        this.totalCount = count;
        this.price = price;
        this.territoryKey = territoryKey;
        this.registredDate = GameTimeService.getServerTimeInSecond();
        this.expirationDate = GameTimeService.getServerTimeInSecond() + 604800L;
    }

    public ItemMarket(final BasicDBObject obj) {
        this.item = new Item((BasicDBObject) obj.get("item"));
        this.objectId = obj.getLong("_id");
        this.accountId = obj.getLong("accountId");
        this.registredDate = obj.getLong("marketRegistredDate");
        this.expirationDate = obj.getLong("marketExpirationDate");
        this.revenue = obj.getLong("revenue");
        this.totalCount = obj.getLong("totalCount");
        this.price = obj.getLong("price");
        this.territoryKey = obj.getInt("territoryKey");
    }

    public static ItemMarket newItemMarket(final long accountId, final Item item, final long count, final long price, final int territoryKey, final long partyId) {
        return new ItemMarket(accountId, GameServerIDFactory.getInstance().nextId(GSIDStorageType.ItemMarket), item, count, price, territoryKey, partyId);
    }

    public boolean isWaiting() {
        return this.registredDate + ItemMarketConfig.REGISTER_WAITING_TICK / 1000 > GameTimeService.getServerTimeInSecond();
    }

    public long getLastPrice() {
        return this.lastPrice;
    }

    public void setLastPrice(final long lastPrice) {
        this.lastPrice = lastPrice;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public synchronized boolean addCount(final long count) {
        return this.item.addCount(count);
    }

    public int getTerritoryKey() {
        return this.territoryKey;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public synchronized boolean addRevenue(final long revenue) {
        if (this.revenue + revenue < 0L) {
            return false;
        }
        this.revenue += revenue;
        return true;
    }

    public long getPartyId() {
        return this.partyId;
    }

    public long getRevenue() {
        return this.revenue;
    }

    public long getExpirationDate() {
        return this.expirationDate;
    }

    public boolean isExpired() {
        return GameTimeService.getServerTimeInSecond() > this.expirationDate;
    }

    public long getRegisteredDate() {
        return this.registredDate;
    }

    public long getItemPrice() {
        return this.price;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public Item getItem() {
        return this.item;
    }

    public int getItemId() {
        return this.item.getItemId();
    }

    public int getEnchantLevel() {
        return this.item.getEnchantLevel();
    }

    public long getCount() {
        return this.item.getCount();
    }

    public ItemTemplate getTemplate() {
        return this.item.getTemplate();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", (Object) this.objectId);
        builder.append("accountId", (Object) this.accountId);
        builder.append("marketRegistredDate", (Object) this.registredDate);
        builder.append("marketExpirationDate", (Object) this.expirationDate);
        builder.append("revenue", (Object) this.revenue);
        builder.append("totalCount", (Object) this.totalCount);
        builder.append("price", (Object) this.price);
        builder.append("territoryKey", (Object) this.territoryKey);
        builder.append("item", (Object) this.item.toDBObject());
        return builder.get();
    }
}
