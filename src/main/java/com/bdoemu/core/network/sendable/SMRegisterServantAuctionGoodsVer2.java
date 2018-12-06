// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMRegisterServantAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private Servant servant;

    public SMRegisterServantAuctionGoodsVer2(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeC(this.servant.getServantState().ordinal());
        buffer.writeQ(22158322669L);
        buffer.writeQ(22244722669L);
    }
}
