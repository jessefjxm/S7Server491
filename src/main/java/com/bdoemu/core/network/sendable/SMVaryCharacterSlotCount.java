// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMVaryCharacterSlotCount extends SendablePacket<GameClient> {
    private final int count;

    public SMVaryCharacterSlotCount(final int count) {
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.count);
    }
}
