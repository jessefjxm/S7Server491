// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePoint;

import java.util.Collection;

public class SMExplorePointList extends SendablePacket<GameClient> {
    private final Collection<ExplorePoint> territories;

    public SMExplorePointList(final Collection<ExplorePoint> territories) {
        this.territories = territories;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.territories.size());
        for (final ExplorePoint ep : this.territories) {
            buffer.writeH(ep.getTerritoryKey());
            buffer.writeH(ep.getMaxExplorePoints());
            buffer.writeH(ep.getCurrentExplorePoints());
            buffer.writeQ(ep.getExp());
        }
    }
}
