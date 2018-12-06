// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetMyHouseForTownManagement extends SendablePacket<GameClient> {
    private final int[] houses;
    private final boolean buyResidence;
    private final boolean enterWorld;

    public SMSetMyHouseForTownManagement(final int[] houses, final boolean buyResidence, final boolean enterWorld) {
        this.houses = houses;
        this.buyResidence = buyResidence;
        this.enterWorld = enterWorld;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        for (final int houseId : this.houses) {
            buffer.writeH(houseId);
        }
        buffer.writeC(1);
        buffer.writeC(this.enterWorld);
        buffer.writeC(this.buyResidence);
    }
}
