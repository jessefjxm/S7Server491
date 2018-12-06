// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.HpStat;
import com.bdoemu.gameserver.model.stats.MpStat;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;

public class SMSetCharacterCurrentRelatedPoints extends SendablePacket<GameClient> {
    private final Collection<Player> members;

    public SMSetCharacterCurrentRelatedPoints(final Collection<Player> members) {
        this.members = members;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(0L);
        buffer.writeQ(0L);
        buffer.writeH(this.members.size());
        for (final Player member : this.members) {
            buffer.writeD(member.getGameObjectId());
            final HpStat hpStat = member.getGameStats().getHp();
            buffer.writeQ(hpStat.getHealCacheCount());
            buffer.writeD(hpStat.getIntMaxValue());
            buffer.writeD(hpStat.getIntValue());
            final MpStat mpStat = member.getGameStats().getMp();
            buffer.writeQ(mpStat.getMpCacheCount());
            buffer.writeD(mpStat.getIntMaxValue());
            buffer.writeD(mpStat.getIntValue());
            final Location loc = member.getLocation();
            buffer.writeF(loc.getX());
            buffer.writeF(loc.getZ());
            buffer.writeF(loc.getY());
            buffer.writeD(member.getLevel());
        }
    }
}
