// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.network.sendable.SMVaryJoinableGuildMemberCount;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class VaryJoinableGuildMemberCountEvent implements IGuildEvent {
    private Player player;
    private Guild guild;

    public VaryJoinableGuildMemberCountEvent(final Player player, final Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    @Override
    public void onEvent() {
        this.guild.setMaxMemberCount(this.guild.getMaxMemberCount() + 5);
        this.guild.sendBroadcastPacket(new SMVaryJoinableGuildMemberCount(this.guild, 2));
    }

    @Override
    public boolean canAct() {
        return GuildService.getInstance().containsGuild(this.guild) && this.guild == this.player.getGuild() && this.guild.getLeaderAccountId() == this.player.getAccountId() && this.guild.getMaxMemberCount() < Guild.capMaxMemberCount && this.guild.getGuildSkillList().addSkillPoint(-2);
    }
}
