// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.fitness.Fitness;
import com.bdoemu.gameserver.model.creature.player.fitness.FitnessHandler;

public class SMListFitnessExperience extends SendablePacket<GameClient> {
    private final FitnessHandler fitnessHandler;

    public SMListFitnessExperience(final FitnessHandler fitnessHandler) {
        this.fitnessHandler = fitnessHandler;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        for (final Fitness fitness : this.fitnessHandler.getFitness()) {
            buffer.writeD(fitness.getFitnessType().ordinal());
            buffer.writeD(fitness.getLevel());
            buffer.writeQ(fitness.getExp());
        }
        buffer.writeD(this.fitnessHandler.getStamina().getIntValue());
        buffer.writeD(this.fitnessHandler.getWeight().getIntValue());
        buffer.writeF(this.fitnessHandler.getHp().getValue());
        buffer.writeF(this.fitnessHandler.getMp().getValue());
    }
}
