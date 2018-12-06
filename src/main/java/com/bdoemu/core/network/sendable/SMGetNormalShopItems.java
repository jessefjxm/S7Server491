// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.ShopItem;

import java.util.Collection;

public class SMGetNormalShopItems extends SendablePacket<GameClient> {
    private final int sessionId;
    private final Collection<ShopItem> items;

    public SMGetNormalShopItems(final int sessionId, final Collection<ShopItem> items) {
        this.sessionId = sessionId;
        this.items = items;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeH(0);
        buffer.writeC(1);
        buffer.writeD(1);
        buffer.writeH(this.items.size());
        for (final ShopItem item : this.items) {
            buffer.writeH(item.getItemSubGroup().getItemId());
            buffer.writeH(item.getItemSubGroup().getEnchantLevel());
            buffer.writeD(item.getType());
            buffer.writeC(0);
            buffer.writeD(item.getChance());
        }
    }
}
