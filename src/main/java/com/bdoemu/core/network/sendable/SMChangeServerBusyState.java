// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EServerBusyState;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;

public class SMChangeServerBusyState extends SendablePacket<GameClient> {
    private EServerBusyState oldState;
    private EServerBusyState newState;

    public SMChangeServerBusyState(final EServerBusyState oldState, final EServerBusyState newState) {
        this.oldState = oldState;
        this.newState = newState;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH((int) ServerConfig.SERVER_ID);
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeC(this.oldState.ordinal());
        buffer.writeC(this.newState.ordinal());
        buffer.writeC(0);
        buffer.writeC(0);
    }
}
