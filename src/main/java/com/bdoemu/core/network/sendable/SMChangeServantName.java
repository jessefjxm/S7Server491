// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangeServantName extends SendablePacket<GameClient> {
    private String name;
    private long servantObjId;

    public SMChangeServantName(final String name, final long servantObjId) {
        this.name = name;
        this.servantObjId = servantObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
        buffer.writeS((CharSequence) this.name, 62);
    }
}
