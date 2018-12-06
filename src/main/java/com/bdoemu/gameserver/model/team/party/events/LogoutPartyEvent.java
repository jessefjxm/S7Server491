package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LogoutPartyEvent implements IPartyEvent {
    private IParty<Player> party;
    private Player player;
    private Player member;
    private boolean isLeader;

    public LogoutPartyEvent(final IParty<Player> party, final Player player) {
        this.isLeader = false;
        this.party = party;
        this.player = player;
    }

    @Override
    public void onEvent() {
        this.party.getOfflineMembers().add(this.member.getObjectId());
        this.player.setParty(null);
        this.player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.player));
        final InstanceSummon instanceSummon = InstanceSummonService.getInstance().getSummon(this.player.getAccountId());
        if (instanceSummon != null) {
            final List<Player> resetedMembers = new ArrayList<>();
            if (this.isLeader) {
                this.party.getOfflineMembers().remove(this.member.getObjectId());
                for (final long key : instanceSummon.getOwnerMap().keySet()) {
                    if (key != this.player.getAccountId()) {
                        resetedMembers.add(instanceSummon.getOwnerMap().remove(key));
                    }
                }
                for (final Player player : instanceSummon.getOwners()) {
                    this.party.sendBroadcastPacket(new SMResetInstanceSummonActor(player));
                    this.party.sendBroadcastPacket(new SMSetInstanceSummonActor(instanceSummon.getSummon(), player));
                }
                this.party.sendBroadcastPacket(new SMSetInstanceNo(instanceSummon.getSummon().getGameObjectId(), this.player.getPartyCache()));
            } else {
                instanceSummon.getOwnerMap().remove(this.player.getAccountId());
                resetedMembers.add(this.player);

                for (final Player player : resetedMembers)
                    this.party.sendBroadcastPacket(new SMResetInstanceSummonActor(player));
            }
        }
        int newOwner = -1024;
        if (this.isLeader) {
            final Collection<Player> members = this.party.getMembers();
            for (final Player member : members) {
                this.party.setLeader(member);
                newOwner = member.getGameObjectId();
            }
        }
        if (this.party.getMembers().isEmpty()) {
            PartyService.getInstance().removeParty(this.party);
        } else {
            this.party.sendBroadcastPacket(new SMWithdrawParty(this.player.getGameObjectId(), newOwner, 0));
        }
    }

    @Override
    public boolean canAct() {
        this.member = this.party.removeMember(this.player);
        this.isLeader = this.party.isPartyLeader(this.player);
        return PartyService.getInstance().contains(this.party) && this.player.getParty() == this.party && this.member != null;
    }
}