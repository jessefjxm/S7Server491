package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;

public class SMLoginGuild extends SendablePacket<GameClient> {
    private final GuildMember member;
    private final Guild guild;

    public SMLoginGuild(final GuildMember member, final Guild guild) {
        this.member = member;
        this.guild = guild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.member.getAccountId());
        buffer.writeD(this.member.getGameObjectId());
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeQ(this.guild.getCache());
        buffer.writeQ(this.member.getLastLoginDate() / 1000L);
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeH(this.member.getMaxWp());
        buffer.writeQ(this.member.getObjectId());
        buffer.writeS(this.member.getName(), 62);
        buffer.writeD(this.member.getLevel());
        buffer.writeH(this.member.getCharacterKey());
        buffer.writeS(this.member.getFamilyName(), 62);
        buffer.writeC(0);
    }
}
