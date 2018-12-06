// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMSetServantStats extends SendablePacket<GameClient> {
    private final Servant servant;

    public SMSetServantStats(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeD(this.servant.getGameStats().getAccelerationRate().getIntMaxValue());
        buffer.writeD(this.servant.getGameStats().getMaxMoveSpeedRate().getIntMaxValue());
        buffer.writeD(this.servant.getGameStats().getCorneringSpeedRate().getIntMaxValue());
        buffer.writeD(this.servant.getGameStats().getBrakeSpeedRate().getIntMaxValue());
        buffer.writeC(this.servant.getMatingCount());
        buffer.writeD(this.servant.getDeathCount());
        buffer.writeC(this.servant.isImprint());
        buffer.writeC(this.servant.isClearedMatingCount());
        buffer.writeC(this.servant.isClearedDeathCount());
        buffer.writeD(0);
        buffer.writeH(this.servant.getFormIndex());
    }
}
