// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMUnsealServant extends SendablePacket<GameClient> {
    private final Servant servant;

    public SMUnsealServant(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeC(0);
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
    }
}
