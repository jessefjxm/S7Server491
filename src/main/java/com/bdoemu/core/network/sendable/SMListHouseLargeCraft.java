package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseLargeCraft;

import java.util.Collection;

public class SMListHouseLargeCraft extends SendablePacket<GameClient> {
    private Collection<HouseLargeCraft> largerCrafts;

    public SMListHouseLargeCraft(final Collection<HouseLargeCraft> largerCrafts) {
        this.largerCrafts = largerCrafts;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.largerCrafts.size());
        for (final HouseLargeCraft largerCraft : this.largerCrafts) {
            buffer.writeH(largerCraft.getHouseId());
            buffer.writeH(largerCraft.getCraftId());
            for (final long itemCount : largerCraft.getItemCounts()) {
                buffer.writeQ(itemCount);
                //System.out.println("===========SMListHouseLargeCraft Task Left: "+ itemCount);
            }
            for (final long itemCount : largerCraft.getItemCounts()) {
                buffer.writeQ(itemCount);
            }
        }
    }
}
