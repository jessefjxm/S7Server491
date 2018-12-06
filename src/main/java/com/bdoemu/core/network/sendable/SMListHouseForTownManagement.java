// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.House;

import java.util.Collection;

public class SMListHouseForTownManagement extends SendablePacket<GameClient> {
    private final Collection<House> houseList;

    public SMListHouseForTownManagement(final Collection<House> houseList) {
        this.houseList = houseList;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.houseList.size());
        for (final House house : this.houseList) {
            buffer.writeH(house.getHouseId());
            buffer.writeD(house.getReciepeKey());
            buffer.writeD(house.getUpgradeCount());
            buffer.writeD(house.getLevel());
            buffer.writeQ(house.getCraftDate());
        }
    }
}
