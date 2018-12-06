// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMGuildMemberLevelUp extends SendablePacket<GameClient> {
    private long guildObjId;
    private long accountId;
    private int level;

    public SMGuildMemberLevelUp(final long guildObjId, final long accountId, final int level) {
        this.guildObjId = guildObjId;
        this.accountId = accountId;
        this.level = level;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guildObjId);
        buffer.writeQ(this.accountId);
        buffer.writeD(this.level);
    }
}
