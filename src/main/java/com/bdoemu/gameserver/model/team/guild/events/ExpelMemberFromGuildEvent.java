package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.configs.GuildConfig;
import com.bdoemu.core.network.sendable.SMExpelMemberFromGuild;
import com.bdoemu.core.network.sendable.SMNotifyToDisjoinGuildOrAlliance;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;

public class ExpelMemberFromGuildEvent implements IGuildEvent {
    private Player player;
    private Player playerMember;
    private Guild guild;
    private long accountId;
    private GuildMember member;

    public ExpelMemberFromGuildEvent(final Player player, final Guild guild, final long accountId) {
        this.player = player;
        this.guild = guild;
        this.accountId = accountId;
    }

    @Override
    public void onEvent() {
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.GuildMemberExpelled);
        journalEntry.setParam6(this.member.getName());
        this.guild.addJournalEntryAndNotify(journalEntry);
        AccountsDBCollection.getInstance().updateGuildCoolTime(this.member.getAccountId(), GameTimeService.getServerTimeInMillis() + GuildConfig.NEXT_GUILD_JOINABLE_TIME * 1000);
        if (this.playerMember != null) {
            if (guild.getGuildQuest() != null) {
                guild.getGuildQuest().deregisterObserverPlayer(playerMember);
            }

            this.playerMember.getAccountData().setGuildCoolTime(GameTimeService.getServerTimeInMillis() + GuildConfig.NEXT_GUILD_JOINABLE_TIME * 1000);
            this.playerMember.setGuild(null);
            this.playerMember.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.playerMember));
            for (final Creature summon : this.playerMember.getSummonStorage().getSummons()) {
                summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
            }
            this.playerMember.sendPacketNoFlush(new SMNotifyToDisjoinGuildOrAlliance(this.playerMember, 16));
            this.member.endActiveBuffs();
        }
        this.guild.sendBroadcastPacket(new SMExpelMemberFromGuild(this.guild.getObjectId(), this.accountId, this.member.getGameObjectId()));
    }

    @Override
    public boolean canAct() {
        if (!GuildService.getInstance().containsGuild(this.guild) || this.player.getGuild() != this.guild || this.guild.getLeaderAccountId() != this.player.getAccountId()) {
            return false;
        }
        if (guild.getGuildQuest() != null)
            return false;
        this.member = this.guild.removeMember(this.accountId);
        this.playerMember = this.guild.removeOnlineMember(this.accountId);
        return this.member != null;
    }
}
