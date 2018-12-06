// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMShowCutScene extends SendablePacket<GameClient> {
    private final String cutScene;

    public SMShowCutScene(final String cutScene) {
        this.cutScene = cutScene;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.cutScene, 202);
    }
}
