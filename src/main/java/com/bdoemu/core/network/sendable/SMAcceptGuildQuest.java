// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;

public class SMAcceptGuildQuest extends SendablePacket<GameClient> {
    private GuildQuest guildQuest;

    public SMAcceptGuildQuest(final GuildQuest guildQuest) {
        this.guildQuest = guildQuest;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.guildQuest.getGuildQuestT().isPreoccupancy());
        buffer.writeD(this.guildQuest.getQuestId());
        buffer.writeQ(this.guildQuest.getEndDate());
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
    }
}
