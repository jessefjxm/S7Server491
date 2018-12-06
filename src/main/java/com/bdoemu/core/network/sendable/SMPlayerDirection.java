// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPlayerDirection extends SendablePacket<GameClient> {
    private final int gameObjectId;
    private final int carrierGameObjId;
    private final float x;
    private final float y;
    private final float z;
    private final float cos;
    private final float sin;

    public SMPlayerDirection(final int gameObjectId, final float x, final float y, final float z, final float cos, final float sin, final int carrierGameObjId) {
        this.gameObjectId = gameObjectId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.sin = sin;
        this.carrierGameObjId = carrierGameObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeF(this.cos);
        buffer.writeD(0);
        buffer.writeF(this.sin);
        buffer.writeF(this.x);
        buffer.writeF(this.z);
        buffer.writeF(this.y);
        buffer.writeD(this.carrierGameObjId);
    }
}
