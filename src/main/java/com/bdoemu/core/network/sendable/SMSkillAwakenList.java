// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.AwakenedSkill;

import java.util.Collection;

public class SMSkillAwakenList extends SendablePacket<GameClient> {
    private final Collection<AwakenedSkill> awakenedSkills;

    public SMSkillAwakenList(final Collection<AwakenedSkill> awakenedSkills) {
        this.awakenedSkills = awakenedSkills;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.awakenedSkills.size());
        for (final AwakenedSkill awakenedSkill : this.awakenedSkills) {
            buffer.writeH(awakenedSkill.getSkillLevel());
            buffer.writeH(awakenedSkill.getSkillId());
            buffer.writeD(awakenedSkill.getAbilityId());
        }
    }
}
