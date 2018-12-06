// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMDelMaidInfoByHouseholdNo extends SendablePacket<GameClient> {
    private final int type;
    private final long objectId;

    public SMDelMaidInfoByHouseholdNo(final int type, final long objectId) {
        this.type = type;
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.type);
        buffer.writeQ(this.objectId);
    }
}
