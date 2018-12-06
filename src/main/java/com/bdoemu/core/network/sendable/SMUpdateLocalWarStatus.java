// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;

import java.util.Collection;

public class SMUpdateLocalWarStatus extends SendablePacket<GameClient> {
    private Collection<LocalWarStatus> localWars;

    public SMUpdateLocalWarStatus(final Collection<LocalWarStatus> localWars) {
        this.localWars = localWars;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.localWars.size());
        for (final LocalWarStatus localWarStatus : this.localWars) {
            buffer.writeH(localWarStatus.getServerChannel());
            buffer.writeD(localWarStatus.getPlayerCount());
            buffer.writeC(localWarStatus.getLocalWarStatusType().ordinal());
            buffer.writeQ(localWarStatus.getRemainingLocalWarTime());
            buffer.writeC(localWarStatus.isLimited());
        }
    }
}
