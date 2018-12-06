// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSkillAwakenReset extends SendablePacket<GameClient> {
    private final int skillId;

    public SMSkillAwakenReset(final int skillId) {
        this.skillId = skillId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.skillId);
    }
}
