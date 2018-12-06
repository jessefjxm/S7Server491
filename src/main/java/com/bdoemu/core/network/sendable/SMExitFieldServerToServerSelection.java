// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMExitFieldServerToServerSelection extends SendablePacket<GameClient> {
    private final int cookie;

    public SMExitFieldServerToServerSelection(final int cookie) {
        this.cookie = cookie;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.cookie);
    }
}
