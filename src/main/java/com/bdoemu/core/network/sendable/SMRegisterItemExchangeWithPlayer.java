// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;

public class SMRegisterItemExchangeWithPlayer extends WriteItemInfo {
    private final Item item;
    private final Player player;
    private final int tradeSlot;
    private final int invSlot;
    private final int tradeType;
    private final boolean result;

    public SMRegisterItemExchangeWithPlayer(final Player player, final Item item, final int tradeSlot, final int invSlot, final int tradeType, final boolean result) {
        this.player = player;
        this.item = item;
        this.result = result;
        this.tradeSlot = tradeSlot;
        this.invSlot = invSlot;
        this.tradeType = tradeType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.tradeType);
        buffer.writeC(this.result);
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeC(this.item.getStorageLocation().getId());
        buffer.writeC(this.invSlot);
        buffer.writeC(this.tradeSlot);
        this.writeItemInfo(buffer, this.item);
        buffer.writeQ(0L);
    }
}
