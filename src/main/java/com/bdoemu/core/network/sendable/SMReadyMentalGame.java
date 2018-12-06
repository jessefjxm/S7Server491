// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMReadyMentalGame extends SendablePacket<GameClient> {
    private final int theme;
    private final int count;

    public SMReadyMentalGame(final int theme, final int count) {
        this.theme = theme;
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.theme);
        buffer.writeH(this.count);
    }
}
