// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMWithdrawParty extends SendablePacket<GameClient> {
    private final int memberSession;
    private final int state;
    private int ownerSession;

    public SMWithdrawParty(final int memberSession, final int ownerSession, final int state) {
        this.ownerSession = -1024;
        this.memberSession = memberSession;
        this.state = state;
        this.ownerSession = ownerSession;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.state);
        buffer.writeD(this.memberSession);
        buffer.writeD(this.ownerSession);
    }
}
