// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMOpenItemBox extends SendablePacket<GameClient> {
    private final int itemId;
    private final int enchantLevel;

    public SMOpenItemBox(final int itemId, final int enchantLevel) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.itemId);
        buffer.writeH(this.enchantLevel);
    }
}
