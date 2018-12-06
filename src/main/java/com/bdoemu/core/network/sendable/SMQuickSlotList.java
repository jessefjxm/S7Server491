// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMQuickSlotList extends SendablePacket<GameClient> {
    private final Player player;

    public SMQuickSlotList(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.player.getObjectId());
        buffer.writeS((CharSequence) this.player.getQuickSlot().getQuickSlotData(), 757);
    }
}
