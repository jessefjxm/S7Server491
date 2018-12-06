// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseHold;

import java.util.Collection;

public class SMTentHarvestInformation extends SendablePacket<GameClient> {
    private Collection<HouseHold> tents;

    public SMTentHarvestInformation(final Collection<HouseHold> tents) {
        this.tents = tents;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.tents.size());
        for (final HouseHold tent : this.tents) {
            buffer.writeD(tent.getGameObjectId());
            buffer.writeQ(tent.getObjectId());
            buffer.writeQ(tent.getAccountId());
            buffer.writeS((CharSequence) tent.getName(), 62);
            buffer.writeC(0);
            buffer.writeD(tent.getFertilizer());
        }
    }
}
