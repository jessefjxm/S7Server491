// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListGuildUnjoinedPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

import java.util.Collection;

public class CMListGuildUnjoinedPlayer extends ReceivablePacket<GameClient> {
    public CMListGuildUnjoinedPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getGuild() != null) {
            final ListSplitter<Player> playersSplitter = (ListSplitter<Player>) new ListSplitter((Collection) GuildService.getInstance().getJoinablePlayers(), 87);
            while (playersSplitter.hasNext()) {
                player.sendPacket(new SMListGuildUnjoinedPlayer(playersSplitter.getNext(), playersSplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
            }
        }
    }
}
