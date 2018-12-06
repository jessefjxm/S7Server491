// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMNpcWorkerUpgrade extends SendablePacket<GameClient> {
    private final long objectId;
    private final int newWorkerId;
    private final int newSkillId;

    public SMNpcWorkerUpgrade(final long objectId, final int newWorkerId, final int newSkillId) {
        this.objectId = objectId;
        this.newWorkerId = newWorkerId;
        this.newSkillId = newSkillId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeH(this.newWorkerId);
        buffer.writeH(this.newSkillId);
    }
}
