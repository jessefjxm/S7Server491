// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.GiveupGuildQuestEvent;

public class CMGiveupGuildQuest extends ReceivablePacket<GameClient> {
    private int questId;

    public CMGiveupGuildQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.questId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new GiveupGuildQuestEvent(player, guild));
            }
        }
    }
}
