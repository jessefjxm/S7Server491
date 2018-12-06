// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMRecoveryServant extends SendablePacket<GameClient> {
    private final Servant servant;

    public SMRecoveryServant(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeC(0);
        buffer.writeF(this.servant.getGameStats().getHp().getCurrentHp());
        buffer.writeD(this.servant.getGameStats().getMp().getCurrentMp());
        buffer.writeC(this.servant.getServantState().ordinal());
        buffer.writeH(this.servant.getRegionId());
    }
}
