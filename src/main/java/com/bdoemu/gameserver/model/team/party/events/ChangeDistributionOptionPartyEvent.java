package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.SMChangeDistributionOption;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

public class ChangeDistributionOptionPartyEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player player;
    private long moneyCount;
    private EDistributionItemGrade distributionItemGrade;

    public ChangeDistributionOptionPartyEvent(final IParty<Player> party, final Player player, final EDistributionItemGrade distributionItemGrade, final long moneyCount) {
        this.party = party;
        this.player = player;
        this.moneyCount = moneyCount;
        this.distributionItemGrade = distributionItemGrade;
    }

    @Override
    public void onEvent() {
        this.party.setDistributionPrice(this.moneyCount);
        this.party.setDistributionItemGrade(this.distributionItemGrade);
        this.party.sendBroadcastPacket(new SMChangeDistributionOption(this.moneyCount, this.distributionItemGrade));
    }

    @Override
    public boolean canAct() {
        return PartyService.getInstance().contains(this.party) && this.player.getParty() == this.party && this.party.isPartyLeader(this.player);
    }
}