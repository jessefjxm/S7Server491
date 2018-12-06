package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.SubResourcePointStat;

public class SMSetSubResourcePoints extends SendablePacket<GameClient> {
    private Creature owner;

    public SMSetSubResourcePoints(final Creature owner) {
        this.owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.owner.getGameObjectId());
        final SubResourcePointStat stat = this.owner.getGameStats().getSubResourcePointStat();
        buffer.writeQ(stat.getSubResourcePointCacheCount());
        buffer.writeF(stat.getMaxValue());
        buffer.writeF(stat.getValue());
        buffer.writeC(0); // unkNew432
    }
}
