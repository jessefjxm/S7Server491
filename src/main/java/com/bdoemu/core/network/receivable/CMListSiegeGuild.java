// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListSiegeGuild;

public class CMListSiegeGuild extends ReceivablePacket<GameClient> {
    private int castleId;

    public CMListSiegeGuild(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.castleId = this.readH();
    }

    public void runImpl() {
        ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMListSiegeGuild(this.castleId));
    }
}
