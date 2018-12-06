// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPlantWithdraw extends SendablePacket<GameClient> {
    private final int waypointKey;

    public SMPlantWithdraw(final int waypointKey) {
        this.waypointKey = waypointKey;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.waypointKey);
    }
}
