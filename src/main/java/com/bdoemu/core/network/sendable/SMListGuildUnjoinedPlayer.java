// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMListGuildUnjoinedPlayer extends SendablePacket<GameClient> {
    private final Collection<Player> players;
    private final EPacketTaskType packetTaskType;

    public SMListGuildUnjoinedPlayer(final Collection<Player> players, final EPacketTaskType packetTaskType) {
        this.players = players;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.players.size());
        for (final Player player : this.players) {
            buffer.writeD(player.getGameObjectId());
            buffer.writeQ(player.getAccountId());
            buffer.writeS((CharSequence) player.getFamily(), 62);
            buffer.writeQ(player.getObjectId());
            buffer.writeS((CharSequence) player.getName(), 62);
            buffer.writeD(player.getLevel());
            buffer.writeH(player.getCreatureId());
            buffer.writeC(0);
        }
    }
}
