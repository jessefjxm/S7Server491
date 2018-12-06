// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.Item;

public class SMExtendExpirationPeriod extends SendablePacket<GameClient> {
    private Item item;

    public SMExtendExpirationPeriod(final Item item) {
        this.item = item;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.item.getStorageLocation().getId());
        buffer.writeC(this.item.getSlotIndex());
        buffer.writeQ(this.item.getExpirationPeriod());
    }
}
