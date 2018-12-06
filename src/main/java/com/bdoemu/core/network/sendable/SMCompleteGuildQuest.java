// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMCompleteGuildQuest extends SendablePacket<GameClient> {
    private final Guild guild;

    public SMCompleteGuildQuest(final Guild guild) {
        this.guild = guild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeS((CharSequence) this.guild.getName(), 62);
        buffer.writeD(this.guild.getGuildQuest().getQuestId());
    }
}
