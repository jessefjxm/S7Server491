// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;

public class SMPlayerAddSummonList extends SendablePacket<GameClient> {
    private final Creature creature;

    public SMPlayerAddSummonList(final Creature creature) {
        this.creature = creature;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeH(this.creature.getCreatureId());
    }
}
