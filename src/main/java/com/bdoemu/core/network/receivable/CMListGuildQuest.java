// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListGuildQuest;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;

import java.util.Collection;

public class CMListGuildQuest extends ReceivablePacket<GameClient> {
    private boolean result;

    public CMListGuildQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.result = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                final Collection<GuildQuestT> avialableQuests = guild.getAvailableQuests(this.result);
                player.sendPacket(new SMListGuildQuest(avialableQuests));
            }
        }
    }
}
