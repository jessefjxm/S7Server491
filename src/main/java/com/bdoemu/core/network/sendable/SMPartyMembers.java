// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.containers.GameStats;
import com.bdoemu.gameserver.model.team.party.IParty;

import java.util.Collection;

public class SMPartyMembers extends SendablePacket<GameClient> {
    private final IParty<Player> party;
    private final Collection<Player> members;

    public SMPartyMembers(final IParty<Player> party, final Collection<Player> members) {
        this.party = party;
        this.members = members;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.party.getPartyId());
        buffer.writeC((int) this.party.getLootType().getId());
        buffer.writeQ(this.party.getDistributionPrice());
        buffer.writeC(this.party.getDistributionItemGrade().getId());
        buffer.writeC(0);
        buffer.writeH(this.members.size());
        for (final Player player : this.members) {
            final GameStats gameStats = player.getGameStats();
            buffer.writeF(gameStats.getHp().getCurrentHp());
            buffer.writeF(gameStats.getHp().getMaxHp());
            buffer.writeD(gameStats.getMp().getCurrentMp());
            buffer.writeD(gameStats.getMp().getMaxMp());
            buffer.writeC(player.getClassType().getId());
            buffer.writeD(player.getLevel());
            buffer.writeC(this.party.isPartyLeader(player));
            buffer.writeD(player.getGameObjectId());
            buffer.writeS(player.getName(), 62);
        }
    }
}
