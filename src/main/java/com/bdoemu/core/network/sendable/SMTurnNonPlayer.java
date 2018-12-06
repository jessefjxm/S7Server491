// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;

public class SMTurnNonPlayer extends SendablePacket<GameClient> {
    private Creature owner;

    public SMTurnNonPlayer(final Creature owner) {
        this.owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.owner.getGameObjectId());
        final Location loc = this.owner.getLocation();
        buffer.writeF(loc.getCos());
        buffer.writeD(0);
        buffer.writeF(loc.getSin());
        buffer.writeF(loc.getX());
        buffer.writeF(loc.getZ());
        buffer.writeF(loc.getY());
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeC(1);
    }
}
