// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCantFindWhisperPlayer extends SendablePacket<GameClient> {
    private final String name;

    public SMCantFindWhisperPlayer(final String name) {
        this.name = name;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.name, 62);
    }
}
