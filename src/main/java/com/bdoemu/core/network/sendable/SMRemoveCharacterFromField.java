// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRemoveCharacterFromField extends SendablePacket<GameClient> {
    private final long object;
    private final long deleteDate;
    private final int removeType;

    public SMRemoveCharacterFromField(final long object, final long deleteDate, final int removeType) {
        this.object = object;
        this.deleteDate = deleteDate;
        this.removeType = removeType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.object);
        buffer.writeQ(this.deleteDate);
        buffer.writeC(this.removeType);
    }
}
