// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;

public class SMRespondToJoinGuild extends SendablePacket<GameClient> {
    private final Guild guild;
    private final GuildMember member;

    public SMRespondToJoinGuild(final Guild guild, final GuildMember member) {
        this.guild = guild;
        this.member = member;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeQ(this.member.getAccountId());
        buffer.writeS((CharSequence) this.member.getFamilyName(), 62);
        buffer.writeQ(this.member.getJoinedDate());
        buffer.writeQ(this.member.getLastLoginDate());
        buffer.writeQ(this.member.getLastLogoutDate());
        buffer.writeC(this.member.getRank().ordinal());
        buffer.writeD(this.member.getGameObjectId());
        buffer.writeQ(-1L);
        buffer.writeB(new byte[53]);
        buffer.writeD(this.member.getActivityPoints());
        buffer.writeH(0);
        buffer.writeD(0);
        buffer.writeQ(0L);
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeH(this.member.getMaxWp());
        buffer.writeC(0);
        buffer.writeH(0);
        buffer.writeD(0);
        buffer.writeQ(this.member.getObjectId());
        buffer.writeS((CharSequence) this.member.getName(), 62);
        buffer.writeD(this.member.getLevel());
        buffer.writeH(this.member.getCharacterKey());
    }
}
