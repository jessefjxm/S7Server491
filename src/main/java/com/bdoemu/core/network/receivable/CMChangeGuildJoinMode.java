// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class CMChangeGuildJoinMode extends ReceivablePacket<GameClient> {
    private int modeId;

    public CMChangeGuildJoinMode(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.modeId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getGuild() == null) {
            GuildService.getInstance().changeGuildJoinMode(player, this.modeId);
        }
    }
}
