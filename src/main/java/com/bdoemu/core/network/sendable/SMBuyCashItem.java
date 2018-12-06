// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

import java.util.Collection;

public class SMBuyCashItem extends SendablePacket<GameClient> {
    private final int productNr;
    private final long count;
    private final String name;
    private final Collection<Long> addTasks;
    private final EStringTable message;

    public SMBuyCashItem(final int productNr, final long count, final Collection<Long> addTasks, final String name, final EStringTable message) {
        this.productNr = productNr;
        this.count = count;
        this.addTasks = addTasks;
        this.name = name;
        this.message = message;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.productNr);
        buffer.writeQ(this.count);
        buffer.writeC(0);
        buffer.writeS((CharSequence) this.name, 62);
        buffer.writeS((CharSequence) "", 62);
        buffer.writeD(this.message.ordinal());
        buffer.writeH(this.addTasks.size());
        for (final Long task : this.addTasks) {
            buffer.writeQ((long) task);
        }
    }
}
