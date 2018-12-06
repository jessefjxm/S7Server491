// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMInventorySlotCount extends SendablePacket<GameClient> {
    private final int slotSize;

    public SMInventorySlotCount(final int slotSize) {
        this.slotSize = slotSize;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.slotSize);
    }
}
