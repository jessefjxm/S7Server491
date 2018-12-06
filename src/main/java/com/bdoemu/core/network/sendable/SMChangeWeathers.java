// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangeWeathers extends SendablePacket<GameClient> {
    private long unk1;
    private long unk2;

    public SMChangeWeathers(final long unk1, final long unk2) {
        this.unk1 = unk1;
        this.unk2 = unk2;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.unk1);
        buffer.writeD(this.unk2);
    }
}
