package com.bdoemu.gameserver.model.team.guild;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.xmlrpc.XMLRPCable;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcGuild;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMAcquiredJournal;
import com.bdoemu.gameserver.dataholders.GuildQuestData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.GuildWarehouse;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.journal.Journal;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildType;
import com.bdoemu.gameserver.model.team.guild.events.IGuildEvent;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;
import com.bdoemu.gameserver.model.team.guild.model.GuildComment;
import com.bdoemu.gameserver.model.team.guild.model.GuildWar;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Guild extends JSONable implements XMLRPCable {
    public static int capMaxMemberCount;

    static {
        Guild.capMaxMemberCount = 100;
    }

    private final Lock guildLock;
    private final GuildWarehouse guildWarehouse;
    private final GuildSkillList guildSkillList;
    private int tendency;
    private int maxMemberCount;
    private int basicCacheCount;
    private int markCacheCount;
    private long objectId;
    private long leaderAccountId;
    private long cache;
    private long leaderCache;
    private long createDate;
    private ConcurrentHashMap<Long, Player> membersOnline;
    private ConcurrentHashMap<Long, GuildMember> members;
    private ConcurrentHashMap<Long, GuildWar> _wars;
    private boolean _hasDeclaredWarOnce;
    private Journal guildJournal;
    private String name;
    private EGuildType guildType;
    private String notice;
    private String description;
    private byte[] markBytes;
    private TreeMap<Long, GuildComment> comments;
    private GuildQuest guildQuest;
    private int lastGuildQuestCompleteTime;

    public Guild(final String name, final EGuildType guildType, final Player leader) {
        this.lastGuildQuestCompleteTime = 0;
        this.maxMemberCount = 15;
        this.basicCacheCount = 1;
        this.leaderAccountId = -1L;
        this.membersOnline = new ConcurrentHashMap<Long, Player>();
        this.members = new ConcurrentHashMap<Long, GuildMember>();
        this._wars = new ConcurrentHashMap<Long, GuildWar>();
        this._hasDeclaredWarOnce = false;
        this.guildLock = new ReentrantLock();
        this.comments = new TreeMap<Long, GuildComment>();
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.GUILD);
        this.cache = -GameServerIDFactory.getInstance().nextId(GSIDStorageType.CACHE);
        this.guildJournal = new Journal(EJournalType.Guild);
        this.createDate = GameTimeService.getServerTimeInMillis();
        this.guildWarehouse = new GuildWarehouse(this);
        this.guildSkillList = new GuildSkillList(this);
        this.name = name;
        this.guildType = guildType;
        this.leaderAccountId = leader.getAccountId();
        this.leaderCache = leader.getCache();
        this.guildJournal = new Journal(EJournalType.Guild);
        this.markBytes = new byte[14400];
        if (guildType.isGuild())
            this.guildWarehouse.addItem(new Item(1, 80000L, 0), 0);
        this.getMembers().put(leader.getAccountId(), new GuildMember(leader, EGuildMemberRankType.Master));
    }

    public Guild(final BasicDBObject dbObject) {
        this.lastGuildQuestCompleteTime = 0;
        this.maxMemberCount = 15;
        this.basicCacheCount = 1;
        this.leaderAccountId = -1L;
        this.membersOnline = new ConcurrentHashMap<Long, Player>();
        this.members = new ConcurrentHashMap<Long, GuildMember>();
        this._wars = new ConcurrentHashMap<Long, GuildWar>();
        this._hasDeclaredWarOnce = dbObject.getBoolean("hasDeclaredWarOnce", false);
        this.guildLock = new ReentrantLock();
        this.comments = new TreeMap<Long, GuildComment>();
        this.objectId = dbObject.getLong("_id");
        this.cache = -GameServerIDFactory.getInstance().nextId(GSIDStorageType.CACHE);
        this.name = dbObject.getString("name");
        this.tendency = dbObject.getInt("tendency");
        this.maxMemberCount = dbObject.getInt("maxMemberCount");
        this.createDate = dbObject.getLong("createDate");
        this.guildType = EGuildType.valueOf(dbObject.getString("guildType"));
        this.description = dbObject.getString("description", "");
        this.notice = dbObject.getString("notice", "");
        this.guildJournal = new Journal((BasicDBObject) dbObject.get("journal"));
        this.leaderAccountId = dbObject.getLong("leaderAccountId");
        if (dbObject.containsField("guildSkillList")) {
            this.guildSkillList = new GuildSkillList((BasicDBObject) dbObject.get("guildSkillList"), this);
        } else {
            this.guildSkillList = new GuildSkillList(this);
        }
        if (dbObject.containsField("mark")) {
            this.markBytes = (byte[]) dbObject.get("mark");
            checkAndFixMark();
        } else {
            this.markBytes = new byte[14400];
        }
        if (dbObject.containsField("guildWarehouse")) {
            this.guildWarehouse = new GuildWarehouse((BasicDBObject) dbObject.get("guildWarehouse"), this);
        } else {
            this.guildWarehouse = new GuildWarehouse(this);
        }
        final BasicDBList membersDB = (BasicDBList) dbObject.get("members");
        for (final Object aMembersDB : membersDB) {
            final BasicDBObject basicDBObject = (BasicDBObject) aMembersDB;
            final GuildMember member = new GuildMember(basicDBObject);
            this.members.put(member.getAccountId(), member);
        }
        if (dbObject.containsField("wars")) {
            final BasicDBList warsDB = (BasicDBList) dbObject.get("wars");
            if (warsDB != null) {
                for (final Object aWarsDB : warsDB) {
                    final BasicDBObject basicDBObject = (BasicDBObject) aWarsDB;
                    final GuildWar warEntry = new GuildWar(basicDBObject);
                    this._wars.put(warEntry.getObjectId(), warEntry);
                }
            }
        }
        if (dbObject.containsField("comments")) {
            final BasicDBList commentsDB = (BasicDBList) dbObject.get("comments");
            for (final Object aCommentDB : commentsDB) {
                final BasicDBObject basicDBObject2 = (BasicDBObject) aCommentDB;
                final GuildComment comment = new GuildComment(basicDBObject2);
                this.comments.put(comment.getObjectId(), comment);
            }
        }
    }

    public Collection<GuildQuestT> getAvailableQuests(final boolean isPreoccupancy) {
        return GuildQuestData.getInstance().getTemplates().stream().filter(g -> g.isPreoccupancy() == isPreoccupancy && g.getQuestLvl() == 1 && !GuildService.getInstance().containsQuest(g.getGuildQuestNr()) && (this.guildQuest == null || this.guildQuest.getQuestId() != g.getGuildQuestNr())).collect(Collectors.toList());
    }

    public int getLastGuildQuestCompletedTime() {
        return this.lastGuildQuestCompleteTime;
    }

    public GuildSkillList getGuildSkillList() {
        return this.guildSkillList;
    }

    public GuildQuest getGuildQuest() {
        return this.guildQuest;
    }

    public void setGuildQuest(final GuildQuest guildQuest) {
        if (this.guildQuest != null && guildQuest == null)
            this.guildQuest.clearObservers();
        this.guildQuest = guildQuest;
        this.lastGuildQuestCompleteTime = (int) (System.currentTimeMillis() / 1000L);
    }

    public long getCache() {
        return this.cache;
    }

    public long getLeaderCache() {
        return this.leaderCache;
    }

    public void setLeaderCache(final long leaderCache) {
        this.leaderCache = leaderCache;
    }

    public EGuildType getGuildType() {
        return this.guildType;
    }

    public void setGuildType(final EGuildType guildType) {
        this.guildType = guildType;
        this.recalculateGuildBasicCacheCount();
    }

    public int getBasicCacheCount() {
        return this.basicCacheCount;
    }

    public void recalculateGuildBasicCacheCount() {
        ++this.basicCacheCount;
    }

    public int getMarkCacheCount() {
        return this.markCacheCount;
    }

    public void recalculateGuildMarkCacheCount() {
        ++this.markCacheCount;
    }

    public GuildWarehouse getGuildWarehouse() {
        return this.guildWarehouse;
    }

    public Collection<Long> getGuildAccounts() {
        return Collections.list(this.members.keys());
    }

    public long getLeaderAccountId() {
        return this.leaderAccountId;
    }

    public void setLeaderAccountId(final long leaderAccountId) {
        this.leaderAccountId = leaderAccountId;
    }

    public Player getLeader() {
        return this.membersOnline.get(this.leaderAccountId);
    }

    public Collection<Player> getMembersOnline() {
        return this.membersOnline.values();
    }

    public ConcurrentHashMap<Long, GuildMember> getMembers() {
        return this.members;
    }

    public int getMembersCount() {
        return this.members.size();
    }

    public ConcurrentHashMap<Long, GuildWar> getGuildWars() {
        return _wars;
    }

    public GuildWar getGuildWar(final long guildWarObjectId) {
        return _wars.get(guildWarObjectId);
    }

    public void createGuildWarEntry(final Guild srcGuild, final Guild dstGuild) {
        final GuildWar guildWar = new GuildWar(dstGuild);
    }

    public boolean hasDeclaredWarOnce() {
        return _hasDeclaredWarOnce;
    }

    public void removeGuildWarEntry(final Long guildWarObjectId) {
        if (_wars.get(guildWarObjectId) != null)
            _wars.remove(guildWarObjectId);
    }

    public void addOnlineMember(final Player player) {
        this.membersOnline.putIfAbsent(player.getAccountId(), player);
    }

    public Player removeOnlineMember(final long accountId) {
        return this.membersOnline.remove(accountId);
    }

    public void addMember(final GuildMember member) {
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.GuildMemberJoined);
        journalEntry.setParam6(member.getName());
        this.addJournalEntryAndNotify(journalEntry);
        final Player player = member.getPlayer();
        if (player != null) {
            final JournalEntry journalEntryForPlayer = new JournalEntry();
            journalEntryForPlayer.setDate(GameTimeService.getServerTimeInMillis());
            journalEntryForPlayer.setType(EJournalEntryType.GuildJoined);
            journalEntryForPlayer.setParam6(this.getName());
            player.addJournalEntryAndNotify(journalEntryForPlayer);
        }
        this.members.put(member.getAccountId(), member);
    }

    public GuildMember removeMember(final long accountId) {
        return this.members.remove(accountId);
    }

    public GuildMember getMember(final long accountId) {
        return this.members.get(accountId);
    }

    public boolean containsMember(final long accountId) {
        return this.members.containsKey(accountId);
    }

    public boolean isMemberOnline(final Player player) {
        return this.membersOnline.contains(player);
    }

    public long getObjectId() {
        return this.objectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
        this.recalculateGuildBasicCacheCount();
    }

    public int getLevel() {
        if (this.maxMemberCount >= 76) {
            return 4;
        }
        if (this.maxMemberCount >= 51) {
            return 3;
        }
        if (this.maxMemberCount >= 31) {
            return 2;
        }
        return 1;
    }

    private void checkAndFixMark() {
        if (markBytes.length != 14400)
            markBytes = Arrays.copyOf(markBytes, 14400);
    }

    public byte[] getMarkBytes() {
        checkAndFixMark();
        return this.markBytes;
    }

    public void setMarkBytes(final byte[] markBytes) {
        this.markBytes = markBytes;
        checkAndFixMark();
        this.recalculateGuildMarkCacheCount();
    }

    public int getTendency() {
        return this.tendency;
    }

    public void addTendency(final int tendency) {
        this.tendency += tendency;
    }

    public int getMaxMemberCount() {
        return this.maxMemberCount;
    }

    public void setMaxMemberCount(final int maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
    }

    public long getCreateDate() {
        return this.createDate;
    }

    public String getNotice() {
        return this.notice;
    }

    public void setNotice(final String notice) {
        this.notice = notice;
        this.recalculateGuildBasicCacheCount();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
        this.recalculateGuildBasicCacheCount();
    }

    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
        for (final Player player : this.getMembersOnline()) {
            player.sendPacketNoFlush(sp);
        }
    }

    public void onEvent(final IGuildEvent event) {
        this.guildLock.lock();
        try {
            if (event.canAct()) {
                event.onEvent();
            }
        } finally {
            this.guildLock.unlock();
        }
    }

    public Item getGuildFundItem() {
        return this.guildWarehouse.getItem(0);
    }

    public long getGuildFundItemObjId() {
        final Item item = this.getGuildFundItem();
        return (item != null) ? item.getObjectId() : -1L;
    }

    public long getGuildFund() {
        final Item item = this.guildWarehouse.getItem(0);
        return (item != null) ? item.getCount() : 0L;
    }

    public void setGuildFund(final long guildFund) {
        final Item item = this.getGuildFundItem();
        if (item != null) {
            item.setCount(guildFund);
        } else {
            this.guildWarehouse.addItem(new Item(1, guildFund, 0), 0);
        }
    }

    public void addJournalEntryAndNotify(final JournalEntry journalEntry) {
        this.guildJournal.addJournalEntry(journalEntry);
        this.sendBroadcastPacket(new SMAcquiredJournal(EJournalType.Guild, journalEntry));
    }

    public Journal getJournal() {
        return this.guildJournal;
    }

    public TreeMap<Long, GuildComment> getComments() {
        return this.comments;
    }

    public void addComment(final int accountId, final String comment) {
        final String familyName = FamilyService.getInstance().getFamily(accountId);
        if (familyName != null && !StringUtils.isEmpty((CharSequence) comment)) {
            final GuildComment commentObject = new GuildComment(familyName, comment);
            this.comments.put(commentObject.getObjectId(), commentObject);
        }
    }

    public void removeComment(final int commentId) {
        this.comments.values().removeIf(item -> item.getObjectId() == commentId);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList membersListDB = this.members.values().stream().map(GuildMember::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        final BasicDBList warsListDB = this._wars.values().stream().map(GuildWar::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        final BasicDBList commentsListDB = this.comments.values().stream().map(GuildComment::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        builder.append("_id", this.getObjectId());
        builder.append("name", this.getName());
        builder.append("tendency", this.getTendency());
        builder.append("maxMemberCount", this.getMaxMemberCount());
        builder.append("createDate", this.getCreateDate());
        builder.append("guildType", this.guildType.name());
        builder.append("notice", this.notice);
        builder.append("description", this.description);
        builder.append("leaderAccountId", this.leaderAccountId);
        builder.append("mark", this.markBytes);
        builder.append("hasDeclaredWarOnce", this._hasDeclaredWarOnce);
        builder.append("guildWarehouse", this.guildWarehouse.toDBObject());
        builder.append("guildSkillList", this.guildSkillList.toDBObject());
        builder.append("members", membersListDB);
        builder.append("wars", warsListDB);
        builder.append("comments", commentsListDB);
        builder.append("journal", this.guildJournal.toDBObject());
        return builder.get();
    }

    public XmlRpcGuild toXMLRpcObject(final String message) {
        final XmlRpcGuild rpcGuild = new XmlRpcGuild();
        rpcGuild.setName(this.getName());
        rpcGuild.setLevel(this.getLevel());
        rpcGuild.setExp((long) this.getGuildSkillList().getCurrentSkillPointsExp());
        rpcGuild.setTendency(this.getTendency());
        rpcGuild.setObjectId(this.getObjectId());
        rpcGuild.setCreatedDate(this.getCreateDate());
        for (final GuildMember member : this.members.values()) {
            rpcGuild.getGuildMembers().add(member.toXMLRpcObject(null));
        }
        return rpcGuild;
    }
}
