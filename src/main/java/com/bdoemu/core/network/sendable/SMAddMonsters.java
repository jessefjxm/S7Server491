// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMAddMonsters extends SendablePacket<GameClient> {
    private static final int maximum = 14;
    private final Collection<Monster> monsters;

    public SMAddMonsters(final Collection<Monster> monsters) {
        if (monsters.size() > 14) {
            throw new IllegalArgumentException("Maximum size 14");
        }
        this.monsters = monsters;
    }

    public static int getMaximum() {
        return 14;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeH(this.monsters.size());
        for (final Monster monster : this.monsters) {
            final Location location = monster.getLocation();
            buffer.writeD(monster.getGameObjectId());
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
            buffer.writeH(monster.getCreatureId());
            buffer.writeH(monster.getCharacterGroup());
            buffer.writeF(monster.getGameStats().getHp().getCurrentHp());
            buffer.writeF(monster.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(monster.getGuildCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(monster.getPartyCache());
            buffer.writeC(monster.getFunction());
            buffer.writeD(monster.getActionStorage().getActionHash());
            buffer.writeD(monster.getActionIndex());
            buffer.writeD(monster.getDialogIndex());
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeC(monster.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().ordinal());
            buffer.writeD(monster.getActionStorage().getAction().getSpeedRate());
            buffer.writeD(monster.getActionStorage().getAction().getSlowSpeedRate());
            buffer.writeD(0);
            buffer.writeC(0);
            buffer.writeH(0);
            buffer.writeC(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeB(new byte[600]);
            buffer.writeD(-2);
            buffer.writeD(-1);
            buffer.writeC(monster.getMovementController().isMoving());
            final Location destination = monster.getMovementController().getDestination();
            buffer.writeF(destination.getX());
            buffer.writeF(destination.getZ());
            buffer.writeF(destination.getY());
            final Location origin = monster.getMovementController().getOrigin();
            buffer.writeF(origin.getX());
            buffer.writeF(origin.getZ());
            buffer.writeF(origin.getY());
            buffer.writeB(new byte[60]);
            buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
            buffer.writeH(0);
            buffer.writeD(monster.getOwnerGameObjId());
            buffer.writeQ(1887158343L);
            buffer.writeQ(1887158343L);
            buffer.writeQ(GameTimeService.getServerTimeInMillis());
            final InstanceSummon instanceSummon = monster.getInstanceSummon();
            buffer.writeQ((instanceSummon == null) ? -2097151L : instanceSummon.getOwner().getPartyCache());
            buffer.writeB(new byte[51]);
            buffer.writeD(0);
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeF(0);
        }
    }
}
