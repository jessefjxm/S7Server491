// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;

public class SMActionRotate extends SendablePacket<GameClient> {
    private Creature creature;
    private int speed;
    private double direction;

    public SMActionRotate(final Creature creature, final int speed, final double direction) {
        this.creature = creature;
        this.speed = speed;
        this.direction = direction;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeF(this.direction);
        buffer.writeD(this.speed);
    }
}
