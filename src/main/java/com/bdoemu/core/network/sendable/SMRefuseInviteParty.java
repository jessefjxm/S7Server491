// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRefuseInviteParty extends SendablePacket<GameClient> {
    private final String name;
    private final EStringTable stringTable;

    public SMRefuseInviteParty(final String name, final EStringTable stringTable) {
        this.name = name;
        this.stringTable = stringTable;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.name, 62);
        buffer.writeD(this.stringTable.getHash());
    }
}
