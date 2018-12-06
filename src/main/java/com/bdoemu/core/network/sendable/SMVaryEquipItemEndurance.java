// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVaryEquipItemEndurance extends SendablePacket<GameClient> {
    private final int sessionId;
    private final int slotIndex;
    private final int endurance;

    public SMVaryEquipItemEndurance(final int sessionId, final int slotIndex, final int endurance) {
        this.sessionId = sessionId;
        this.slotIndex = slotIndex;
        this.endurance = endurance;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeC(this.slotIndex);
        buffer.writeH(this.endurance);
    }
}
