// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMExchangeItemToClass extends SendablePacket<GameClient> {
    private int itemId;
    private int enchantLevel;
    private int storageType;
    private int slotIndex;

    public SMExchangeItemToClass(final int itemId, final int enchantLevel, final int storageType, final int slotIndex) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.storageType = storageType;
        this.slotIndex = slotIndex;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageType);
        buffer.writeC(this.slotIndex);
        buffer.writeH(this.itemId);
        buffer.writeH(this.enchantLevel);
    }
}
