// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;

public class SMNotifyToJoinGuildOrAlliance extends SendablePacket<GameClient> {
    private final int type;
    private final Player player;
    private final Guild guild;
    private final EGuildMemberRankType guildMemberRankType;

    public SMNotifyToJoinGuildOrAlliance(final Player player, final Guild guild, final int type) {
        this.player = player;
        this.guild = guild;
        this.type = type;
        this.guildMemberRankType = player.getGuildMemberRankType();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeD(this.type);
        buffer.writeQ(this.player.getGuildCache());
        buffer.writeQ(this.guild.getCache());
        buffer.writeC(this.guildMemberRankType.ordinal());
        buffer.writeD(0);
    }
}
