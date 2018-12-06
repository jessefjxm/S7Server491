package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.configs.GuildConfig;
import com.bdoemu.core.network.sendable.SMDisjoinGuild;
import com.bdoemu.core.network.sendable.SMExpelMemberFromGuild;
import com.bdoemu.core.network.sendable.SMNotifyToDisjoinGuildOrAlliance;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;

public class DisjoinMemberFromGuildEvent implements IGuildEvent {
    private Player player;
    private Guild guild;
    private GuildMember guildMember;

    public DisjoinMemberFromGuildEvent(final Guild guild, final Player player) {
        this.player = player;
        this.guild = guild;
    }

    @Override
    public void onEvent() {
        if (guild != null) {
            if (guild.getGuildQuest() != null) {
                guild.getGuildQuest().deregisterObserverPlayer(player);
            }
        }

        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.GuildMemberLeft);
        journalEntry.setParam6(this.player.getName());
        this.guild.addJournalEntryAndNotify(journalEntry);
        final JournalEntry journalEntryForPlayer = new JournalEntry();
        journalEntryForPlayer.setDate(GameTimeService.getServerTimeInMillis());
        journalEntryForPlayer.setType(EJournalEntryType.GuildLeft);
        journalEntryForPlayer.setParam6(this.guild.getName());
        this.player.addJournalEntryAndNotify(journalEntryForPlayer);
        this.player.getAccountData().setGuildCoolTime(GameTimeService.getServerTimeInMillis() + GuildConfig.NEXT_GUILD_JOINABLE_TIME * 1000);
        this.player.setGuild(null);
        this.player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.player));
        for (final Creature summon : this.player.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        this.player.sendPacket(new SMNotifyToDisjoinGuildOrAlliance(this.player, 8));
        this.player.sendBroadcastItSelfPacket(new SMDisjoinGuild(this.player, this.guild));
        this.guild.sendBroadcastPacket(new SMExpelMemberFromGuild(this.guild.getObjectId(), this.player.getAccountId(), this.player.getGameObjectId()));
        this.guildMember.endActiveBuffs();
    }

    @Override
    public boolean canAct() {
        if (this.guild.getLeaderAccountId() == this.player.getAccountId()) {
            return false;
        }
        if (guild.getGuildQuest() != null)
            return false;

        this.guildMember = this.guild.removeMember(this.player.getAccountId());
        return GuildService.getInstance().containsGuild(this.guild) && this.guild == this.player.getGuild() && this.guildMember != null && this.guild.removeOnlineMember(this.player.getAccountId()) != null;
    }
}
