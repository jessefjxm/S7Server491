// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMQuestSearchResult extends SendablePacket<GameClient> {
    private final int characterId;

    public SMQuestSearchResult(final int characterId) {
        this.characterId = characterId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.characterId);
    }
}
