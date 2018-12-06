// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;

public class SMStopNonPlayerMoving extends SendablePacket<GameClient> {
    private Creature creature;

    public SMStopNonPlayerMoving(final Creature creature) {
        this.creature = creature;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeD(-1024);
        buffer.writeD(this.creature.getActionStorage().getActionHash());
        buffer.writeF(this.creature.getLocation().getX());
        buffer.writeF(this.creature.getLocation().getZ());
        buffer.writeF(this.creature.getLocation().getY());
        buffer.writeF(this.creature.getLocation().getCos());
        buffer.writeD(0);
        buffer.writeF(this.creature.getLocation().getSin());
        buffer.writeC(0);
    }
}
