// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMEndServantSkillExpTraining extends SendablePacket<GameClient> {
    private long servantObjId;

    public SMEndServantSkillExpTraining(final long servantObjId) {
        this.servantObjId = servantObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
    }
}
