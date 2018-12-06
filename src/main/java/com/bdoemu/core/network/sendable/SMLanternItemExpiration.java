// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMLanternItemExpiration extends SendablePacket<GameClient> {
    private int gameObjId;
    private int slotIndex;

    public SMLanternItemExpiration(final int gameObjId, final int slotIndex) {
        this.gameObjId = gameObjId;
        this.slotIndex = slotIndex;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjId);
        buffer.writeC(this.slotIndex);
    }
}
