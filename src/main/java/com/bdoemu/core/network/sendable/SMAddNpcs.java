// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMAddNpcs extends SendablePacket<GameClient> {
    public static final int MAXIMUM = 15;
    private final Collection<Npc> npcs;

    public SMAddNpcs(final Collection<Npc> npcs) {
        if (npcs.size() > 15) {
            throw new IllegalArgumentException("Maximum size entries size is 15");
        }
        this.npcs = npcs;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeH(this.npcs.size());
        for (final Npc npc : this.npcs) {
            final Location location = npc.getLocation();
            buffer.writeD(npc.getGameObjectId());
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
            buffer.writeD(npc.getCreatureId());
            buffer.writeF(npc.getGameStats().getHp().getCurrentHp());
            buffer.writeF(npc.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(npc.getGuildCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(npc.getPartyCache());
            buffer.writeC(npc.getFunction());
            buffer.writeD(npc.getActionStorage().getActionHash());
            buffer.writeD(npc.getActionIndex());
            buffer.writeD(npc.getDialogIndex());
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeC(npc.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().ordinal());
            buffer.writeD(npc.getActionStorage().getAction().getSpeedRate());
            buffer.writeD(npc.getActionStorage().getAction().getSlowSpeedRate());
            buffer.writeD(0);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeH(0);
            buffer.writeH(10);
            buffer.writeB(new byte[600]);
            buffer.writeD(-2);
            buffer.writeD(-1);
            buffer.writeC(npc.getMovementController().isMoving());
            final Location destination = npc.getMovementController().getDestination();
            buffer.writeF(destination.getX());
            buffer.writeF(destination.getZ());
            buffer.writeF(destination.getY());
            final Location origin = npc.getMovementController().getOrigin();
            buffer.writeF(origin.getX());
            buffer.writeF(origin.getZ());
            buffer.writeF(origin.getY());
            buffer.writeB(new byte[12]);
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeB(new byte[20]);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeQ(0L);
            buffer.writeD(0);
            buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
            buffer.writeH(0);
            buffer.writeD(npc.getOwnerGameObjId());
            buffer.writeQ(1821508125L);
            buffer.writeQ(1821508125L);
            buffer.writeQ(GameTimeService.getServerTimeInMillis());
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeD(-1024);
        }
    }
}
