// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMRepairItem extends SendablePacket<GameClient> {
    private final EItemStorageLocation storageType;
    private final int playerSessionId;
    private final int slotIndex;
    private final int currentEndurance;
    private final int maxEndurance;

    public SMRepairItem(final int playerSessionId, final EItemStorageLocation storageType, final int slotIndex, final int currentEndurance, final int maxEndurance) {
        this.playerSessionId = playerSessionId;
        this.storageType = storageType;
        this.slotIndex = slotIndex;
        this.currentEndurance = currentEndurance;
        this.maxEndurance = maxEndurance;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.playerSessionId);
        buffer.writeC(this.storageType.getId());
        buffer.writeC(this.slotIndex);
        buffer.writeB(new byte[7]);
        buffer.writeH(this.currentEndurance);
        buffer.writeH(this.maxEndurance);
        buffer.writeC(0);
    }
}
