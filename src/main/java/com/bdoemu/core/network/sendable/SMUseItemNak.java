// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMUseItemNak extends SendablePacket<GameClient> {
    private final EItemStorageLocation storageLocation;
    private final int slotIndex;
    private final EStringTable message;

    public SMUseItemNak(final EItemStorageLocation storageLocation, final int slotIndex, final EStringTable message) {
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.message = message;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageLocation.getId());
        buffer.writeC(this.slotIndex);
        buffer.writeD(this.message.getHash());
    }
}
