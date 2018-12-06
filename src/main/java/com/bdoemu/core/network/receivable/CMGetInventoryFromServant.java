// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMGetInventoryFromServant extends ReceivablePacket<GameClient> {
    private int servantSessionId;

    public CMGetInventoryFromServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantSessionId = this.readD();
    }

    public void runImpl() {
    }
}
