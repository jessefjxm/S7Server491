// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetNpcActionForClientSpawn extends SendablePacket<GameClient> {
    private int unk1;
    private int gameObjectId;

    public SMSetNpcActionForClientSpawn(final int unk1, final int gameObjectId) {
        this.unk1 = unk1;
        this.gameObjectId = gameObjectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.unk1);
        buffer.writeD(this.gameObjectId);
    }
}
