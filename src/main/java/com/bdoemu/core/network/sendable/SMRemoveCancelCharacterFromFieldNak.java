// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRemoveCancelCharacterFromFieldNak extends SendablePacket<GameClient> {
    private final long objectId;
    private final EStringTable stringTableMessage;

    public SMRemoveCancelCharacterFromFieldNak(final long objectId, final EStringTable stringTableMessage) {
        this.objectId = objectId;
        this.stringTableMessage = stringTableMessage;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.objectId);
        buffer.writeD(this.stringTableMessage.getHash());
    }
}
