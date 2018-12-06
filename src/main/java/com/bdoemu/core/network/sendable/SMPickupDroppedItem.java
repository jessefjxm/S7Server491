// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPickupDroppedItem extends SendablePacket<GameClient> {
    private final int playerSessionId;
    private final int bodySessionId;
    private final int slotIndex;
    private final int type;
    private final boolean isEmptyDrop;
    private final long count;

    public SMPickupDroppedItem(final int playerSessionId, final int bodySessionId, final int slotIndex, final boolean isEmptyDrop, final int type, final long count) {
        this.playerSessionId = playerSessionId;
        this.bodySessionId = bodySessionId;
        this.slotIndex = slotIndex;
        this.isEmptyDrop = isEmptyDrop;
        this.type = type;
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type);
        buffer.writeD(this.playerSessionId);
        buffer.writeD(this.bodySessionId);
        buffer.writeC(this.slotIndex);
        buffer.writeQ(this.count);
        buffer.writeC(this.isEmptyDrop);
    }
}
