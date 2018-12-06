// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMVaryJoinableGuildMemberCount extends SendablePacket<GameClient> {
    private Guild guild;
    private int skillPoints;

    public SMVaryJoinableGuildMemberCount(final Guild guild, final int skillPoints) {
        this.guild = guild;
        this.skillPoints = skillPoints;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeH(this.guild.getMaxMemberCount());
        buffer.writeH(this.skillPoints);
    }
}
