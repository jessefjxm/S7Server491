// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMailRemove extends SendablePacket<GameClient> {
    private final long mailId;
    private final boolean result;

    public SMMailRemove(final long mailId, final boolean result) {
        this.mailId = mailId;
        this.result = result;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.mailId);
        buffer.writeC(this.result);
    }
}
