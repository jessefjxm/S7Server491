// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRemoveCharacterFromFieldNak extends SendablePacket<GameClient> {
    private long objectId;
    private EStringTable stringTable;

    public SMRemoveCharacterFromFieldNak(final long objectId, final EStringTable stringTable) {
        this.objectId = objectId;
        this.stringTable = stringTable;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeD(this.stringTable.getHash());
    }
}
