package com.bdoemu.gameserver.model.team.guild.services;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.network.receivable.CMCreateGuild;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.GuildsDBCollection;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CreateGuildItemEvent;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildType;
import com.bdoemu.gameserver.model.team.guild.events.LoginGuildEvent;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class GuildService extends APeriodicTaskService {
    private static class Holder {
        static final GuildService INSTANCE = new GuildService();
    }

    private static final Logger log = LoggerFactory.getLogger(GuildService.class);
    private final ConcurrentHashMap<Long, Guild> guilds;
    private final ConcurrentHashMap<Integer, GuildQuest> guildQuests;
    private final ConcurrentLinkedQueue<Player> joinablePlayers;

    private GuildService() {
        super(27, TimeUnit.MINUTES);
        this.guildQuests = new ConcurrentHashMap<>();
        this.joinablePlayers = new ConcurrentLinkedQueue<>();
        this.guilds = GuildsDBCollection.getInstance().load();
        GuildService.log.info("Loaded {} guilds.", this.guilds.size());
    }

    public static GuildService getInstance() {
        return Holder.INSTANCE;
    }

    public void inviteToGuild(final Player owner, final Player invited) {
        final Guild guild = owner.getGuild();
        if (guild == null)
            return;

        synchronized (this) {
            if (invited.hasGuild())
                return;
            final GuildMember member = new GuildMember(invited, EGuildMemberRankType.General);
            invited.setGuild(guild);
            guild.addMember(member);
            this.changeGuildJoinMode(invited, 1);
            invited.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(invited));
            for (final Creature summon : invited.getSummonStorage().getSummons()) {
                summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
            }
            guild.sendBroadcastPacket(new SMNotifyToJoinGuildOrAlliance(invited, guild, 11));
            guild.onEvent(new LoginGuildEvent(guild, invited, true));
            owner.sendPacket(new SMRespondToJoinGuild(guild, member));
        }
    }

    public void changeGuildJoinMode(final Player player, final int modeId) {
        switch (modeId) {
            case 0:
                this.joinablePlayers.add(player);
                break;
            case 1:
                this.joinablePlayers.remove(player);
                break;
        }
    }

    public ConcurrentLinkedQueue<Player> getJoinablePlayers() {
        return this.joinablePlayers;
    }

    public void onLogout(final Player player) {
        if (player.getGuild() != null && player.getGuild().getGuildQuest() != null)
            player.getGuild().getGuildQuest().deregisterObserverPlayer(player);
        this.joinablePlayers.remove(player);
    }

    public void onLogin(final Player player) {
        final ListSplitter<Guild> guildsSplit = new ListSplitter<>(this.guilds.values(), 957);
        while (guildsSplit.hasNext()) {
            player.sendPacket(new SMRefreshGuildSeqNo(guildsSplit.getNext()));
        }
        for (final Guild guild : this.guilds.values()) {
            if (guild.containsMember(player.getAccountId())) {
                player.setGuild(guild);
            }
        }
    }

    public void removeGuild(final Guild guild) {
        if (this.guilds.values().remove(guild)) {
            GuildsDBCollection.getInstance().delete(guild.getObjectId());
        }
    }

    public boolean updateGuildName(final Player player, final Guild guild, final String name) {
        synchronized (this) {
            if (getInstance().getGuildByName(name) != null) {
                player.sendPacket(new SMNak(EStringTable.eErrNoGuildNameUserIsBusy, CMCreateGuild.class));
                return false;
            }
            guild.setName(name);
            guild.recalculateGuildBasicCacheCount();
            World.getInstance().broadcastWorldPacket(new SMRefreshGuildSeqNo(Collections.singletonList(guild)));
        }
        return true;
    }

    public void createGuild(final Player player, final EGuildType guildType, final String name) {
        Guild guild;
        synchronized (this) {
            if (player.getGuild() != null || name.isEmpty()) {
                return;
            }
            if (getInstance().getGuildByName(name) != null) {
                player.sendPacket(new SMNak(EStringTable.eErrNoGuildNameUserIsBusy, CMCreateGuild.class));
                return;
            }
            if (guildType.isGuild() && !player.getPlayerBag().onEvent(new CreateGuildItemEvent(player))) {
                return;
            }
            guild = new Guild(name, guildType, player);
            this.guilds.put(guild.getObjectId(), guild);
            player.setGuild(guild);
        }
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.GuildCreated);
        guild.addJournalEntryAndNotify(journalEntry);
        this.changeGuildJoinMode(player, 1);
        World.getInstance().broadcastWorldPacket(new SMRefreshGuildSeqNo(Collections.singletonList(guild)));
        player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(player));
        for (final Creature summon : player.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        player.sendBroadcastItSelfPacket(new SMNotifyToJoinGuildOrAlliance(player, guild, 9));
        guild.sendBroadcastPacket(new SMChangeGuildJoinMode(player.getGameObjectId(), 2));
        guild.onEvent(new LoginGuildEvent(guild, player, true));
        guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.CREATE_GUILD, guild, guild.getName()));
        GuildsDBCollection.getInstance().save(guild);
    }

    public boolean removeGuildQuest(final GuildQuest guildQuest) {
        return this.guildQuests.values().remove(guildQuest);
    }

    public boolean containsGuild(final Guild guild) {
        return this.guilds.values().contains(guild);
    }

    public boolean containsQuest(final int questId) {
        return this.guildQuests.containsKey(questId);
    }

    public void run() {
        for (final Guild guild : this.guilds.values()) {
            if (this.containsGuild(guild)) {
                /*try {
					// Update Player data stuff..
					final ConcurrentHashMap<Long, GuildMember> players = guild.getMembers();
					if (players != null) {
						for (Map.Entry<Long, GuildMember> entry : guild.getMembers().entrySet()) {
							if (entry.getValue() != null)
								entry.getValue().addActivityPoints(27);
						}
					}
				} catch(Exception e) {
					log.warn("An error occured while trying to add acitivty points!", e);
				}
				*/
                GuildsDBCollection.getInstance().update(guild);
            }
        }
    }

    public ConcurrentHashMap<Long, Guild> getGuilds() {
        return this.guilds;
    }

    public Guild getGuild(final long guildId) {
        return this.guilds.get(guildId);
    }

    public Collection<GuildQuest> getGuildQuests() {
        return this.guildQuests.values();
    }

    public GuildQuest newGuildQuest(final GuildQuestT template, final Guild guild) {
        GuildQuest guildQuest;
        if (template.isPreoccupancy()) {
            guildQuest = this.guildQuests.computeIfAbsent(template.getGuildQuestNr(), m -> new GuildQuest(guild, template));
        } else {
            guildQuest = new GuildQuest(guild, template);
        }
        return guildQuest;
    }

    public Guild getGuildByName(final String name) {
        final Optional<Guild> guild = this.guilds.values().stream().filter(item -> item.getName().toLowerCase().equals(name.toLowerCase())).findFirst();
        return guild.orElse(null);
    }
}
