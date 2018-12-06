package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.world.Location;

import java.util.List;

public class SMAddDeadBodys extends SendablePacket<GameClient> {
    private static final int maximum = 100;
    private final List<DeadBody> deadBodys;

    public SMAddDeadBodys(final List<DeadBody> deadBodys) {
        if (deadBodys.size() > 100) {
            throw new IllegalArgumentException("Maximum size 100");
        }
        this.deadBodys = deadBodys;
    }

    public static int getMaximum() {
        return 100;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(0);
        int writeHPos = buffer.position();
        buffer.writeH(this.deadBodys.size());
        short totalProcessed = 0;
        for (final Creature crt : this.deadBodys) {
            if (!(crt instanceof DeadBody))
                continue;
            ++totalProcessed;
            DeadBody deadBody = (DeadBody) crt;
            final Creature owner = deadBody.getOwner();
            final Location location = owner.getLocation();
            buffer.writeD(deadBody.getGameObjectId());
            buffer.writeD(-1024);
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeH(deadBody.getCreatureId());
            buffer.writeH(deadBody.getCharacterGroup());
            buffer.writeF(owner.getGameStats().getHp().getMaxHp());
            buffer.writeF(owner.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(deadBody.getCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(deadBody.getCache());
            buffer.writeC(0);
            buffer.writeD(owner.getGameObjectId());
            buffer.writeH(deadBody.getCreatureId());
            buffer.writeQ(deadBody.getLootAuthorityTime());
            buffer.writeQ(deadBody.getWinnerPartyId());
            buffer.writeD(deadBody.getWinnerGameObjId());
            buffer.writeD(22);
            buffer.writeC(deadBody.dropIsEmpty());
            buffer.writeC(deadBody.hasCollections());
            buffer.writeC(0);
            buffer.writeH(0);
            buffer.writeD(owner.getActionIndex());
            buffer.writeQ(deadBody.getSpawnTime());
            buffer.writeQ(owner.getObjectId());
            buffer.writeC(1);
            buffer.writeC(0);
        }

        if (totalProcessed != deadBodys.size())
            buffer.getBuffer().putShort(writeHPos, totalProcessed);
    }
}
