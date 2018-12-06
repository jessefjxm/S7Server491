// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMPartyMemberPickupItem extends SendablePacket<GameClient> {
    private final int sessionId;
    private final int itemId;
    private final int enchant;

    public SMPartyMemberPickupItem(final int sessionId, final int itemId, final int enchant) {
        this.sessionId = sessionId;
        this.itemId = itemId;
        this.enchant = enchant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeH(this.itemId);
        buffer.writeH(this.enchant);
    }
}
