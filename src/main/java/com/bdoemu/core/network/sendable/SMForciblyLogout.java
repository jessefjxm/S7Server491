// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMForciblyLogout extends SendablePacket<GameClient> {
    private final int id;

    public SMForciblyLogout(final int id) {
        this.id = id;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.id);
    }
}
