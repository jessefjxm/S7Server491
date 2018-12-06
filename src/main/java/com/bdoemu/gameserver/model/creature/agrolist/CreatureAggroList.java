package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CreatureAggroList implements IAggroList {
    private final ConcurrentHashMap<Integer, CreatureAggroInfo> aggroList;
    private Creature actor;

    public CreatureAggroList(final Creature actor) {
        this.aggroList = new ConcurrentHashMap<>();
        this.actor = actor;
    }

    @Override
    public void addCreature(final Creature creature) {
        if (creature != null && creature != this.actor) {
            this.aggroList.computeIfAbsent(creature.getGameObjectId(), m -> new CreatureAggroInfo(creature));
        }
    }

    @Override
    public void addDmg(final Creature creature, final double dmg) {
        Creature owner = creature.getOwner();
        if (creature.getGameObjectId() != actor.getGameObjectId() && (owner == null || owner.isPlayer() && owner.getGameObjectId() != actor.getGameObjectId())) {
            CreatureAggroInfo aggroInfo = aggroList.computeIfAbsent(creature.getGameObjectId(), m -> new CreatureAggroInfo(creature));
            if (owner != null && owner.isPlayer()) {
                CreatureAggroInfo ownerAggroInfo = aggroList.computeIfAbsent(owner.getGameObjectId(), m -> new CreatureAggroInfo(owner));
                if (ownerAggroInfo != null) {
                    ownerAggroInfo.addDmg(Math.ceil(dmg));
                    ownerAggroInfo.addHate(1);
                }
            }
            aggroInfo.addDmg(Math.ceil(dmg));
            aggroInfo.addHate(creature.isMonster() ? 10 : 1);
        }
    }

    @Override
    public void clear(final boolean dontKeepLastTarget) {
        if (dontKeepLastTarget) {
            this.aggroList.clear();
        } else {
            this.aggroList.keySet().removeIf(item -> item != this.getTargetGameObjId());
        }
    }

    @Override
    public Creature getMostDamageCreature() {
        Creature mostDamager = null;
        double maxDmg = 0.0;
        for (final CreatureAggroInfo info : this.aggroList.values()) {
            if (!MathUtils.isInRange(actor.getLocation(), info.getCreature().getLocation(), 6000))
                continue;

            if (info.getDmg() > maxDmg) {
                maxDmg = info.getDmg();
                mostDamager = info.getCreature();
            }
        }
        return mostDamager;
    }

    @Override
    public Creature getTarget() {
        final CreatureAggroInfo aggroInfo = this.getMostHate();
        return (aggroInfo != null) ? aggroInfo.getCreature() : null;
    }

    @Override
    public void setTarget(final Creature creature) {
        if (creature != null) {
            final int sessionId = creature.getGameObjectId();
            final CreatureAggroInfo aggroInfo = this.aggroList.get(sessionId);
            final CreatureAggroInfo mostHate = this.getMostHate();
            if (aggroInfo != null) {
                if (mostHate != null)
                    aggroInfo.setHate(mostHate.getHate() + 10);
                else
                    aggroInfo.addHate(10);
            }
        }
    }

    @Override
    public int getTargetGameObjId() {
        final Creature target = this.getTarget();
        return (target != null) ? target.getGameObjectId() : -1024;
    }

    @Override
    public CreatureAggroInfo getMostHate() {
        CreatureAggroInfo mostHate = null;
        double maxHate = 0.0;
        for (final CreatureAggroInfo info : this.aggroList.values()) {
            if (!info.getCreature().isDead()) {
                if (info.getCreature().getLocation().getGameSector() == null)
                    continue;

                if (!MathUtils.isInRange(actor.getLocation(), info.getCreature().getLocation(), 6000))
                    continue;

                if (info.getHate() <= maxHate)
                    continue;

                maxHate = info.getHate();
                mostHate = info;
            }
        }
        return mostHate;
    }

    @Override
    public AggroInfo getMostDamage() {
        AggroInfo winner = null;
        ConcurrentHashMap<Long, PartyAggroInfo> partyAggroList = null;
        for (final CreatureAggroInfo info : this.aggroList.values()) {
            if (info.getDmg() == 0.0)
                continue;

            final Creature creature = info.getCreature();
            if (!MathUtils.isInRange(actor.getLocation(), creature.getLocation(), 6000))
                continue;

            if (creature.isPlayer()) {
                final Player player = (Player) creature;
                final IParty<Player> party = player.getParty();
                if (party != null && !party.getMembers().isEmpty()) {
                    if (partyAggroList == null) {
                        partyAggroList = new ConcurrentHashMap<>();
                    }
                    final long partyId = party.getPartyId();
                    final PartyAggroInfo partyAggroInfo = partyAggroList.computeIfAbsent(partyId, k -> new PartyAggroInfo(party));
                    partyAggroInfo.addDmg(info.getDmg());
                    winner = this.compareWinners(winner, partyAggroInfo);
                    continue;
                }
            }
            winner = this.compareWinners(winner, info);
        }
        return winner;
    }

    private AggroInfo compareWinners(AggroInfo winner, final AggroInfo member) {
        if (winner == null) {
            winner = member;
        } else if (winner.getDmg() < member.getDmg()) {
            winner = member;
        }
        return winner;
    }

    @Override
    public List<Creature> getAggroCreatures() {
        List<Creature> ret = new ArrayList<>();
        for (CreatureAggroInfo crt : this.aggroList.values()) {
            Creature crtx = crt.getCreature();
            if (crtx == null || !MathUtils.isInRange(actor.getLocation(), crtx.getLocation(), 6000))
                continue;
            ret.add(crtx);
        }
        return ret;
        //return this.aggroList.values().stream().map(CreatureAggroInfo::getCreature).collect(Collectors.toList());
    }

    @Override
    public CreatureAggroInfo getAggroInfo(final Creature creature) {
        return this.aggroList.get(creature.getGameObjectId());
    }

    @Override
    public boolean isEmpty() {
        return this.aggroList.isEmpty();
    }
}
