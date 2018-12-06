// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMBroadcastArrest extends SendablePacket<GameClient> {
    private final String killer;
    private final String arrested;

    public SMBroadcastArrest(final String killer, final String arrested) {
        this.killer = killer;
        this.arrested = arrested;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.killer, 62);
        buffer.writeS((CharSequence) this.arrested, 62);
    }
}
