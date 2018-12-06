package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCash;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMCash extends ReceivablePacket<GameClient> {
    public CMCash(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && !player.getBanController().checkChat()) {
            ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMCash(player.getAccountId()));
        }
    }
}