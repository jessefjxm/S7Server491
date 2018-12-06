// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMUpdateLocalWarStatus;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.LocalWarService;

public class CMUpdateLocalWarStatus extends ReceivablePacket<GameClient> {
    public CMUpdateLocalWarStatus(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMUpdateLocalWarStatus(LocalWarService.getInstance().getLocalWars()));
        }
    }
}
