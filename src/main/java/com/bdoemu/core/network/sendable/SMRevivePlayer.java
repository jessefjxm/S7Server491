// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRevivePlayer extends SendablePacket<GameClient> {
    private int gameObjId;

    public SMRevivePlayer(final int gameObjId) {
        this.gameObjId = gameObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjId);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
