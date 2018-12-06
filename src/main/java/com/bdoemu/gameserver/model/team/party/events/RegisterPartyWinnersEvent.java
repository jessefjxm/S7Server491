package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.PartyAggroInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.service.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterPartyWinnersEvent implements IPartyEvent {
    private static final Logger log = LoggerFactory.getLogger((Class) RegisterPartyWinnersEvent.class);
    private IParty<Player> party;
    private EPartyLootType LootingType;
    private PartyAggroInfo partyAggroInfo;
    private Creature target;

    public RegisterPartyWinnersEvent(final Creature target, final IParty<Player> party, final PartyAggroInfo partyAggroInfo) {
        this.target = target;
        this.party = party;
        this.partyAggroInfo = partyAggroInfo;
        this.LootingType = partyAggroInfo.getLootType();
    }

    @Override
    public void onEvent() {
        final Collection<Player> onlineMembers = this.party.getMembers().stream().filter(player -> KnowList.knowObject(this.target, player)).collect(Collectors.toList());
        final int size = onlineMembers.size();
        final List<Long> winners = new ArrayList<Long>();
        if (size > 0) {
            switch (this.LootingType) {
                case Free: {
                    winners.addAll(onlineMembers.stream().map(Creature::getObjectId).collect(Collectors.toList()));
                    this.partyAggroInfo.setPartyId(this.party.getPartyId());
                    break;
                }
                case Shuffle: {
                    final Player[] members = onlineMembers.toArray(new Player[size]);
                    final Player _member = members[this.party.nextShuffleIndex(size)];
                    this.partyAggroInfo.setKiller(_member);
                    winners.add(_member.getObjectId());
                    break;
                }
                case Random: {
                    final Player[] members = onlineMembers.toArray(new Player[size]);
                    final Player m = members[Rnd.get(0, size - 1)];
                    this.partyAggroInfo.setKiller(m);
                    winners.add(m.getObjectId());
                    break;
                }
                case Master: {
                    final Player master = this.party.getLeader();
                    if (master != null) {
                        this.partyAggroInfo.setKiller(master);
                        winners.add(master.getObjectId());
                        break;
                    }
                    break;
                }
                case PartyInven: {
                    for (final Player member : onlineMembers) {
                        winners.add(member.getObjectId());
                    }
                    this.partyAggroInfo.setPartyId(this.party.getPartyId());
                    break;
                }
            }
        } else {
            RegisterPartyWinnersEvent.log.warn("RegisterPartyWinnersEvent has no winners");
        }
        this.partyAggroInfo.setWinners(winners);
    }

    @Override
    public boolean canAct() {
        return PartyService.getInstance().contains(this.party);
    }
}