// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMLearnGuildSkill extends SendablePacket<GameClient> {
    private final int skillId;
    private final int skillLevel;
    private final int skillPoints;

    public SMLearnGuildSkill(final int skillId, final int skillLevel, final int skillPoints) {
        this.skillId = skillId;
        this.skillLevel = skillLevel;
        this.skillPoints = skillPoints;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.skillLevel);
        buffer.writeH(this.skillId);
        buffer.writeH(this.skillPoints);
    }
}
