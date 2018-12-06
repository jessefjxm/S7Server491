// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVaryAdrenalin extends SendablePacket<GameClient> {
    private final int value;

    public SMVaryAdrenalin(final int value) {
        this.value = value;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.value);
    }
}
