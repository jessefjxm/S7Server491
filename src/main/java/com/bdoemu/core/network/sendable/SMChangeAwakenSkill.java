// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangeAwakenSkill extends SendablePacket<GameClient> {
    private final int oldSkillId;
    private final int newSkillId;
    private final int abilityId;

    public SMChangeAwakenSkill(final int oldSkillId, final int newSkillId, final int abilityId) {
        this.oldSkillId = oldSkillId;
        this.newSkillId = newSkillId;
        this.abilityId = abilityId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.oldSkillId);
        buffer.writeH(this.newSkillId);
        buffer.writeD(this.abilityId);
    }
}
