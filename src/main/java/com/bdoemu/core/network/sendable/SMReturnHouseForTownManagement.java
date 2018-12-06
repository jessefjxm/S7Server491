// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMReturnHouseForTownManagement extends SendablePacket<GameClient> {
    private final int houseId;

    public SMReturnHouseForTownManagement(final int houseId) {
        this.houseId = houseId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.houseId);
    }
}
