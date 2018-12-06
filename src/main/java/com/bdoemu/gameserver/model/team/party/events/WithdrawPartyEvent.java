package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.core.network.sendable.SMWithdrawParty;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.services.PvPBattleService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

import java.util.Collection;

public class WithdrawPartyEvent implements IPartyEvent {
    private Player leader;
    private Player kickedMember;
    private IParty<Player> party;
    private int reason;

    public WithdrawPartyEvent(final Player leader, final Player kickedMember, final IParty<Player> party) {
        this.reason = 0;
        this.leader = leader;
        this.kickedMember = kickedMember;
        this.party = party;
    }

    @Override
    public void onEvent() {
        this.kickedMember.setParty(null);
        this.kickedMember.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.kickedMember));
        for (final Creature summon : this.kickedMember.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        int newOwner = -1024;
        final Collection<Player> elements = this.party.getMembers();
        if (this.party.isPartyLeader(this.kickedMember)) {
            for (final Player player : elements) {
                if (player != null && player != this.kickedMember) {
                    newOwner = player.getGameObjectId();
                    this.party.setLeader(player);
                    break;
                }
            }
        }
        this.party.sendBroadcastPacket(new SMWithdrawParty(this.kickedMember.getGameObjectId(), newOwner, this.reason));
        this.party.removeMember(this.kickedMember);
        if (this.party.getMembers().isEmpty()) {
            PartyService.getInstance().removeParty(this.party);
        }
        if (this.kickedMember.hasPvpMatch()) {
            PvPBattleService.getInstance().cancelDuel(this.kickedMember);
        }
    }

    @Override
    public boolean canAct() {
        if (this.leader == this.kickedMember) {
            this.reason = 0;
        } else {
            if (!this.party.isPartyLeader(this.leader)) {
                return false;
            }
            this.reason = 1;
        }
        return PartyService.getInstance().contains(this.party) && this.leader.getParty() == this.party && this.kickedMember.getParty() == this.party;
    }
}
