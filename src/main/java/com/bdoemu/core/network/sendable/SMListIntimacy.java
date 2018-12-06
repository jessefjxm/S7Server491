// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

import java.util.Map;

public class SMListIntimacy extends SendablePacket<GameClient> {
    private final Map<Integer, Integer> intimacyMap;

    public SMListIntimacy(final Map<Integer, Integer> intimacyMap) {
        this.intimacyMap = intimacyMap;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.intimacyMap.size());
        for (final Map.Entry<Integer, Integer> entry : this.intimacyMap.entrySet()) {
            buffer.writeH((int) entry.getKey());
            buffer.writeD((int) entry.getValue());
        }
    }
}
