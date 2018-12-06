// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMItemChange extends SendablePacket<GameClient> {
    private int storageType;
    private int slotIndex;

    public SMItemChange(final int storageType, final int slotIndex) {
        this.storageType = storageType;
        this.slotIndex = slotIndex;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageType);
        buffer.writeC(this.slotIndex);
    }
}
