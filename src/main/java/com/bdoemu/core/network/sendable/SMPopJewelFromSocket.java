// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMPopJewelFromSocket extends SendablePacket<GameClient> {
    private final EItemStorageLocation itemStorageType;
    private final int itemSlot;
    private final int type;
    private final byte[] sockets;

    public SMPopJewelFromSocket(final int type, final EItemStorageLocation itemStorageType, final int itemSlot, final byte[] sockets) {
        this.type = type;
        this.itemStorageType = itemStorageType;
        this.itemSlot = itemSlot;
        this.sockets = sockets;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type);
        buffer.writeH(1);
        buffer.writeC(this.itemStorageType.getId());
        buffer.writeC(this.itemSlot);
        buffer.writeC(0);
        for (final byte index : this.sockets) {
            buffer.writeC((int) index);
        }
    }
}
