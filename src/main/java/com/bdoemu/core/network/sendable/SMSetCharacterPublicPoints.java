// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterPublicPoints extends SendablePacket<GameClient> {
    private final float hpDiff;
    private final Creature target;
    private final int ownerSession;

    public SMSetCharacterPublicPoints(final Creature target) {
        this(target, -1024, target.getGameStats().getHp().getCurrentHp());
    }

    public SMSetCharacterPublicPoints(final Creature target, final float hpDiff) {
        this(target, -1024, hpDiff);
    }

    public SMSetCharacterPublicPoints(final Creature target, final int ownerSession, final float hpDiff) {
        this.target = target;
        this.hpDiff = hpDiff;
        this.ownerSession = ownerSession;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final GameStats stats = this.target.getGameStats();
        buffer.writeQ(this.target.getPartyCache());
        buffer.writeQ(this.target.getGuildCache());
        buffer.writeD(this.target.getGameObjectId());
        buffer.writeQ(stats.getHp().getHealCacheCount());
        buffer.writeF(stats.getHp().getMaxHp());
        buffer.writeF(stats.getHp().getCurrentHp());
        buffer.writeF(this.hpDiff);
        buffer.writeD(this.ownerSession);
        buffer.writeD(this.target.getGameStats().getSwimmingStat().getIntMaxValue());
    }
}
