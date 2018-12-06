// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMListSiegeGuild extends SendablePacket<GameClient> {
    private final int castleId;

    public SMListSiegeGuild(final int castleId) {
        this.castleId = castleId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(this.castleId);
        buffer.writeH(0);
    }
}
