// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMTopLifeRanking extends SendablePacket<GameClient> {
    private Player[] players;

    public SMTopLifeRanking(final Player[] players) {
        this.players = players;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        for (final Player player : this.players) {
            buffer.writeD((player != null) ? player.getGameObjectId() : -1024);
        }
    }
}
