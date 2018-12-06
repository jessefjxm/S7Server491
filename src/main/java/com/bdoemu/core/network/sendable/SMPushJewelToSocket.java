// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMPushJewelToSocket extends SendablePacket<GameClient> {
    private final EItemStorageLocation itemStorageType;
    private final EItemStorageLocation jewelStorageType;
    private final int itemSlot;
    private final int jewelSlot;
    private final int jewelIndex;

    public SMPushJewelToSocket(final EItemStorageLocation itemStorageType, final EItemStorageLocation jewelStorageType, final int jewelIndex, final int itemSlot, final int jewelSlot) {
        this.itemStorageType = itemStorageType;
        this.jewelStorageType = jewelStorageType;
        this.jewelIndex = jewelIndex;
        this.itemSlot = itemSlot;
        this.jewelSlot = jewelSlot;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.jewelIndex);
        buffer.writeC(this.itemStorageType.getId());
        buffer.writeC(this.itemSlot);
        buffer.writeC(this.jewelStorageType.getId());
        buffer.writeC(this.jewelSlot);
    }
}
