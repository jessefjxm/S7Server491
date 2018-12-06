// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMDieNonPlayer extends SendablePacket<GameClient> {
    private final int ownerSession;
    private final int killerSession;
    private long actionHash;

    public SMDieNonPlayer(final int ownerSession, final int killerSession, final long actionHash) {
        this.ownerSession = ownerSession;
        this.killerSession = killerSession;
        this.actionHash = actionHash;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.ownerSession);
        buffer.writeD(this.killerSession);
        buffer.writeD(this.actionHash);
    }
}
