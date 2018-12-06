// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMChangeSkillAwakeningBitFlag extends SendablePacket<GameClient> {
    private final int awakeningSkill;
    private final int abilityId;

    public SMChangeSkillAwakeningBitFlag(final int awakeningSkill, final int abilityId) {
        this.awakeningSkill = awakeningSkill;
        this.abilityId = abilityId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.awakeningSkill);
        buffer.writeD(this.abilityId);
    }
}
