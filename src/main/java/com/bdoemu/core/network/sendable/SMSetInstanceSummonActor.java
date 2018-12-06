// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;

public class SMSetInstanceSummonActor extends SendablePacket<GameClient> {
    private final Creature summon;
    private final Player owner;

    public SMSetInstanceSummonActor(final Creature summon, final Player owner) {
        this.summon = summon;
        this.owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.summon.getGameObjectId());
        final Location loc = this.summon.getLocation();
        buffer.writeF(loc.getX());
        buffer.writeF(loc.getZ());
        buffer.writeF(loc.getY());
        buffer.writeD(this.owner.getGameObjectId());
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
