package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.LifeRankService;

import java.util.List;

public class SMListContentsLocalWarRank extends SendablePacket<GameClient> {
    private List<Player> players;

    public SMListContentsLocalWarRank(final List<Player> players) {
        this.players = players;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(LifeRankService.getInstance().getMembersRegistered());
        for (int i = 0; i < 30; ++i) {
            Player player = null;
            if (i < this.players.size())
                player = this.players.get(i);
            this.writeLocalWarRankData(buffer, player);
        }
    }

    private void writeLocalWarRankData(final SendByteBuffer buffer, final Player player) {
        buffer.writeD((player == null) ? -1024 : player.getGameObjectId());
        buffer.writeQ((player == null) ? 0L : player.getAccountId());
        buffer.writeD((player == null) ? 0 : player.getLevel());
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeS(((player == null) ? "" : player.getName()), 62);
        buffer.writeS(((player == null) ? "" : player.getFamily()), 62);
        buffer.writeQ((player == null) ? 0L : player.getObjectId());
        buffer.writeQ((player == null) ? 0L : player.getGuildId());
        buffer.writeD((player == null) ? 0L : player.getLocalWarPoints());
    }
}
