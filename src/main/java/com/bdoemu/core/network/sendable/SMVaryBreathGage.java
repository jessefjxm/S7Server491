// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVaryBreathGage extends SendablePacket<GameClient> {
    private int value;

    public SMVaryBreathGage(final int value) {
        this.value = value;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.value);
    }
}
