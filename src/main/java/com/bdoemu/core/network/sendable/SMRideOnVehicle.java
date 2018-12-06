// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMRideOnVehicle extends SendablePacket<GameClient> {
    private Servant servant;
    private Player owner;

    public SMRideOnVehicle(final Player owner) {
        this.owner = owner;
        this.servant = owner.getCurrentVehicle();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD((this.servant != null) ? this.servant.getGameObjectId() : this.owner.getGameObjectId());
        buffer.writeF((this.servant == null) ? this.owner.getLocation().getX() : 0.0);
        buffer.writeF((this.servant == null) ? this.owner.getLocation().getZ() : 0.0);
        buffer.writeF((this.servant == null) ? this.owner.getLocation().getY() : 0.0);
        buffer.writeF((this.servant != null) ? this.servant.getLocation().getX() : 0.0);
        buffer.writeF((this.servant != null) ? this.servant.getLocation().getZ() : 0.0);
        buffer.writeF((this.servant != null) ? this.servant.getLocation().getY() : 0.0);
        buffer.writeD(-1024);
        buffer.writeC(1);
    }
}
