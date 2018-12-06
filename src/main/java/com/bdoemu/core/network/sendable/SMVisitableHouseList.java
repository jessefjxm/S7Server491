// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.FixedHouseTop;

import java.util.Collection;

public class SMVisitableHouseList extends SendablePacket<GameClient> {
    private final Collection<FixedHouseTop> fixedHouseTops;
    private final int houseId;
    private final byte type;

    public SMVisitableHouseList(final int houseId, final Collection<FixedHouseTop> fixedHouseTops, final byte type) {
        this.houseId = houseId;
        this.fixedHouseTops = fixedHouseTops;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.houseId);
        buffer.writeC((int) this.type);
        buffer.writeC(1);
        buffer.writeH(this.fixedHouseTops.size());
        for (final FixedHouseTop fixedHouseTop : this.fixedHouseTops) {
            buffer.writeQ(fixedHouseTop.getAccountId());
            buffer.writeQ(fixedHouseTop.getHouseObjId());
            buffer.writeS((CharSequence) fixedHouseTop.getFamily(), 62);
            buffer.writeD(fixedHouseTop.getInteriorPoints());
        }
    }
}
