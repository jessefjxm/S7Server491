// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVehicleDropRider extends SendablePacket<GameClient> {
    private final int servantSessionId;
    private final int killerSessionId;

    public SMVehicleDropRider(final int servantSessionId, final int killerSessionId) {
        this.servantSessionId = servantSessionId;
        this.killerSessionId = killerSessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servantSessionId);
        buffer.writeD(this.killerSessionId);
        buffer.writeD(-1750161730);
    }
}
