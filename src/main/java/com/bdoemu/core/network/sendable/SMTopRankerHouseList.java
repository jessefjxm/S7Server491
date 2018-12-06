// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.FixedHouseTop;
import com.bdoemu.gameserver.model.houses.HouseTopRank;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMTopRankerHouseList extends SendablePacket<GameClient> {
    private final Collection<HouseTopRank> topFixedHouses;
    private final EPacketTaskType packetTaskType;

    public SMTopRankerHouseList(final Collection<HouseTopRank> topFixedHouses, final EPacketTaskType packetTaskType) {
        this.topFixedHouses = topFixedHouses;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.topFixedHouses.size());
        for (final HouseTopRank houseTopRank : this.topFixedHouses) {
            buffer.writeH(houseTopRank.getHouseId());
            final FixedHouseTop fixedHouse = houseTopRank.getTopFixedHouse();
            if (fixedHouse != null) {
                buffer.writeQ(fixedHouse.getAccountId());
                buffer.writeQ(fixedHouse.getHouseObjId());
                buffer.writeS((CharSequence) fixedHouse.getFamily(), 62);
                buffer.writeD(fixedHouse.getInteriorPoints());
            } else {
                buffer.writeQ(-1L);
                buffer.writeQ(0L);
                buffer.writeS((CharSequence) "", 62);
                buffer.writeD(0);
            }
        }
    }
}
