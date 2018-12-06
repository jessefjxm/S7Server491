package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.SMStartMatch;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;
import com.bdoemu.gameserver.model.creature.player.duel.enums.EPvpMatchState;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

public class AcceptPvpMatchPartyEvent implements IPartyEvent {
    private Player player;
    private IParty<Player> party;
    private PvpMatch pvpMatch;

    public AcceptPvpMatchPartyEvent(final Player player, final IParty<Player> party, final PvpMatch pvpMatch) {
        this.player = player;
        this.party = party;
        this.pvpMatch = pvpMatch;
    }

    @Override
    public void onEvent() {
        for (final Player member : this.party.getMembers()) {
            this.pvpMatch.addParticipant(member);
            member.getPVPController().initDuel(this.pvpMatch, EPvpMatchState.Duel);
            member.sendBroadcastItSelfPacket(new SMStartMatch(member));
        }
    }

    @Override
    public boolean canAct() {
        return PartyService.getInstance().contains(this.party) && this.player.getParty() == this.party;
    }
}