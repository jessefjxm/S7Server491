// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRemoveCard extends SendablePacket<GameClient> {
    private int cardId;

    public SMRemoveCard(final int cardId) {
        this.cardId = cardId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.cardId);
    }
}
