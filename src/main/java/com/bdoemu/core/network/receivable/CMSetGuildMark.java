// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.SetGuildMarkItemEvent;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class CMSetGuildMark extends ReceivablePacket<GameClient> {
    private int slot;
    private byte[] markData;

    public CMSetGuildMark(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.slot = this.readC();
        this.markData = this.readB(14000);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                player.getPlayerBag().onEvent(new SetGuildMarkItemEvent(guild, player, this.slot, this.markData));
            }
        }
    }
}
