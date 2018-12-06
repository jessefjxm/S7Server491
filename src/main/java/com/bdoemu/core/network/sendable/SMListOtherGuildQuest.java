// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;

import java.util.Collection;

public class SMListOtherGuildQuest extends SendablePacket<GameClient> {
    private Collection<GuildQuest> guildQuests;

    public SMListOtherGuildQuest(final Collection<GuildQuest> guildQuests) {
        this.guildQuests = guildQuests;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.guildQuests.size());
        for (final GuildQuest guildQuest : this.guildQuests) {
            buffer.writeD(guildQuest.getQuestId());
            buffer.writeQ(guildQuest.getGuild().getObjectId());
            buffer.writeQ(guildQuest.getAcceptDate());
        }
    }
}
