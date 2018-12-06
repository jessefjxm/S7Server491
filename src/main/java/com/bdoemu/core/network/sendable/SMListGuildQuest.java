// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;

import java.util.Collection;

public class SMListGuildQuest extends SendablePacket<GameClient> {
    public Collection<GuildQuestT> avialableQuests;

    public SMListGuildQuest(final Collection<GuildQuestT> avialableQuests) {
        this.avialableQuests = avialableQuests;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.avialableQuests.size());
        int index = 0;
        for (final GuildQuestT template : this.avialableQuests) {
            buffer.writeD(template.getGuildQuestNr());
            buffer.writeD(index++);
        }
    }
}
