// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMIncreaseExperienceToExplorationNode extends ReceivablePacket<GameClient> {
    private int waypointKey;
    private int wp;

    public CMIncreaseExperienceToExplorationNode(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.waypointKey = this.readD();
        this.wp = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getExploration().increaseExperienceToExplorationNode(this.waypointKey, this.wp);
        }
    }
}
