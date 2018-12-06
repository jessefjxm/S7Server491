package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.GuildConfig;
import com.bdoemu.core.network.receivable.CMDestroyGuild;
import com.bdoemu.core.network.sendable.SMDestroyGuild;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyToJoinGuildOrAlliance;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

public class DestroyGuildEvent implements IGuildEvent {
    private Player player;
    private Guild guild;
    private GuildMember guildMember;

    public DestroyGuildEvent(final Player player, final Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    @Override
    public void onEvent() {
        GuildService.getInstance().removeGuild(this.guild);
        World.getInstance().broadcastWorldPacket(new SMDestroyGuild(this.guild.getObjectId(), this.guild.getName()));
        this.player.getAccountData().setGuildCoolTime(GameTimeService.getServerTimeInMillis() + GuildConfig.NEXT_GUILD_JOINABLE_TIME * 1000);
        this.player.setGuild(null);
        this.player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.player));
        for (final Creature summon : this.player.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        this.player.sendBroadcastItSelfPacket(new SMNotifyToJoinGuildOrAlliance(this.player, this.guild, 18));
        this.guildMember.endActiveBuffs();
        if (guild.getGuildQuest() != null)
            guild.getGuildQuest().deregisterObserverPlayer(player);
    }

    @Override
    public boolean canAct() {
        // Guild master squad cannot be disbanded.
        if (this.guild.getMembers().size() > 1) {
            player.sendPacket(new SMNak(EStringTable.eErrNoGuildMasterSquadCantDestroy, CMDestroyGuild.class));
            return false;
        }

        // You cannot appoint the Guild Master or disband the guild during a quest.
        if (this.guild.getGuildQuest() != null) {
            player.sendPacket(new SMNak(EStringTable.eErrNoGuildQuestCantChangeGuildMasterAndDestroy, CMDestroyGuild.class));
            return false;
        }

        this.guildMember = this.guild.getMember(this.guild.getLeaderAccountId());
        return GuildService.getInstance().containsGuild(this.guild) && this.guild == this.player.getGuild() && this.guild.getLeaderAccountId() == this.player.getAccountId();
    }
}


