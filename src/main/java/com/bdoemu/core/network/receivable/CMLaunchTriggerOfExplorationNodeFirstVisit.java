// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMLaunchTriggerOfExplorationNodeFirstVisit extends ReceivablePacket<GameClient> {
    private int nodeId;

    public CMLaunchTriggerOfExplorationNodeFirstVisit(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.nodeId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getExploration().learnDiscovery(this.nodeId, true);
        }
    }
}
