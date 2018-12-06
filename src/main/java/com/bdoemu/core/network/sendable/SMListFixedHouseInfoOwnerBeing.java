// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;

import java.util.Collection;

public class SMListFixedHouseInfoOwnerBeing extends SendablePacket<GameClient> {
    private final Player player;
    private final Collection<HouseHold> houseHolds;
    private final byte type;

    public SMListFixedHouseInfoOwnerBeing(final Player player, final Collection<HouseHold> houseHolds, final byte type) {
        this.player = player;
        this.houseHolds = houseHolds;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC((int) this.type);
        buffer.writeH(this.houseHolds.size());
        for (final HouseHold houseHold : this.houseHolds) {
            buffer.writeH(houseHold.getCreatureId());
            buffer.writeQ(houseHold.getObjectId());
            buffer.writeQ(houseHold.getAccountId());
            buffer.writeS((CharSequence) houseHold.getFamily(), 62);
            buffer.writeQ(this.player.getGuildCache());
            buffer.writeQ(this.player.getPartyCache());
            buffer.writeQ(houseHold.getDate());
            buffer.writeQ(0L);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeC(0);
        }
    }
}
