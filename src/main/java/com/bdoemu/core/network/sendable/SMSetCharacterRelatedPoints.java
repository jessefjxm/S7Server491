// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterRelatedPoints extends SendablePacket<GameClient> {
    private final int mpDiff;
    private final Creature owner;

    public SMSetCharacterRelatedPoints(final Creature owner) {
        this(owner, owner.getGameStats().getMp().getIntValue());
    }

    public SMSetCharacterRelatedPoints(final Creature owner, final int mpDiff) {
        this.mpDiff = mpDiff;
        this.owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final GameStats stats = this.owner.getGameStats();
        buffer.writeQ(this.owner.getPartyCache());
        buffer.writeQ(this.owner.getGuildCache());
        buffer.writeD(this.owner.getGameObjectId());
        buffer.writeQ(stats.getMp().getMpCacheCount());
        buffer.writeD(stats.getMp().getIntMaxValue());
        buffer.writeD(stats.getMp().getIntValue());
        buffer.writeD(this.mpDiff);
        buffer.writeC(0);
    }
}
