// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.House;

public class SMChangeHouseUseTypeForTownManagement extends SendablePacket<GameClient> {
    private final House house;

    public SMChangeHouseUseTypeForTownManagement(final House house) {
        this.house = house;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.house.getHouseId());
        buffer.writeD(this.house.getReciepeKey());
        buffer.writeD(this.house.getLevel());
        buffer.writeD(this.house.getUpgradeCount());
        buffer.writeQ(this.house.getCraftDate());
        buffer.writeC(0);
    }
}
