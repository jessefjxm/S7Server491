// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;

public class SMChangeGuildMemberGrade extends SendablePacket<GameClient> {
    private final Guild guild;
    private final long memberAccountId;
    private final long leaderAccountId;
    private final EGuildMemberRankType rank;

    public SMChangeGuildMemberGrade(final Guild guild, final long leaderAccountId, final long memberAccountId, final EGuildMemberRankType rank) {
        this.guild = guild;
        this.leaderAccountId = leaderAccountId;
        this.memberAccountId = memberAccountId;
        this.rank = rank;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeD(this.guild.getBasicCacheCount());
        buffer.writeQ(this.leaderAccountId);
        buffer.writeQ(this.memberAccountId);
        buffer.writeC(this.rank.ordinal());
    }
}
