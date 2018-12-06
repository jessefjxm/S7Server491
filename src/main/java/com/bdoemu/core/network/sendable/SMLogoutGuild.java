// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.GuildMember;

public class SMLogoutGuild extends SendablePacket<GameClient> {
    private final GuildMember member;
    private final long guildId;

    public SMLogoutGuild(final GuildMember member, final long guildId) {
        this.member = member;
        this.guildId = guildId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.member.getAccountId());
        buffer.writeS((CharSequence) this.member.getName(), 62);
        buffer.writeS((CharSequence) this.member.getFamilyName(), 62);
        buffer.writeD(this.member.getGameObjectId());
        buffer.writeQ(this.member.getLastLogoutDate());
        buffer.writeQ(this.guildId);
        buffer.writeQ(0L);
    }
}
