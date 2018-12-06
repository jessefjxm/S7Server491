// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMGetWebBenefit extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger("AuditLogger");
    }

    public CMGetWebBenefit(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
    }
}
