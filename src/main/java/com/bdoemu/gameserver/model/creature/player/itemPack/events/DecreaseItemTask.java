// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class DecreaseItemTask {
    private final int slotIndex;
    private final long count;
    private long objectId;
    private EItemStorageLocation storageLocation;

    public DecreaseItemTask(final int slotIndex, final long count, final EItemStorageLocation storageLocation) {
        this.slotIndex = slotIndex;
        this.count = count;
        this.storageLocation = storageLocation;
    }

    public EItemStorageLocation getStorageLocation() {
        return this.storageLocation;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public long getCount() {
        return this.count;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(final long objectId) {
        this.objectId = objectId;
    }
}
