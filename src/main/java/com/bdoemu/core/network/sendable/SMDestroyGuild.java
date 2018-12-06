// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMDestroyGuild extends SendablePacket<GameClient> {
    private final long objectId;
    private final String name;

    public SMDestroyGuild(final long objectId, final String name) {
        this.objectId = objectId;
        this.name = name;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeS((CharSequence) this.name, 62);
    }
}
