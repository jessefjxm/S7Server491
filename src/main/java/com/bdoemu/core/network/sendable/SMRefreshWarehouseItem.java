// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRefreshWarehouseItem extends SendablePacket<GameClient> {
    private final int townId;

    public SMRefreshWarehouseItem(final int townId) {
        this.townId = townId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.townId);
    }
}
