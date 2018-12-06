// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

import java.util.Set;

public class SMAcquiredTitles extends SendablePacket<GameClient> {
    private final Set<Integer> titles;

    public SMAcquiredTitles(final Set<Integer> titles) {
        this.titles = titles;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.titles.size());
        for (final int titleId : this.titles) {
            buffer.writeD(titleId);
            buffer.writeQ(0L);
        }
    }
}
