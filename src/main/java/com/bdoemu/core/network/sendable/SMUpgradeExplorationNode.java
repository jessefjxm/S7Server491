// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.exploration.Discovery;

public class SMUpgradeExplorationNode extends SendablePacket<GameClient> {
    private final Discovery discovery;

    public SMUpgradeExplorationNode(final Discovery discovery) {
        this.discovery = discovery;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.discovery.getWaypointId());
        buffer.writeH(this.discovery.getLevel());
        buffer.writeH(this.discovery.getContribution());
    }
}
