// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMManufactureItem extends SendablePacket<GameClient> {
    private EStringTable msg;

    public SMManufactureItem(final EStringTable msg) {
        this.msg = msg;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.msg.getHash());
    }
}
