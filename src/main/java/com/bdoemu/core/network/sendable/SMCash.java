// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.MainServer;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCash extends SendablePacket<GameClient> {
    private final long count;

    public SMCash(final long accountId) {
        this.count = MainServer.getRmi().getCash(accountId);
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.count);
    }
}
