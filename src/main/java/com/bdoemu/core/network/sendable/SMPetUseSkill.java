// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPetUseSkill extends SendablePacket<GameClient> {
    private long petObjId;
    private int unk;

    public SMPetUseSkill(final long petObjId, final int unk) {
        this.petObjId = petObjId;
        this.unk = unk;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.petObjId);
        buffer.writeD(this.unk);
    }
}
