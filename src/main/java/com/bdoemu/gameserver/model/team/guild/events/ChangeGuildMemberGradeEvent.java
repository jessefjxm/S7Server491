package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.network.sendable.SMChangeGuildMemberGrade;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.core.network.sendable.SMRefreshGuildBasicCache;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;

public class ChangeGuildMemberGradeEvent implements IGuildEvent {
    private Player player;
    private Guild guild;
    private GuildMember member;
    private GuildMember leader;
    private long accountId;
    private EGuildMemberRankType rank;

    public ChangeGuildMemberGradeEvent(final Player player, final Guild guild, final long accountId, final EGuildMemberRankType rank) {
        this.player = player;
        this.guild = guild;
        this.accountId = accountId;
        this.rank = rank;
    }

    @Override
    public void onEvent() {
        this.member.setRank(this.rank);
        if (this.rank.isMaster()) {
            this.guild.setLeaderAccountId(this.accountId);
            this.leader.setRank(EGuildMemberRankType.General);
            this.guild.setGuildFund(this.guild.getGuildFund() / 2L);
            this.guild.recalculateGuildBasicCacheCount();
            this.guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.CHANGE_GUILD_LEADER, this.guild, this.accountId));
            this.guild.sendBroadcastPacket(new SMChangeGuildMemberGrade(this.guild, this.player.getAccountId(), this.accountId, this.rank));
            final JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(GameTimeService.getServerTimeInMillis());
            journalEntry.setType(EJournalEntryType.ChangeGuildMaster);
            journalEntry.setParam6(this.member.getFamilyName());
            this.guild.addJournalEntryAndNotify(journalEntry);
            this.guild.sendBroadcastPacket(new SMRefreshGuildBasicCache(this.guild));
            this.player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.player));
        } else {
            this.guild.sendBroadcastPacket(new SMChangeGuildMemberGrade(this.guild, this.player.getAccountId(), this.accountId, this.rank));
        }
        final Player playerMember = this.member.getPlayer();
        if (playerMember != null) {
            playerMember.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(playerMember));
        }
    }

    @Override
    public boolean canAct() {
        this.member = this.guild.getMember(this.accountId);
        this.leader = this.guild.getMember(this.guild.getLeaderAccountId());
        return this.leader != null && this.member != null && this.accountId != this.guild.getLeaderAccountId() && (!this.rank.isMaster() || this.member.getRank().isOfficer()) && (!this.rank.isOfficer() || this.guild.getMembers().values().stream().filter(guildMember -> guildMember.getRank().isOfficer()).count() < 2L) && GuildService.getInstance().containsGuild(this.guild) && this.member != null && this.player.getGuild() == this.guild && this.guild.getLeaderAccountId() == this.player.getAccountId();
    }
}
