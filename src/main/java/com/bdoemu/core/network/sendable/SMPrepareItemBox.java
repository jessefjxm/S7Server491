// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMPrepareItemBox extends SendablePacket<GameClient> {
    private final EItemStorageLocation storageType;
    private final int slotIndex;

    public SMPrepareItemBox(final int slotIndex, final EItemStorageLocation storageType) {
        this.slotIndex = slotIndex;
        this.storageType = storageType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageType.getId());
        buffer.writeC(this.slotIndex);
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(0);
    }
}
