package com.bdoemu.gameserver.model.creature.services;

import com.bdoemu.core.network.sendable.SMSetInstanceSummonActor;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class InstanceSummonService {
    private static class Holder {
        static final InstanceSummonService INSTANCE = new InstanceSummonService();
    }
    private final ConcurrentHashMap<Long, InstanceSummon> summons;

    public InstanceSummonService() {
        this.summons = new ConcurrentHashMap<Long, InstanceSummon>();
    }

    public static InstanceSummonService getInstance() {
        return Holder.INSTANCE;
    }

    public void clear(final InstanceSummon summon) {
        this.summons.values().remove(summon);
    }

    public InstanceSummon getSummon(final long accountId) {
        return this.summons.get(accountId);
    }

    public synchronized boolean addMonster(final InstanceSummon summon) {
        final Player owner = summon.getOwner();
        final IParty<Player> party = owner.getParty();
        if (party != null) {
            this.summons.put(party.getLeader().getAccountId(), summon);
            for (final Player member : party.getMembers()) {
                summon.putMember(member);
                party.sendBroadcastPacket(new SMSetInstanceSummonActor(summon.getSummon(), member));
            }
        } else {
            this.summons.put(owner.getAccountId(), summon);
            summon.putMember(owner);
        }
        World.getInstance().spawn(summon.getSummon(), true, false);
        for (final Player player : summon.getOwners()) {
            summon.getSummon().sendBroadcastPacket(new SMSetInstanceSummonActor(summon.getSummon(), player));
        }
        return true;
    }

    public void onLogin(final Player player) {
        final InstanceSummon instanceSummon = this.summons.get(player.getAccountId());
        if (instanceSummon != null) {
            instanceSummon.putMember(player);
            instanceSummon.setOwner(player);
        }
    }

    public boolean contains(final long accountId) {
        return this.summons.containsKey(accountId);
    }
}
