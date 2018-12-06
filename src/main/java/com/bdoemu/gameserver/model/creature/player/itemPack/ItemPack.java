package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class ItemPack extends JSONable {
    public static HashMap<Integer, Integer> staticItems;

    static {
        (ItemPack.staticItems = new HashMap<>()).put(1, 0);
        ItemPack.staticItems.put(6, 0);
        ItemPack.staticItems.put(7, 1);
    }

    protected final ConcurrentSkipListMap<Integer, Item> itemMap;
    protected final EItemStorageLocation locationType;

    public ItemPack(final EItemStorageLocation locationType) {
        this.itemMap = new ConcurrentSkipListMap<>();
        this.locationType = locationType;
    }

    public Item getItemById(final int itemId) {
        for (final Item item : this.itemMap.values()) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public Item getItem(final long objectId) {
        for (final Item item : this.itemMap.values()) {
            if (item.getObjectId() == objectId) {
                return item;
            }
        }
        return null;
    }

    public Integer getItemIndex(final long objectId) {
        for (final Map.Entry<Integer, Item> entry : this.itemMap.entrySet()) {
            if (entry.getValue().getObjectId() == objectId) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Integer getItemIndex(final int itemId, final int enchantLevel) {
        for (final Map.Entry<Integer, Item> entry : this.itemMap.entrySet()) {
            final Item item = entry.getValue();
            if (item.getItemId() == itemId && item.getEnchantLevel() == enchantLevel) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean containsItem(final int slot) {
        return this.itemMap.get(slot) != null;
    }

    public Item getItem(final int slot) {
        return this.itemMap.get(slot);
    }

    public long getItemCount(final int slot) {
        final Item item = this.itemMap.get(slot);
        return (item != null) ? item.getCount() : 0L;
    }

    public Item removeItem(final int slot) {
        return this.itemMap.remove(slot);
    }

    public boolean addItem(final Item item, final int slot) {
        if (item == null || this.itemMap.putIfAbsent(slot, item) != null) {
            return false;
        }
        item.initGameObjectId();
        return true;
    }

    public ConcurrentSkipListMap<Integer, Item> getItemMap() {
        return this.itemMap;
    }

    public Collection<Item> getItemList() {
        return this.itemMap.values();
    }

    public int getItemSize() {
        return this.itemMap.size();
    }

    public boolean isEmpty() {
        return this.itemMap.isEmpty();
    }

    public EItemStorageLocation getLocationType() {
        return this.locationType;
    }

    public boolean hasFreeSlots() {
        return this.freeSlotCount() > 0;
    }

    public abstract int getExpandSize();

    public abstract int getBaseExpandSize();

    public abstract int getMaxExpandSize();

    public int freeSlotCount() {
        return this.getExpandSize() - this.getItemSize();
    }

    public int getDefaultSlotIndex() {
        return 0;
    }
}
