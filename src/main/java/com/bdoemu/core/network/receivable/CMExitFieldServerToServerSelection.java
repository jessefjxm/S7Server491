// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMExitFieldServerToServerSelection;

public class CMExitFieldServerToServerSelection extends ReceivablePacket<GameClient> {
    public CMExitFieldServerToServerSelection(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        ((GameClient) this.getClient()).close((SendablePacket) new SMExitFieldServerToServerSelection(((GameClient) this.getClient()).getLoginAccountInfo().getCookie()));
    }
}
