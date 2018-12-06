// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangeGuildJoinMode extends SendablePacket<GameClient> {
    private final int gameObjectId;
    private final int modeId;

    public SMChangeGuildJoinMode(final int gameObjectId, final int modeId) {
        this.gameObjectId = gameObjectId;
        this.modeId = modeId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeD(this.modeId);
    }
}
