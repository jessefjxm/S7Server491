// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMEnterPlayerCharacterToFieldComplete extends SendablePacket<GameClient> {
    private Player player;

    public SMEnterPlayerCharacterToFieldComplete(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(1);
        buffer.writeC(1);
        buffer.writeC(1);
        buffer.writeD(1);
        buffer.writeC(1);
        buffer.writeQ(this.player.getLastLogout() / 1000L);
        buffer.writeD(1);
        buffer.writeQ(-2L);
        buffer.writeH(0);
    }
}
