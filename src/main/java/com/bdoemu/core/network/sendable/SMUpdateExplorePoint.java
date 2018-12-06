// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePoint;

public class SMUpdateExplorePoint extends SendablePacket<GameClient> {
    private final ExplorePoint ep;

    public SMUpdateExplorePoint(final ExplorePoint ep) {
        this.ep = ep;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.ep.getTerritoryKey());
        buffer.writeH(this.ep.getMaxExplorePoints());
        buffer.writeH(this.ep.getCurrentExplorePoints());
        buffer.writeQ(this.ep.getExp());
        buffer.writeC(0);
    }
}
