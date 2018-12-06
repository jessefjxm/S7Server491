// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRefuseInviteGuild extends SendablePacket<GameClient> {
    private final String name;
    private final EStringTable stringTable;
    private final long coolTime;

    public SMRefuseInviteGuild(final String name, final EStringTable stringTable, final long coolTime) {
        this.name = name;
        this.stringTable = stringTable;
        this.coolTime = coolTime;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.name, 62);
        buffer.writeD(this.stringTable.getHash());
        buffer.writeQ(this.coolTime);
    }
}
