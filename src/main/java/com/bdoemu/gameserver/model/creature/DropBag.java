// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DropBag {
    private final Map<Integer, Item> dropMap;
    private EDropBagType dropBagType;
    private int sourceCreatureId;
    private int sourceGameObjectId;
    private long validityTime;

    public DropBag(final List<Item> items, final int sourceGameObjectId, final int sourceCreatureId, final EDropBagType dropBagType) {
        this.dropMap = new ConcurrentHashMap<Integer, Item>();
        this.sourceGameObjectId = -1024;
        this.validityTime = 0L;
        this.dropBagType = dropBagType;
        this.sourceGameObjectId = sourceGameObjectId;
        this.sourceCreatureId = sourceCreatureId;
        int index = 0;
        for (final Item item : items) {
            this.dropMap.put(index++, item);
        }
    }

    public DropBag(final Item item, final long count, final int sourceGameObjectId, final EDropBagType dropBagType) {
        this.dropMap = new ConcurrentHashMap<Integer, Item>();
        this.sourceGameObjectId = -1024;
        this.validityTime = 0L;
        this.dropBagType = dropBagType;
        this.sourceGameObjectId = sourceGameObjectId;
        this.dropMap.put(0, new Item(item.getItemId(), count, item.getEnchantLevel()));
    }

    public boolean remove(final int slotIndex) {
        return this.dropMap.remove(slotIndex) != null;
    }

    public boolean decrease(final int slotIndex, final long count) {
        final Item item = this.dropMap.get(slotIndex);
        if (item == null || !item.addCount(-count)) {
            return false;
        }
        if (item.getCount() <= 0L) {
            this.dropMap.remove(slotIndex);
        }
        return true;
    }

    public int getSourceGameObjectId() {
        return this.sourceGameObjectId;
    }

    public int getSourceCreatureId() {
        return this.sourceCreatureId;
    }

    public Map<Integer, Item> getDropMap() {
        return this.dropMap;
    }

    public boolean isEmpty() {
        return this.dropMap.isEmpty();
    }

    public EDropBagType getDropBagType() {
        return this.dropBagType;
    }

    public long getValidityTime() {
        return this.validityTime;
    }

    public void setValidityTime(final long validityTime) {
        this.validityTime = validityTime;
    }
}
