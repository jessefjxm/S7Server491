// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMExpelMemberFromGuild extends SendablePacket<GameClient> {
    private final long guildId;
    private final long accountId;
    private final int objectId;

    public SMExpelMemberFromGuild(final long guildId, final long accountId, final int objectId) {
        this.guildId = guildId;
        this.accountId = accountId;
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guildId);
        buffer.writeQ(this.accountId);
        buffer.writeD(this.objectId);
    }
}
