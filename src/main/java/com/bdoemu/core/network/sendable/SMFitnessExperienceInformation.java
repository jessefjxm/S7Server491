// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.fitness.Fitness;

public class SMFitnessExperienceInformation extends SendablePacket<GameClient> {
    private final Fitness fitness;
    private final int addStamina;
    private final int addWeight;
    private final int addHp;
    private final int addMp;

    public SMFitnessExperienceInformation(final Fitness fitness, final int addStamina, final int addWeight, final int addHp, final int addMp) {
        this.fitness = fitness;
        this.addStamina = addStamina;
        this.addWeight = addWeight;
        this.addHp = addHp;
        this.addMp = addMp;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.fitness.getFitnessType().ordinal());
        buffer.writeD(this.fitness.getLevel());
        buffer.writeQ(this.fitness.getExp());
        buffer.writeD(this.addStamina);
        buffer.writeD(this.addWeight);
        buffer.writeD(this.addHp);
        buffer.writeD(this.addMp);
    }
}
