package com.bdoemu.gameserver.model.team.party;

import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.events.EDistributionItemGrade;
import com.bdoemu.gameserver.model.team.party.events.IPartyEvent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface IParty<T extends Creature> {
    long getPartyId();

    T getLeader();

    void setLeader(final T p0);

    boolean isPartyLeader(final T p0);

    boolean isFull();

    Collection<T> getMembers();

    Collection<T> getMembers(final T p0);

    void addMember(final T p0);

    T removeMember(final T p0);

    int getMembersCount();

    int getMaxSize();

    boolean onEvent(final IPartyEvent p0);

    List<Long> getOfflineMembers();

    void sendBroadcastPacket(final SendablePacket<GameClient> p0);

    EPartyLootType getLootType();

    void setLootType(final EPartyLootType p0);

    Collection<Long> getPartyAccounts();

    int nextShuffleIndex(final int p0);

    long getDistributionPrice();

    void setDistributionPrice(final long p0);

    EDistributionItemGrade getDistributionItemGrade();

    void setDistributionItemGrade(final EDistributionItemGrade p0);

    void startTask();

    void cancelTask();

    ConcurrentHashMap<Long, PartyItemMarket> getPartyInventory();
}