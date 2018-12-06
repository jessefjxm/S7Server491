// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMovePlayerNak extends SendablePacket<GameClient> {
    private double x;
    private double y;
    private double z;
    private EStringTable message;

    public SMMovePlayerNak(final double x, final double y, final double z, final EStringTable message) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = message;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeF(this.x);
        buffer.writeF(this.z);
        buffer.writeF(this.y);
        buffer.writeD(this.message.getHash());
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
