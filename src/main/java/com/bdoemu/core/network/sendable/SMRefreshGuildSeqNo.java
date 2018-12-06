// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;

import java.util.Collection;

public class SMRefreshGuildSeqNo extends SendablePacket<GameClient> {
    public static final int MAX_SIZE = 957;
    private final Collection<Guild> guilds;

    public SMRefreshGuildSeqNo(final Collection<Guild> guilds) {
        this.guilds = guilds;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.guilds.size());
        for (final Guild guild : this.guilds) {
            buffer.writeQ(guild.getObjectId());
            buffer.writeD(guild.getBasicCacheCount());
            buffer.writeD(guild.getMarkCacheCount());
            buffer.writeC(0);
        }
    }
}
