package com.bdoemu.gameserver.model.team.guild;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.xmlrpc.XMLRPCable;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcGuildMember;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuildMember extends JSONable implements XMLRPCable {
    private long accountId;
    private long joinedDate;
    private long lastLoginDate;
    private long lastLogoutDate;
    private int maxWp;
    private int maxExplorePoints;
    private int activityPoints;
    private int guildActivityPoints;
    private int guildContractEndDate;
    private long guildContractPayout;
    private long guildContractPenalty;
    private int guildContractTerm;
    private EGuildMemberRankType rank;
    private Player player;
    private List<ActiveBuff> activeBuffs;
    private CopyOnWriteArrayList<Observer> observers;

    public GuildMember(final BasicDBObject basicDBObject) {
        this.accountId = basicDBObject.getLong("accountId");
        this.joinedDate = basicDBObject.getLong("joinedDate");
        this.lastLoginDate = basicDBObject.getLong("lastLoginDate");
        this.lastLogoutDate = basicDBObject.getLong("lastLogoutDate");
        this.rank = EGuildMemberRankType.values()[basicDBObject.getInt("rank")];
        this.maxWp = basicDBObject.getInt("maxWp");
        this.maxExplorePoints = basicDBObject.getInt("maxExplorePoints");
        this.activityPoints = basicDBObject.getInt("activityPoints");
        this.guildActivityPoints = basicDBObject.getInt("guildActivityPoints");
        this.guildContractPayout = basicDBObject.getLong("guildContractPayout", 0);
        this.guildContractPenalty = basicDBObject.getLong("guildContractPenalty", 0);
        this.guildContractTerm = basicDBObject.getInt("guildContractTerm", 0);
        this.guildContractEndDate = basicDBObject.getInt("guildContractEndDate", 0);
    }

    public GuildMember(final Player player, final EGuildMemberRankType guildRankType) {
        this.accountId = player.getAccountId();
        this.joinedDate = GameTimeService.getServerTimeInMillis();
        this.rank = guildRankType;
    }

    public CopyOnWriteArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(CopyOnWriteArrayList<Observer> obs) {
        if (obs == null && observers != null)
            observers.clear();
        observers = obs;
    }

    public void addObserver(Observer obs) {
        if (observers == null)
            observers = new CopyOnWriteArrayList<>();
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        if (observers != null)
            observers.remove(obs);
    }

    public void updateLogoutInfo() {
        this.player = null;
    }

    public void updateLoginInfo(final Player player) {
        this.player = player;
    }

    public void updateInfo(final Player player) {
        this.maxWp = player.getMaxWp();
        this.maxExplorePoints = player.getExplorePointHandler().getMainExplorePoint().getMaxExplorePoints();
    }

    public void addActiveBuffs(final List<ActiveBuff> activeBuffs) {
        if (activeBuffs != null && !activeBuffs.isEmpty()) {
            if (this.activeBuffs != null) {
                this.activeBuffs.addAll(activeBuffs);
            } else {
                this.activeBuffs = activeBuffs;
            }
        }
    }

    public void endActiveBuffs() {
        if (this.activeBuffs != null) {
            for (final ActiveBuff activeBuff : this.activeBuffs) {
                activeBuff.endEffect();
            }
            this.activeBuffs.clear();
            this.activeBuffs = null;
        }
    }

    public List<ActiveBuff> getActiveBuffs() {
        return this.activeBuffs;
    }

    public int getActivityPoints() {
        return this.activityPoints;
    }

    public int getGuildActivityPoints() {
        return this.guildActivityPoints;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public String getFamilyName() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public long getJoinedDate() {
        return this.joinedDate;
    }

    public long getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(final long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public long getLastLogoutDate() {
        return this.lastLogoutDate;
    }

    public void setLastLogoutDate(final long lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    public EGuildMemberRankType getRank() {
        return this.rank;
    }

    public void setRank(final EGuildMemberRankType rank) {
        this.rank = rank;
    }

    public int getGameObjectId() {
        final Player player = this.getPlayer();
        return (player != null) ? player.getGameObjectId() : -1024;
    }

    public int getLevel() {
        final Player player = this.getPlayer();
        return (player != null) ? player.getLevel() : 1;
    }

    public int getMaxWp() {
        return this.maxWp;
    }

    public int getMaxExplorePoints() {
        return this.maxExplorePoints;
    }

    public long getCache() {
        return 0L;
    }

    public String getName() {
        final Player player = this.getPlayer();
        return (player != null) ? player.getName() : "";
    }

    public long getObjectId() {
        final Player player = this.getPlayer();
        return (player != null) ? player.getObjectId() : 0L;
    }

    public int getCharacterKey() {
        final Player player = this.getPlayer();
        return (player != null) ? player.getCreatureId() : 0;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void addActivityPoints(final int points) {
        this.activityPoints += points;
        this.guildActivityPoints += points;

        if (getPlayer().getGuild() != null)
            getPlayer().getGuild().sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.MEMBER_LEVEL_UP, getPlayer().getGuild(), getPlayer().getAccountId(), points));
    }

    public void updateContract(int endDate, int term, long payout, long penalty) {
        guildContractEndDate = endDate;
        guildContractTerm = term;
        guildContractPayout = payout;
        guildContractPenalty = penalty;
    }

    public int getContractEndDate() {
        return guildContractEndDate;
    }

    public boolean isContractValid() {
        return guildContractEndDate > System.currentTimeMillis() / 1000L;
    }

    public int getContractTerm() {
        return guildContractTerm;
    }

    public long getContractPayout() {
        return guildContractPayout;
    }

    public long getContractPenalty() {
        return guildContractPenalty;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("accountId", this.getAccountId());
        builder.append("joinedDate", this.getJoinedDate());
        builder.append("lastLoginDate", this.getLastLoginDate());
        builder.append("lastLogoutDate", this.getLastLogoutDate());
        builder.append("rank", this.getRank().ordinal());
        builder.append("maxWp", this.getMaxWp());
        builder.append("maxExplorePoints", this.getMaxExplorePoints());
        builder.append("activityPoints", this.getActivityPoints());
        builder.append("guildActivityPoints", this.getGuildActivityPoints());
        builder.append("guildContractPayout", this.guildContractPayout);
        builder.append("guildContractPenalty", this.guildContractPenalty);
        builder.append("guildContractTerm", this.guildContractTerm);
        builder.append("guildContractEndDate", this.guildContractEndDate);
        return builder.get();
    }

    public XmlRpcGuildMember toXMLRpcObject(final String message) {
        final XmlRpcGuildMember rpcGuildMember = new XmlRpcGuildMember();
        rpcGuildMember.setAccountId(this.getAccountId());
        rpcGuildMember.setFamilyName(this.getFamilyName());
        rpcGuildMember.setJoinedDate(this.getJoinedDate());
        rpcGuildMember.setLastLoginDate(this.getLastLoginDate());
        rpcGuildMember.setLastLogoutDate(this.getLastLogoutDate());
        rpcGuildMember.setRank(this.getRank().ordinal());
        return rpcGuildMember;
    }
}
