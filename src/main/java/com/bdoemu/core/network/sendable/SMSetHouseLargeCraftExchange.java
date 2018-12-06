// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetHouseLargeCraftExchange extends SendablePacket<GameClient> {
    private int houseId;
    private int recipeId;

    public SMSetHouseLargeCraftExchange(final int houseId, final int recipeId) {
        this.houseId = houseId;
        this.recipeId = recipeId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.houseId);
        buffer.writeH(this.recipeId);
    }
}
