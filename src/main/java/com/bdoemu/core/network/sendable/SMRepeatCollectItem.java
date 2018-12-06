// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMRepeatCollectItem extends WriteItemInfo {
    private EItemStorageLocation storageLocation;
    private int slotIndex;

    public SMRepeatCollectItem(final EItemStorageLocation storageLocation, final int slotIndex) {
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageLocation.ordinal());
        buffer.writeC(this.slotIndex);
    }
}
