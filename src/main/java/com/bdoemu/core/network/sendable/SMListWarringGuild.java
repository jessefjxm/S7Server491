package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.model.GuildWar;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SMListWarringGuild extends SendablePacket<GameClient> {
    private long _time;
    private Guild _guild;

    public SMListWarringGuild(Guild guild) {
        _guild = guild;
        _time = GameTimeService.getServerTimeInSecond();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        if (_guild == null)
            return;

        final ConcurrentHashMap<Long, GuildWar> wars = _guild.getGuildWars();
        int totalGuildsPass = 0;

        buffer.writeH(wars.size());
        for (Map.Entry<Long, GuildWar> entry : wars.entrySet()) {
            final GuildWar war = entry.getValue();
            if (war == null)
                continue;

            if (totalGuildsPass > 3)
                break;

            buffer.writeQ(entry.getKey());
            buffer.writeD(war.getKills());
            buffer.writeD(war.getDeaths());
            buffer.writeH(1);
            buffer.writeH(6259);
            buffer.writeH(125);
            buffer.writeH(0);
            buffer.writeQ(war.getRegisterTime());
            buffer.writeD(0);
            buffer.writeD(125);
            ++totalGuildsPass;
        }

        for (int i = 0; i < (6 - totalGuildsPass); ++i) {
            buffer.writeQ(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeH(1);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeQ(_time);
            buffer.writeD(0);
            buffer.writeD(0);
        }
    }
}