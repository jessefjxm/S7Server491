// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.receivable.CMBuyItemAtItemMarket;
import com.bdoemu.core.network.receivable.CMRegisterReservationPurchaseAtItemMarket;
import com.bdoemu.core.network.sendable.SMListItemHeaderAtItemMarket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.databaseCollections.ItemMarketDBCollection;
import com.bdoemu.gameserver.databaseCollections.ItemMarketReservationsDBCollection;
import com.bdoemu.gameserver.databaseCollections.MasterItemMarketDBCollection;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MasterItemMarket extends JSONable {
    private final ItemTemplate template;
    private final int enchantLevel;
    private final ConcurrentLinkedQueue<ItemMarket> items;
    private long count;
    private long lastPrice;
    private long soldCount;
    private long minPrice;
    private long maxPrice;
    private long itemMinPrice;
    private long itemMaxPrice;
    private long _id;
    private ReservationItemMarket reservationItemMarket;

    public MasterItemMarket(final ItemTemplate template, final int enchantLevel) {
        this.items = new ConcurrentLinkedQueue<>();
        this.template = template;
        this.enchantLevel = enchantLevel;
        this.itemMinPrice = this.getBaseItemMinPrice();
        this.itemMaxPrice = this.getBaseItemMaxPrice();
        _id = GameServerIDFactory.getInstance().nextId(GSIDStorageType.MasterItemMarket);
    }

    public MasterItemMarket(final BasicDBObject dbObject) {
        this.items = new ConcurrentLinkedQueue<>();
        _id = dbObject.getLong("_id");
        this.template = ItemData.getInstance().getItemTemplate(dbObject.getInt("itemId"));
        this.enchantLevel = dbObject.getInt("itemEnchantLevel", 0);
        this.itemMinPrice = dbObject.getLong("itemMinPrice", getBaseItemMinPrice());
        this.itemMaxPrice = dbObject.getLong("itemMaxPrice", getBaseItemMaxPrice());
        this.soldCount = dbObject.getLong("marketSoldCount", 0);
        this.minPrice = dbObject.getLong("marketMinPrice", 0);
        this.maxPrice = dbObject.getLong("marketMaxPrice", 0);
        this.lastPrice = dbObject.getLong("marketPrice", 0);
    }

    public Collection<ItemMarket> getItems() {
        new Predicate<ItemMarket>() {
            @Override
            public boolean test(final ItemMarket itemMarket) {
                return false;
            }
        };
        return this.items.stream().filter(itemMarket -> {
            if (itemMarket.isExpired()) {
                this.unregisterItem(itemMarket);
                World.getInstance().broadcastWorldPacket(new SMListItemHeaderAtItemMarket(Collections.singletonList(this), EPacketTaskType.Update));
                return false;
            } else {
                return true;
            }
        }).sorted(Comparator.comparingLong(ItemMarket::getItemPrice)).limit(30L).collect(Collectors.toList());
    }

    public void checkReservationItem(final ItemMarket itemMarket) {
        synchronized (this.items) {
            if (this.reservationItemMarket != null && this.reservationItemMarket.getItemId() == itemMarket.getItemId() && this.reservationItemMarket.getEnchantLevel() == itemMarket.getEnchantLevel() && this.reservationItemMarket.getMoney() == itemMarket.getItemPrice() && itemMarket.getCount() >= this.reservationItemMarket.getCount() && this.reservationItemMarket.getAccountId() != itemMarket.getAccountId()) {
                if (itemMarket.addCount(-this.reservationItemMarket.getCount())) {
                    this.lastPrice = itemMarket.getItemPrice();
                    itemMarket.setLastPrice(itemMarket.getItemPrice());
                    this.soldCount += this.reservationItemMarket.getCount();
                }
                itemMarket.addRevenue(this.reservationItemMarket.getMoney() * this.reservationItemMarket.getCount() * 650000L / 1000000L);
                ItemMarketDBCollection.getInstance().update(itemMarket);
                if (ItemMarketService.getInstance().getReservationItemsMarket(this.reservationItemMarket.getAccountId()).remove(this.reservationItemMarket)) {
                    ItemMarketReservationsDBCollection.getInstance().delete(this.reservationItemMarket.getObjectId());
                }
                MailService.getInstance().sendMail(itemMarket.getAccountId(), 0L, "{3183609639|3119282326}", "{3183609639|990597375}", "{3183609639|199595621|643213191|ItemInfo:Name_" + itemMarket.getItemId() + "|2207029135|" + this.reservationItemMarket.getCount() + "}");
                MailService.getInstance().sendMail(this.reservationItemMarket.getAccountId(), -1L, "{3183609639|2083153775}", "{3183609639|2865461740}", "{3183609639|753007990}", new Item(itemMarket.getItem(), this.reservationItemMarket.getCount()));
                this.reservationItemMarket = null;
            }
        }
    }

    public void setReservationItemMarket(final ReservationItemMarket reservationItemMarket) {
        this.reservationItemMarket = reservationItemMarket;
    }

    public boolean cancelReservationPurchaseAtItemMarket(final Player player) {
        synchronized (this.items) {
            if (this.reservationItemMarket != null && this.reservationItemMarket.getAccountId() == player.getAccountId()) {
                ItemMarketService.getInstance().getReservationItemsMarket(player.getAccountId()).remove(this.reservationItemMarket);
                ItemMarketReservationsDBCollection.getInstance().delete(this.reservationItemMarket.getObjectId());
                this.reservationItemMarket = null;
            }
        }
        return true;
    }

    public boolean registerReservationAtItemMarket(final Player player, final int itemId, final int enchantLevel, final ETerritoryKeyType territoryKeyType, final long money, final long count) {
        if (!ItemMarketService.getInstance().getReservationItemsMarket(player.getAccountId()).isEmpty()) {
            player.sendPacket(new SMNak(EStringTable.eErrNoItemMarketReservationCountMax, CMRegisterReservationPurchaseAtItemMarket.class));
            return false;
        }
        final List<ReservationItemMarket> reservationItemsMarket = new ArrayList<ReservationItemMarket>();
        synchronized (this.items) {
            if (!this.items.isEmpty()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoReservationPurchaseIsInvalid, CMRegisterReservationPurchaseAtItemMarket.class));
                return false;
            }
            if (this.reservationItemMarket != null) {
                player.sendPacket(new SMNak(EStringTable.eErrNoItemMarketReservationCountMax, CMRegisterReservationPurchaseAtItemMarket.class));
                return false;
            }
            reservationItemsMarket.add(this.reservationItemMarket = new ReservationItemMarket(itemId, enchantLevel, territoryKeyType, money, count, player.getAccountId()));
            ItemMarketReservationsDBCollection.getInstance().save(this.reservationItemMarket);
        }
        ItemMarketService.getInstance().getRservationItemMarketMap().put(player.getAccountId(), reservationItemsMarket);
        return true;
    }

    public void addItem(final ItemMarket itemMarket, boolean isInit) {
        synchronized (this.items) {
            this.count += itemMarket.getCount();
            this.items.add(itemMarket);

            if (!isInit) {
                recalculatePrices();
                itemMinPrice = (getBaseItemMinPrice() + itemMarket.getItemPrice()) / 2L;
                itemMaxPrice = getBaseItemMaxPrice() + (this.itemMinPrice - this.getBaseItemMinPrice());
                MasterItemMarketDBCollection.getInstance().save(this);
            }
        }

        if (!isInit)
            World.getInstance().broadcastWorldPacket(new SMListItemHeaderAtItemMarket(Collections.singletonList(this), EPacketTaskType.Update));
    }

    public boolean buyItem(final Player player, final ItemMarket itemMarket, final long count) {
        synchronized (this.items) {
            /*if (itemMarket.getItemPrice() != this.minPrice) {
                player.sendPacket(new SMNak(EStringTable.eErrNoItemCanBuyLowestPriceOnly, CMBuyItemAtItemMarket.class));
                return false;
            }*/
            if (!this.items.contains(itemMarket)) {
                player.sendPacket(new SMNotifyItemMarketInfo(itemMarket, ENotifyItemMarketInfoType.UPDATE));
                player.sendPacket(new SMNak(EStringTable.eErrNoAlreadySoldOutItemAtItemMarket));
                return false;
            }
            if (itemMarket.getCount() < count) {
                player.sendPacket(new SMNotifyItemMarketInfo(itemMarket, ENotifyItemMarketInfoType.UPDATE, itemMarket.getCount()));
                player.sendPacket(new SMNak(EStringTable.eErrNoCountIsInvalid, CMBuyItemAtItemMarket.class));
                return false;
            }
            if (itemMarket.addCount(-count)) {
                this.lastPrice   = itemMarket.getItemPrice();
                itemMarket.setLastPrice(itemMarket.getItemPrice());
                this.soldCount  += count;
                this.count      -= count;

                // If remaining items are 0 and was removed successfully.
                if (itemMarket.getCount() <= 0L && this.items.remove(itemMarket)) {
                    recalculatePrices();
                }

                // When we sell the item, we want to save it with the new price,
                // depending on how much we sold and how much was listed.
                MasterItemMarketDBCollection.getInstance().save(this);
                return true;
            }
            return false;
        }
    }

    public void recalculatePrices() {
        if (this.items.isEmpty()) {
            this.minPrice = 0L;
            this.maxPrice = 0L;
        } else {
            long minPrice = 0L;
            long maxPrice = 0L;
            for (final ItemMarket itemMarket : this.items) {
                if (minPrice == 0L || itemMarket.getItemPrice() < minPrice) {
                    minPrice = itemMarket.getItemPrice();
                }
                if (maxPrice == 0L || itemMarket.getItemPrice() > maxPrice) {
                    maxPrice = itemMarket.getItemPrice();
                }
            }
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }
    }

    public boolean unregisterItem(final ItemMarket itemMarket) {
        synchronized (this.items) {
            if (itemMarket.getRevenue() > 0L) {
                return false;
            }
            final boolean result = this.items.remove(itemMarket);
            if (result) {
                this.count -= itemMarket.getCount();
                recalculatePrices();
                MasterItemMarketDBCollection.getInstance().save(this);
            }
            return result;
        }
    }

    public long getCount() {
        return this.count;
    }

    public int size() {
        return this.items.size();
    }

    public long getSoldCount() {
        return this.soldCount;
    }

    public long getMinPrice() {
        return this.minPrice;
    }

    public long getMaxPrice() {
        return this.maxPrice;
    }

    public long getItemMinPrice() {
        return this.itemMinPrice;
    }

    public long getItemMaxPrice() {
        return this.itemMaxPrice;
    }

    public long getBaseItemMinPrice() {
        long baseItemMinPrice = (long) this.template.getBasePriceForItemMarket() * (long) this.template.getMinestPercentForItemMarket() / 1000000L;
        final int blackCountForItemMarket = this.template.getBlackCountForItemMarket(this.enchantLevel);
        switch (this.template.getItemClassify()) {
            case Armor: {
                baseItemMinPrice += blackCountForItemMarket * 122727;
                break;
            }
            case MainWeapon:
            case SubWeapon: {
                baseItemMinPrice += blackCountForItemMarket * (245455 * 1.5);
                break;
            }
            case Accessory: {
                baseItemMinPrice += blackCountForItemMarket * baseItemMinPrice;
                break;
            }
        }
        final ItemEnchantT itemEnchantT = this.template.getEnchantTemplate(this.enchantLevel);
        if (itemEnchantT != null) {
            baseItemMinPrice += itemEnchantT.getItemMarketAddedPrice();
        }
        return baseItemMinPrice;
    }

    public long getBaseItemMaxPrice() {
        long baseItemMaxPrice = (long) this.template.getBasePriceForItemMarket() + (long) this.template.getBasePriceForItemMarket() * (long) this.template.getMaxestPercentForItemMarket() / 1000000L;
        final int blackCountForItemMarket = this.template.getBlackCountForItemMarket(this.enchantLevel);
        switch (this.template.getItemClassify()) {
            case Armor: {
                baseItemMaxPrice += blackCountForItemMarket * 122727;
                break;
            }
            case MainWeapon:
            case SubWeapon: {
                baseItemMaxPrice += blackCountForItemMarket * (245455 * 1.5);
                break;
            }
            case Accessory: {
                baseItemMaxPrice += blackCountForItemMarket * baseItemMaxPrice;
                break;
            }
        }
        final ItemEnchantT itemEnchantT = this.template.getEnchantTemplate(this.enchantLevel);
        if (itemEnchantT != null) {
            baseItemMaxPrice += itemEnchantT.getItemMarketAddedPrice();
        }
        return baseItemMaxPrice;
    }

    public long getLastPrice() {
        return this.lastPrice;
    }

    public int getItemId() {
        return this.template.getItemId();
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", _id);
        builder.append("itemId", template.getItemId());
        builder.append("itemEnchantLevel", enchantLevel);
        builder.append("itemMinPrice", itemMinPrice);
        builder.append("itemMaxPrice", itemMaxPrice);
        builder.append("marketSoldCount", soldCount);
        builder.append("marketMinPrice", minPrice);
        builder.append("marketMaxPrice", maxPrice);
        builder.append("marketPrice", lastPrice);
        return builder.get();
    }
}
