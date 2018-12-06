package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.SMPartyMembers;
import com.bdoemu.core.network.sendable.SMPartyNewMember;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.core.network.sendable.SMSetInstanceSummonActor;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.AddPartyFriendEvent;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

import java.util.ArrayList;
import java.util.Collection;

public class LoginPartyEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player player;

    public LoginPartyEvent(final IParty<Player> party, final Player player) {
        this.party = party;
        this.player = player;
    }

    @Override
    public void onEvent() {
        this.player.setParty(this.party);
        for (final Player partyMember : this.party.getMembers()) {
            partyMember.getFriendHandler().onEvent(new AddPartyFriendEvent(partyMember, this.player));
            this.player.getFriendHandler().onEvent(new AddPartyFriendEvent(this.player, partyMember));
        }
        this.player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.player));
        for (final Creature summon : this.player.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        this.party.sendBroadcastPacket(new SMPartyNewMember(this.player));
        this.party.addMember(this.player);
        final Collection<Player> members = new ArrayList<Player>(this.party.getMembers());
        this.player.sendPacket(new SMPartyMembers(this.party, members));
        final InstanceSummon instanceSummon = InstanceSummonService.getInstance().getSummon(this.party.getLeader().getAccountId());
        if (instanceSummon != null) {
            instanceSummon.putMember(this.player);
            this.party.sendBroadcastPacket(new SMSetInstanceSummonActor(instanceSummon.getSummon(), this.player));
        }
    }

    @Override
    public boolean canAct() {
        return PartyService.getInstance().contains(this.party) && this.party.getOfflineMembers().remove(this.player.getObjectId()) && !this.party.getLeader().hasPvpMatch();
    }
}