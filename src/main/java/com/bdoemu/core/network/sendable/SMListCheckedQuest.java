// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMListCheckedQuest extends SendablePacket<GameClient> {
    private final byte[] data;

    public SMListCheckedQuest(final byte[] data) {
        this.data = data;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeB(this.data);
    }
}
