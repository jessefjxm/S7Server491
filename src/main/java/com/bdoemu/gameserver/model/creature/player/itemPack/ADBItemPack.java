package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public abstract class ADBItemPack extends ItemPack {
    protected final Stat expandStat;
    protected final ConcurrentLinkedQueue<Item> backpack;
    private Playable owner;

    public ADBItemPack(final EItemStorageLocation locationType, final BasicDBObject dbObject, final Playable playable) {
        super(locationType);
        this.backpack = new ConcurrentLinkedQueue<>();
        this.owner = playable;
        if (locationType.isGuildWarehouse())
            this.expandStat = new Stat(this.owner, new BaseElement(50));
        else
            this.expandStat = new Stat(this.owner, new BaseElement(dbObject.getInt("expandSize")));
        final BasicDBList itemsDB = (BasicDBList) dbObject.get("items");
        int index = 1;
        for (final Object itemDBObj : itemsDB) {
            final BasicDBObject dbItem = (BasicDBObject) itemDBObj;
            if (dbItem != null) {
                if (ItemData.getInstance().getItemTemplate(dbItem.getInt("itemId")) == null) {
                    continue;
                }
                final Item item = new Item(dbItem);
                int slotIndex = dbItem.getInt("index");
                item.setStorageLocation(locationType);
                if (item.getTemplate().getItemActionNumber() > 0) {
                    this.backpack.add(item);
                }
                if (locationType.isWarehouse() && slotIndex > 0) {
                    slotIndex = index++;
                }
                item.setSlotIndex(slotIndex);
                this.itemMap.put(slotIndex, item);
                if (locationType.isWarehouse() || locationType.isGuildWarehouse() || locationType.isCashInventory()) {
                    continue;
                }
                this.owner.getGameStats().getWeight().addWeight(item.getTemplate().getWeight() * item.getCount());
            }
        }
        final BasicDBList expandElementsDB = (BasicDBList) dbObject.get("expandElements");
        for (final Object expandDBObj : expandElementsDB) {
            if (expandDBObj == null) {
                continue;
            }
            final BasicDBObject dbItem2 = (BasicDBObject) expandDBObj;
            final ExpandElement expandElement = new ExpandElement(dbItem2);
            if (expandElement.getEndTime() <= System.currentTimeMillis()) {
                continue;
            }
            this.expandStat.addElement(expandElement);
        }
    }

    public ADBItemPack(final EItemStorageLocation locationType, final Stat expandStat, final Playable playable) {
        super(locationType);
        this.backpack = new ConcurrentLinkedQueue<>();
        this.expandStat = expandStat;
        this.owner = playable;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("expandSize", this.getBaseExpandSize());
        final BasicDBList expandElementsList = this.expandStat.getElements().stream().map(JSONable::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        builder.append("expandElements", expandElementsList);
        final BasicDBList itemsDB = new BasicDBList();
        for (final int key : this.getItemMap().keySet()) {
            final Item item2 = this.getItem(key);
            if (item2 == null) {
                continue;
            }
            final BasicDBObject dbItem = (BasicDBObject) item2.toDBObject();
            dbItem.put("index", key);
            itemsDB.add(dbItem);
        }
        builder.append("items", itemsDB);
        return builder.get();
    }

    public int getBackpackSize() {
        return this.backpack.size();
    }

    public Playable getOwner() {
        return this.owner;
    }

    @Override
    public int getExpandSize() {
        return (this.expandStat.getIntMaxValue() > this.getMaxExpandSize()) ? this.getMaxExpandSize() : this.expandStat.getIntMaxValue();
    }

    @Override
    public int getBaseExpandSize() {
        return this.expandStat.getBase().getIntValue();
    }
}
