// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.AwakenedSkill;

public class SMSkillAwakening extends SendablePacket<GameClient> {
    private final AwakenedSkill awakenedSkill;
    private final int awakenedSkillSize;
    private final int awakenedWeaponSkillSize;

    public SMSkillAwakening(final AwakenedSkill awakenedSkill, final int awakenedSkillSize, final int awakenedWeaponSkillSize) {
        this.awakenedSkill = awakenedSkill;
        this.awakenedSkillSize = awakenedSkillSize;
        this.awakenedWeaponSkillSize = awakenedWeaponSkillSize;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.awakenedSkill.getSkillId());
        buffer.writeD(this.awakenedSkill.getAbilityId());
        buffer.writeD(this.awakenedSkillSize);
        buffer.writeD(this.awakenedWeaponSkillSize);
    }
}
