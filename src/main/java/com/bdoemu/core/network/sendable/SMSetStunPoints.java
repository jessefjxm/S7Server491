package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.containers.PlayerGameStats;

public class SMSetStunPoints extends SendablePacket<GameClient> {
    private final Player player;

    public SMSetStunPoints(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final PlayerGameStats stats = this.player.getGameStats();
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeD(100); // TODO
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
