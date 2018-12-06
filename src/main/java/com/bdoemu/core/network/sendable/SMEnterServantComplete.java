// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMEnterServantComplete extends SendablePacket<GameClient> {
    private final Servant vehicle;

    public SMEnterServantComplete(final Servant vehicle) {
        this.vehicle = vehicle;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.vehicle.getName(), 62);
        buffer.writeD(this.vehicle.getGameObjectId());
        buffer.writeQ(this.vehicle.getObjectId());
        buffer.writeF(this.vehicle.getLocation().getX());
        buffer.writeF(this.vehicle.getLocation().getZ());
        buffer.writeF(this.vehicle.getLocation().getY());
        buffer.writeH(this.vehicle.getCreatureId());
        buffer.writeQ(-1L);
    }
}
