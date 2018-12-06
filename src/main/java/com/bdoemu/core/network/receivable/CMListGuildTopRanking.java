// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMListGuildTopRanking extends ReceivablePacket<GameClient> {
    public CMListGuildTopRanking(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readD();
    }

    public void runImpl() {
    }
}
