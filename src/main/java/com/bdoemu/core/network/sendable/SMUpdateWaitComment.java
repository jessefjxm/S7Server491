// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMUpdateWaitComment extends SendablePacket<GameClient> {
    private final int gameObjectId;
    private final int updateNr;
    private final long accountId;
    private final byte type;
    private final String message;

    public SMUpdateWaitComment(final int gameObjectId, final long accountId, final int updateNr, final byte type, final String message) {
        this.gameObjectId = gameObjectId;
        this.accountId = accountId;
        this.updateNr = updateNr;
        this.type = type;
        this.message = message;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeQ(this.accountId);
        buffer.writeD(this.updateNr);
        buffer.writeC((int) this.type);
        buffer.writeS((CharSequence) this.message, 702);
    }
}
