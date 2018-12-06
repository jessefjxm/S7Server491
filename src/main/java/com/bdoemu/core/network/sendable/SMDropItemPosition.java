// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.world.Location;

public class SMDropItemPosition extends SendablePacket<GameClient> {
    private final DeadBody deadBody;

    public SMDropItemPosition(final DeadBody deadBody) {
        this.deadBody = deadBody;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.deadBody.getGameObjectId());
        buffer.writeD(this.deadBody.getOwner().getGameObjectId());
        final Location loc = this.deadBody.getLocation();
        buffer.writeF(loc.getX());
        buffer.writeF(loc.getZ());
        buffer.writeF(loc.getY());
    }
}
