// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMVoiceChatState extends ReceivablePacket<GameClient> {
    private long accountId;
    private long guildId;

    public CMVoiceChatState(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.accountId = this.readQ();
        this.guildId = this.readQ();
        this.readD();
    }

    public void runImpl() {
    }
}
