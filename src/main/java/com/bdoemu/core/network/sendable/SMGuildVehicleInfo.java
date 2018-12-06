// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMGuildVehicleInfo extends SendablePacket<GameClient> {
    private Servant servant;

    public SMGuildVehicleInfo(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeC(42);
        buffer.writeF(this.servant.getLocation().getX());
        buffer.writeF(this.servant.getLocation().getZ());
        buffer.writeF(this.servant.getLocation().getY());
        buffer.writeC(1);
    }
}
