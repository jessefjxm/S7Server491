// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;

public class SMUpdateLocalwarState extends SendablePacket<GameClient> {
    private LocalWarStatus localWarStatus;

    public SMUpdateLocalwarState(final LocalWarStatus localWarStatus) {
        this.localWarStatus = localWarStatus;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.localWarStatus.getLocalWarStatusType().ordinal());
        buffer.writeQ(this.localWarStatus.getEndLocalWarDate());
    }
}
