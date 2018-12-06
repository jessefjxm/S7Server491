package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMTakeGuildBenefit extends ReceivablePacket<GameClient> {
    public CMTakeGuildBenefit(final short opcode) {
        super(opcode);
    }

    protected void read() {
        readC();
        readQ();
    }

    public void runImpl() {
    }
}
