// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable.utils;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public abstract class ReadItemInfoPacket extends ReceivablePacket<GameClient> {
    protected int itemId;
    protected long itemObjectId;

    public ReadItemInfoPacket(final short opcode) {
        super(opcode);
    }

    public void readItemInfo() {
        this.readD();
        this.itemObjectId = this.readQ();
        this.readC();
        this.readC();
        this.readD();
        switch (this.readC()) {
            case 1: {
                this.readB(51);
                break;
            }
            case 2: {
                this.readB(29);
                break;
            }
            case 3: {
                this.readB(6);
                break;
            }
            case 4: {
                this.readB(9);
                break;
            }
            case 5: {
                this.readB(24);
                break;
            }
            case 6: {
                this.readB(16);
                break;
            }
            case 7: {
                this.readB(16);
                break;
            }
            case 8: {
                this.readB(8);
                break;
            }
        }
        this.readQ();
        this.readD();
        this.readD();
        this.readC();
        this.readQ();
        this.readQ();
    }
}
