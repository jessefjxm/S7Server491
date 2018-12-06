// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMExchangeItemSlotInInventory extends SendablePacket<GameClient> {
    private final Player player;
    private final EItemStorageLocation storageType;
    private final int oldSlot;
    private final int nextSlot;

    public SMExchangeItemSlotInInventory(final Player player, final EItemStorageLocation storageType, final int nextSlot, final int oldSlot) {
        this.player = player;
        this.nextSlot = nextSlot;
        this.oldSlot = oldSlot;
        this.storageType = storageType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeC(this.storageType.getId());
        buffer.writeC(this.nextSlot);
        buffer.writeC(this.oldSlot);
    }
}
