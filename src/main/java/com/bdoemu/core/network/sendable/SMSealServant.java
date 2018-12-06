// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMSealServant extends SendablePacket<GameClient> {
    private final long servantObjectId;
    private final int servantSessionId;
    private final int servantTownId;
    private final int deathCount;

    public SMSealServant(final Servant servant) {
        this.servantObjectId = servant.getObjectId();
        this.servantSessionId = servant.getGameObjectId();
        this.servantTownId = servant.getRegionId();
        this.deathCount = servant.getDeathCount();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjectId);
        buffer.writeC(0);
        buffer.writeD(this.servantSessionId);
        buffer.writeH(this.servantTownId);
        buffer.writeD(this.deathCount);
    }
}
