// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.ItemMarket;

public class SMBroadcastRegisterItemAtItemMarket extends SendablePacket<GameClient> {
    private final ItemMarket itemMarket;
    private final int type;

    public SMBroadcastRegisterItemAtItemMarket(final ItemMarket itemMarket, final int type) {
        this.itemMarket = itemMarket;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.itemMarket.getItemId());
        buffer.writeH(this.itemMarket.getEnchantLevel());
        buffer.writeH(this.itemMarket.getTerritoryKey());
        buffer.writeQ(this.itemMarket.getCount());
        buffer.writeQ(this.itemMarket.getItemPrice());
        buffer.writeS((CharSequence) "", 62);
        buffer.writeQ(-1L);
        buffer.writeD(this.type);
    }
}
