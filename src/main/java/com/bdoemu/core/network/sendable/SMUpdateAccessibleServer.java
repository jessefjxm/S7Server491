// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;

public class SMUpdateAccessibleServer extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH((int) ServerConfig.SERVER_ID);
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeQ(1458151798L);
        buffer.writeQ(1458152704L);
    }
}
