// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMUpdatePalette extends SendablePacket<GameClient> {
    private int dyeItemId;
    private long dyeItemcount;
    private boolean unk;
    private boolean addOrRemove;

    public SMUpdatePalette(final int itemId, final long count, final boolean unk, final boolean addOrRem) {
        this.dyeItemId = itemId;
        this.dyeItemcount = count;
        this.unk = unk;
        this.addOrRemove = addOrRem;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.dyeItemId);
        buffer.writeQ(this.dyeItemcount);
        buffer.writeC(0);
        buffer.writeC(this.addOrRemove);
    }
}
