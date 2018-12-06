// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMUpdateIntimacy extends SendablePacket<GameClient> {
    private final int npcId;
    private final int intimacyLevel;
    private final int npcSessionId;

    public SMUpdateIntimacy(final int npcId, final int intimacyLevel, final int npcSessionId) {
        this.npcId = npcId;
        this.intimacyLevel = intimacyLevel;
        this.npcSessionId = npcSessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.npcId);
        buffer.writeD(this.intimacyLevel);
        buffer.writeD(this.npcSessionId);
    }
}
