package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;

import java.util.List;

public class SMGetGuildInformation extends SendablePacket<GameClient> {
    public static int MAX_MEMBERS;

    static {
        SMGetGuildInformation.MAX_MEMBERS = 60;
    }

    private final Guild guild;
    private final List<GuildMember> members;

    public SMGetGuildInformation(final Guild guild, final List<GuildMember> members) {
        this.guild = guild;
        this.members = members;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeS(this.guild.getName(), 62);
        buffer.writeD(this.guild.getBasicCacheCount());
        buffer.writeD(this.guild.getMarkCacheCount());
        buffer.writeQ(this.guild.getCreateDate() / 1000L);
        buffer.writeH(this.guild.getMaxMemberCount());
        buffer.writeH(0);
        buffer.writeQ(this.guild.getCache());
        buffer.writeH(0);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeC(this.guild.getGuildType().ordinal());
        buffer.writeD(0);
        buffer.writeD(guild.getAvailableQuests(true).size());
        buffer.writeD(1);
        buffer.writeQ(this.guild.getGuildFund());
        buffer.writeQ(0L);
        buffer.writeQ(0L);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeQ(this.guild.getGuildFundItemObjId());
        buffer.writeD(-1);
        buffer.writeD(-1);
        buffer.writeD(-1);
        buffer.writeD(-1);
        buffer.writeD(-1);
        buffer.writeD(-1);
        buffer.writeB(new byte[65]);
        buffer.writeH(this.members.size());
        for (final GuildMember member : this.members) {
            buffer.writeQ(member.getAccountId());
            buffer.writeS(member.getFamilyName(), 62);
            buffer.writeQ(member.getJoinedDate() / 1000L);
            buffer.writeQ(member.getLastLoginDate() / 1000L);
            buffer.writeQ(member.getLastLogoutDate() / 1000L);
            buffer.writeC(member.getRank().ordinal());
            buffer.writeD(member.getGameObjectId());
            buffer.writeQ(-1L);
            buffer.writeB(new byte[28]);
            buffer.writeQ(member.getContractEndDate());
            buffer.writeQ(member.getContractPayout());
            buffer.writeQ(member.getContractPenalty());
            buffer.writeC(guild.getLeaderAccountId() == member.getAccountId());
            buffer.writeD(member.getActivityPoints());
            buffer.writeD(member.getGuildActivityPoints());
            buffer.writeQ(member.getContractEndDate()); // renewal time
            buffer.writeH(member.getMaxExplorePoints());
            buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
            buffer.writeH(member.getMaxWp());
            buffer.writeC(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeQ(member.getObjectId());
            buffer.writeS(member.getName(), 62);
            buffer.writeD(member.getLevel());
            buffer.writeH(member.getCharacterKey());
        }
    }
}
