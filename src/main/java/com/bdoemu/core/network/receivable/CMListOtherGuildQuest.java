// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListOtherGuildQuest;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

import java.util.Collection;

public class CMListOtherGuildQuest extends ReceivablePacket<GameClient> {
    public CMListOtherGuildQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                final Collection<GuildQuest> guildQuests = GuildService.getInstance().getGuildQuests();
                player.sendPacket(new SMListOtherGuildQuest(guildQuests));
            }
        }
    }
}
