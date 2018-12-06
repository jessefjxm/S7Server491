package com.bdoemu.gameserver.model.team.party;

import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMSetCharacterCurrentRelatedPoints;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.events.EDistributionItemGrade;
import com.bdoemu.gameserver.model.team.party.events.IPartyEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PlayerParty implements Runnable, IParty<Player> {
    private final ConcurrentHashMap<Long, Player> onlineMembers;
    private final ConcurrentHashMap<Long, PartyItemMarket> partyInventory;
    private final List<Long> offlineMembers;
    private final long partyId;
    private final Lock partyLock;
    private long distributionPrice;
    private Player leader;
    private Future<?> packetBroadcastTask;
    private EPartyLootType lootType;
    private EDistributionItemGrade distributionItemGrade;
    private int shuffleIndex;

    public PlayerParty(final Player leader) {
        this.onlineMembers = new ConcurrentHashMap<>();
        this.partyInventory = new ConcurrentHashMap<>();
        this.offlineMembers = new CopyOnWriteArrayList<>();
        this.lootType = EPartyLootType.Free;
        this.distributionItemGrade = EDistributionItemGrade.white;
        this.partyLock = new ReentrantLock();
        this.partyId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.PARTY);
        (this.leader = leader).setParty(this);
    }

    @Override
    public int nextShuffleIndex(final int size) {
        if (this.shuffleIndex >= size - 1) {
            return this.shuffleIndex = 0;
        }
        return ++this.shuffleIndex;
    }

    @Override
    public Collection<Long> getPartyAccounts() {
        return this.onlineMembers.values().stream().map(Player::getAccountId).collect(Collectors.toList());
    }

    public ConcurrentHashMap<Long, Player> getOnlineMembers() {
        return this.onlineMembers;
    }

    public boolean isMember(final Player player) {
        return this.onlineMembers.containsKey(player.getObjectId());
    }

    @Override
    public boolean isFull() {
        return this.onlineMembers.size() + this.offlineMembers.size() >= 5;
    }

    @Override
    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
        this.onlineMembers.values().stream().filter(Objects::nonNull).forEach(player -> player.sendPacket(sp));
    }

    @Override
    public long getDistributionPrice() {
        return this.distributionPrice;
    }

    @Override
    public void setDistributionPrice(final long distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    @Override
    public EDistributionItemGrade getDistributionItemGrade() {
        return this.distributionItemGrade;
    }

    @Override
    public void setDistributionItemGrade(final EDistributionItemGrade distributionItemGrade) {
        this.distributionItemGrade = distributionItemGrade;
    }

    @Override
    public ConcurrentHashMap<Long, PartyItemMarket> getPartyInventory() {
        return this.partyInventory;
    }

    @Override
    public long getPartyId() {
        return this.partyId;
    }

    @Override
    public Player getLeader() {
        return this.leader;
    }

    @Override
    public void setLeader(final Player creature) {
        this.leader = creature;
    }

    @Override
    public boolean isPartyLeader(final Player player) {
        return player.getObjectId() == this.leader.getObjectId();
    }

    @Override
    public Collection<Player> getMembers() {
        return this.onlineMembers.values();
    }

    @Override
    public Collection<Player> getMembers(final Player excludeMember) {
        return this.getMembers().stream().filter(member -> excludeMember != null && member.getGameObjectId() != excludeMember.getGameObjectId()).collect(Collectors.toList());
    }

    @Override
    public void addMember(final Player member) {
        if (member.isPlayer()) {
            this.onlineMembers.put(member.getObjectId(), member);
        }
    }

    @Override
    public Player removeMember(final Player member) {
        return this.onlineMembers.remove(member.getObjectId());
    }

    @Override
    public int getMembersCount() {
        return this.onlineMembers.size() + this.offlineMembers.size();
    }

    @Override
    public int getMaxSize() {
        return 5;
    }

    @Override
    public List<Long> getOfflineMembers() {
        return this.offlineMembers;
    }

    @Override
    public boolean onEvent(final IPartyEvent event) {
        boolean result = false;
        this.partyLock.lock();
        try {
            if (event.canAct()) {
                result = true;
                event.onEvent();
            }
        } finally {
            this.partyLock.unlock();
        }
        return result;
    }

    @Override
    public EPartyLootType getLootType() {
        return this.lootType;
    }

    @Override
    public void setLootType(final EPartyLootType lootType) {
        this.lootType = lootType;
    }

    @Override
    public void startTask() {
        this.packetBroadcastTask = ThreadPool.getInstance().scheduleGeneralAtFixedRate((Runnable) this, 0L, 5L, TimeUnit.SECONDS);
    }

    @Override
    public void cancelTask() {
        if (this.packetBroadcastTask != null) {
            this.packetBroadcastTask.cancel(true);
        }
    }

    @Override
    public void run() {
        for (final Player member : this.onlineMembers.values()) {
            member.sendPacket(new SMSetCharacterCurrentRelatedPoints(new ArrayList<>(this.onlineMembers.values())));
        }
    }
}
