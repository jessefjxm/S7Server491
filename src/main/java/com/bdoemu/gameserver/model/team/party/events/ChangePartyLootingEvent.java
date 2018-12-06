package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.SMChangeLooting;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

public class ChangePartyLootingEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player owner;
    private EPartyLootType lootType;

    public ChangePartyLootingEvent(final IParty<Player> party, final Player owner, final EPartyLootType lootType) {
        this.party = party;
        this.owner = owner;
        this.lootType = lootType;
    }

    @Override
    public void onEvent() {
        this.party.setLootType(this.lootType);
        this.party.sendBroadcastPacket(new SMChangeLooting(this.lootType));
    }

    @Override
    public boolean canAct() {
        return PartyService.getInstance().contains(this.party) && this.owner.getParty() == this.party && this.party.isPartyLeader(this.owner);
    }
}