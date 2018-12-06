// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMPartyNewMember extends SendablePacket<GameClient> {
    private final Player player;

    public SMPartyNewMember(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final GameStats gameStats = this.player.getGameStats();
        buffer.writeF(gameStats.getHp().getCurrentHp());
        buffer.writeF(gameStats.getHp().getMaxHp());
        buffer.writeD(gameStats.getMp().getCurrentMp());
        buffer.writeD(gameStats.getMp().getMaxMp());
        buffer.writeC(this.player.getClassType().getId());
        buffer.writeD(this.player.getLevel());
        buffer.writeC(0);
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeS((CharSequence) this.player.getName(), 62);
    }
}
