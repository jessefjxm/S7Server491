// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseHold;

public class SMUnoccupyHouseholdFixed extends SendablePacket<GameClient> {
    private final HouseHold houseHold;

    public SMUnoccupyHouseholdFixed(final HouseHold houseHold) {
        this.houseHold = houseHold;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.houseHold.getCreatureId());
        buffer.writeQ(this.houseHold.getObjectId());
    }
}
