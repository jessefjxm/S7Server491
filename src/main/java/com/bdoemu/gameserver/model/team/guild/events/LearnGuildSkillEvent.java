// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class LearnGuildSkillEvent implements IGuildEvent {
    private Player player;
    private Guild guild;
    private int skillId;

    public LearnGuildSkillEvent(final Player player, final Guild guild, final int skillId) {
        this.player = player;
        this.guild = guild;
        this.skillId = skillId;
    }

    @Override
    public void onEvent() {
        // TODO
    }

    @Override
    public boolean canAct() {
        return GuildService.getInstance().containsGuild(this.guild) && this.guild == this.player.getGuild() && this.guild.getLeaderAccountId() == this.player.getAccountId() && this.guild.getGuildSkillList().addSkill(this.skillId);
    }
}
