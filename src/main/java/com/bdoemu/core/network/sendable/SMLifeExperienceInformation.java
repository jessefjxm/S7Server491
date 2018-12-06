// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.LifeExperienceInformation;

public class SMLifeExperienceInformation extends SendablePacket<GameClient> {
    private final LifeExperienceInformation lifeExperience;

    public SMLifeExperienceInformation(final LifeExperienceInformation lifeExperience) {
        this.lifeExperience = lifeExperience;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.lifeExperience.getType().ordinal());
        buffer.writeD(this.lifeExperience.getLevel());
        buffer.writeQ(this.lifeExperience.getExp());
    }
}
