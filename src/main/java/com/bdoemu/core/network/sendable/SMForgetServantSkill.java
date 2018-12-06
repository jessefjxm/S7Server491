// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMForgetServantSkill extends SendablePacket<GameClient> {
    private long servantObjId;
    private int skillId;

    public SMForgetServantSkill(final long servantObjId, final int skillId) {
        this.servantObjId = servantObjId;
        this.skillId = skillId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
        buffer.writeC(this.skillId);
    }
}
