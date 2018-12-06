package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListDeclareGuildWarToMyGuild;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMListDeclareGuildWarToMyGuild extends ReceivablePacket<GameClient> {
    public CMListDeclareGuildWarToMyGuild(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        if (getClient() == null)
            return;

        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getGuild() != null) {
            player.sendPacket(new SMListDeclareGuildWarToMyGuild());
        }
    }
}
