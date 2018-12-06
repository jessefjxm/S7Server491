// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMUpdateGamePointQuest extends ReceivablePacket<GameClient> {
    private int value;

    public CMUpdateGamePointQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.value = this.readD();
    }

    public void runImpl() {
    }
}
