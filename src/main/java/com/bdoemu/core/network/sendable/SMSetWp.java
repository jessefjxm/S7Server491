// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetWp extends SendablePacket<GameClient> {
    private final int currentWp;
    private final int baseWp;

    public SMSetWp(final int currentWp, final int baseWp) {
        this.currentWp = currentWp;
        this.baseWp = baseWp;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.currentWp);
        buffer.writeH(this.baseWp);
    }
}
