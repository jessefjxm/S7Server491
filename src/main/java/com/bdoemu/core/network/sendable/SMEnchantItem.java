// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.EnchantItemEvent;

public class SMEnchantItem extends SendablePacket<GameClient> {
    private final EnchantItemEvent enchantItemEvent;

    public SMEnchantItem(final EnchantItemEvent enchantItemEvent) {
        this.enchantItemEvent = enchantItemEvent;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.enchantItemEvent.getEnchantType());
        buffer.writeC(this.enchantItemEvent.getItemStorageType().ordinal());
        buffer.writeC(this.enchantItemEvent.getItemSlot());
        buffer.writeC(this.enchantItemEvent.getStoneStorageType().ordinal());
        buffer.writeC(this.enchantItemEvent.getStoneSlot());
        final Long stoneCount = this.enchantItemEvent.getStoneCount();
        buffer.writeQ((stoneCount == null) ? 0L : ((long) stoneCount));
        buffer.writeC(this.enchantItemEvent.getCroneStorageType().ordinal());
        buffer.writeC(this.enchantItemEvent.getCroneSlotIndex());
        buffer.writeQ(this.enchantItemEvent.getCroneStoneCount());
        buffer.writeH(this.enchantItemEvent.getItemId());
        buffer.writeH(this.enchantItemEvent.getEnchantLevel());
        buffer.writeD(this.enchantItemEvent.getMsg().getHash());
        buffer.writeC(this.enchantItemEvent.isType());
        buffer.writeH(this.enchantItemEvent.getMaxEndurance());
        buffer.writeD(0);
    }
}
