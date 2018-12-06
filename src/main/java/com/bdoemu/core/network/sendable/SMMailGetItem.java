// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMailGetItem extends SendablePacket<GameClient> {
    private final long id;
    private final long count;

    public SMMailGetItem(final long id, final long count) {
        this.id = id;
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.id);
        buffer.writeQ(this.count);
        buffer.writeC(0);
    }
}
