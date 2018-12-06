// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.service.FamilyService;

public class SMRefreshGuildBasicCache extends SendablePacket<GameClient> {
    private Guild guild;

    public SMRefreshGuildBasicCache(final Guild guild) {
        this.guild = guild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeD(this.guild.getBasicCacheCount());
        buffer.writeS((CharSequence) this.guild.getName(), 62);
        buffer.writeS((CharSequence) FamilyService.getInstance().getFamily(this.guild.getLeaderAccountId()), 62);
        buffer.writeC(this.guild.getGuildType().ordinal());
        buffer.writeS((CharSequence) this.guild.getDescription(), 402);
        buffer.writeS((CharSequence) this.guild.getNotice(), 602);
        buffer.writeC(0);
    }
}
