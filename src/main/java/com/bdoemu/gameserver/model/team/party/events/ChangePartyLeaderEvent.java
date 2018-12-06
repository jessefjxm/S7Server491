package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMChangePartyLeader;
import com.bdoemu.core.network.sendable.SMChangePartyLeader;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

public class ChangePartyLeaderEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player oldOwner;
    private Player nextOwner;

    public ChangePartyLeaderEvent(final IParty<Player> party, final Player oldOwner, final Player nextOwner) {
        this.party = party;
        this.oldOwner = oldOwner;
        this.nextOwner = nextOwner;
    }

    @Override
    public void onEvent() {
        this.party.setLeader(this.nextOwner);
        this.party.sendBroadcastPacket(new SMChangePartyLeader(this.nextOwner.getGameObjectId()));
    }

    @Override
    public boolean canAct() {
        if (InstanceSummonService.getInstance().contains(this.oldOwner.getAccountId())) {
            this.oldOwner.sendPacket(new SMNak(EStringTable.eErrNoDontChangePartyLeaderBySummonMonster, CMChangePartyLeader.class));
            return false;
        }
        return PartyService.getInstance().contains(this.party) && this.oldOwner.getParty() == this.party && this.nextOwner.getParty() == this.party && this.party.isPartyLeader(this.oldOwner);
    }
}