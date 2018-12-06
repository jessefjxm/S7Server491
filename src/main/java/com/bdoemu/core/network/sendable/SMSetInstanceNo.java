// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetInstanceNo extends SendablePacket<GameClient> {
    private final int summonSessionId;
    private final long partyCache;

    public SMSetInstanceNo(final int summonSessionId, final long partyCache) {
        this.summonSessionId = summonSessionId;
        this.partyCache = partyCache;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.summonSessionId);
        buffer.writeQ(this.partyCache);
        buffer.writeQ(0L);
    }
}
