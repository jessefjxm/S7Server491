// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.exploration.Discovery;

import java.util.Collection;

public class SMExplorationInfo extends SendablePacket<GameClient> {
    private final Collection<Discovery> discoveryList;

    public SMExplorationInfo(final Collection<Discovery> discoveryList) {
        this.discoveryList = discoveryList;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(0);
        buffer.writeH(this.discoveryList.size());
        for (final Discovery discovery : this.discoveryList) {
            buffer.writeD(discovery.getWaypointId());
            buffer.writeH(discovery.getLevel());
            buffer.writeD(discovery.getExp());
            buffer.writeD(0);
        }
    }
}
