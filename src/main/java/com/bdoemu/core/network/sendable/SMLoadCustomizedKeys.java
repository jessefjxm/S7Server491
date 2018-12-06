// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMLoadCustomizedKeys extends SendablePacket<GameClient> {
    private Player player;

    public SMLoadCustomizedKeys(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeB(this.player.getCustomizedKeys());
    }
}
