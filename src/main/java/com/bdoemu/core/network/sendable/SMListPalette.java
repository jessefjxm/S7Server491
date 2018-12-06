// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

import java.util.HashMap;
import java.util.Map;

public class SMListPalette extends SendablePacket<GameClient> {
    private HashMap<Integer, Long> dyes;

    public SMListPalette(final HashMap<Integer, Long> dyes) {
        this.dyes = dyes;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeH(this.dyes.size());
        for (final Map.Entry<Integer, Long> entry : this.dyes.entrySet()) {
            buffer.writeD((int) entry.getKey());
            buffer.writeQ((long) entry.getValue());
        }
    }
}
