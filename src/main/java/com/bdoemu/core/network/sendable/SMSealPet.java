// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSealPet extends SendablePacket<GameClient> {
    private final int gameObjectId;
    private final long petObjId;

    public SMSealPet(final int gameObjectId, final long petObjId) {
        this.gameObjectId = gameObjectId;
        this.petObjId = petObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeQ(this.petObjId);
    }
}
