package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAnswerInviteParty;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMPartyMembers;
import com.bdoemu.core.network.sendable.SMPartyNewMember;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.AddPartyFriendEvent;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

import java.util.ArrayList;
import java.util.Collection;

public class NewPartyMemberEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player owner;
    private Player newMember;
    private boolean init;

    public NewPartyMemberEvent(final IParty<Player> party, final Player owner, final Player newMember, final boolean init) {
        this.party = party;
        this.owner = owner;
        this.newMember = newMember;
        this.init = init;
    }

    @Override
    public void onEvent() {
        if (!this.init) {
            this.newMember.setParty(this.party);
        }
        for (final Player player : this.party.getMembers()) {
            player.getFriendHandler().onEvent(new AddPartyFriendEvent(player, this.newMember));
            this.newMember.getFriendHandler().onEvent(new AddPartyFriendEvent(this.newMember, player));
        }
        this.newMember.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.newMember));
        for (final Creature summon : this.newMember.getSummonStorage().getSummons()) {
            summon.sendBroadcastPacket(new SMSetCharacterTeamAndGuild(summon));
        }
        this.party.sendBroadcastPacket(new SMPartyNewMember(this.newMember));
        this.party.addMember(this.newMember);
        final Collection<Player> members = new ArrayList<Player>(this.party.getMembers());
        this.newMember.sendPacket(new SMPartyMembers(this.party, members));
    }

    @Override
    public boolean canAct() {
        if (!this.init && this.newMember.hasParty()) {
            this.newMember.sendPacket(new SMNak(EStringTable.eErrNoPartyMemberDuplication, CMAnswerInviteParty.class));
            return false;
        }
        return PartyService.getInstance().contains(this.party) && !this.party.isFull() && this.owner.getParty() == this.party && this.party.isPartyLeader(this.owner) && !this.owner.hasPvpMatch();
    }
}