// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.House;

public class SMBuyHouseForTownManagement extends SendablePacket<GameClient> {
    private final House house;

    public SMBuyHouseForTownManagement(final House house) {
        this.house = house;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.house.getHouseId());
        buffer.writeD(this.house.getUpgradeCount());
        buffer.writeD(this.house.getLevel());
        buffer.writeD(this.house.getReciepeKey());
        buffer.writeQ(this.house.getCraftDate());
    }
}
