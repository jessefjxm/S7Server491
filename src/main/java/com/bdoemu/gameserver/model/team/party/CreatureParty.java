package com.bdoemu.gameserver.model.team.party;

import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.events.EDistributionItemGrade;
import com.bdoemu.gameserver.model.team.party.events.IPartyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CreatureParty implements Runnable, IParty<Creature> {
    private static final Logger log = LoggerFactory.getLogger(CreatureParty.class);
    protected Creature leader;
    private int minSize;
    private int maxSize;
    private Map<Integer, Creature> members;
    private ScheduledFuture<?> partyCheckTask;

    public CreatureParty(final Creature leader, final int minSize, final int maxSize) {
        this.members = new ConcurrentHashMap<>();
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.leader = leader;
    }

    @Override
    public void startTask() {
        this.partyCheckTask = ThreadPool.getInstance().scheduleGeneralAtFixedRate((Runnable) this, 1L, 30L, TimeUnit.SECONDS);
    }

    @Override
    public void cancelTask() {
        if (this.partyCheckTask != null) {
            this.partyCheckTask.cancel(true);
        }
    }

    @Override
    public void run() {
        this.members.values().stream().filter(member -> member.getAi() != null).forEach(member -> member.getAi().HandlePartyCheck(null, null));
    }

    @Override
    public long getPartyId() {
        return -1L;
    }

    @Override
    public Creature getLeader() {
        return this.leader;
    }

    @Override
    public void setLeader(final Creature creature) {
        this.leader = creature;
    }

    @Override
    public boolean isPartyLeader(final Creature creature) {
        return this.leader.getGameObjectId() == creature.getGameObjectId();
    }

    @Override
    public boolean isFull() {
        return this.members.size() == this.maxSize;
    }

    @Override
    public Collection<Creature> getMembers() {
        return this.members.values();
    }

    @Override
    public Collection<Creature> getMembers(final Creature excludeMember) {
        return this.getMembers().stream().filter(member -> excludeMember != null && member.getGameObjectId() != excludeMember.getGameObjectId()).collect(Collectors.toList());
    }

    @Override
    public void addMember(final Creature member) {
        if (this.members.size() < this.maxSize) {
            this.members.put(member.getGameObjectId(), member);
            member.setParty(this);
            if (member.getAi() != null) {
                member.getAi().HandlePartyInvited(this.leader, null);
            }
        }
    }

    @Override
    public Creature removeMember(final Creature member) {
        if (member.getGameObjectId() == this.leader.getGameObjectId()) {
            this.members.values().stream().filter(partyMember -> partyMember.getAi() != null && partyMember != this.leader).forEach(partyMember -> {
                partyMember.setParty(null);
                partyMember.getAi().HandlePartyReleased(null, null);
            });
        }
        member.setParty(null);
        return this.members.remove(member.getGameObjectId());
    }

    @Override
    public int getMembersCount() {
        return this.members.size();
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public boolean onEvent(final IPartyEvent event) {
        return false;
    }

    @Override
    public List<Long> getOfflineMembers() {
        return null;
    }

    @Override
    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
    }

    @Override
    public EPartyLootType getLootType() {
        return null;
    }

    @Override
    public void setLootType(final EPartyLootType type) {
    }

    @Override
    public Collection<Long> getPartyAccounts() {
        return null;
    }

    @Override
    public int nextShuffleIndex(final int size) {
        return -1;
    }

    @Override
    public long getDistributionPrice() {
        return 0L;
    }

    @Override
    public void setDistributionPrice(final long distributionPrice) {
    }

    @Override
    public EDistributionItemGrade getDistributionItemGrade() {
        return null;
    }

    @Override
    public void setDistributionItemGrade(final EDistributionItemGrade distributionItemGrade) {
    }

    @Override
    public ConcurrentHashMap<Long, PartyItemMarket> getPartyInventory() {
        return null;
    }
}
