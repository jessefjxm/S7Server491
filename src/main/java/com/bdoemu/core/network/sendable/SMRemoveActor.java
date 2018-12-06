// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;

public class SMRemoveActor extends SendablePacket<GameClient> {
    private final Creature creature;
    private final ERemoveActorType type;

    public SMRemoveActor(final Creature creature, final ERemoveActorType type) {
        this.creature = creature;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeC((int) this.type.getId());
        buffer.writeF(this.creature.getLocation().getX());
        buffer.writeF(this.creature.getLocation().getZ());
        buffer.writeF(this.creature.getLocation().getY());
    }
}
