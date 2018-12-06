// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMMaidInfoTotal extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(0);
    }
}
