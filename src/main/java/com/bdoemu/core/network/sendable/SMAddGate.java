// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Gate;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMAddGate extends SendablePacket<GameClient> {
    private static final int maximum = 15;
    private final Collection<Gate> gates;

    public SMAddGate(final Collection<Gate> gates) {
        if (gates.size() > 15) {
            throw new IllegalArgumentException("Maximum size entries size is 15");
        }
        this.gates = gates;
    }

    public static int getMaximum() {
        return 15;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        for (final Gate gate : this.gates) {
            final Location location = gate.getLocation();
            buffer.writeD(gate.getGameObjectId());
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
            buffer.writeD(gate.getCreatureId());
            buffer.writeD(gate.getGameStats().getHp().getIntValue());
            buffer.writeD(gate.getGameStats().getHp().getIntMaxValue());
            buffer.writeD(0);
            buffer.writeQ(gate.getObjectId());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(gate.getCache());
            buffer.writeC(gate.getFunction());
            buffer.writeD(gate.getActionStorage().getActionHash());
            buffer.writeD(gate.getActionIndex());
            buffer.writeD(gate.getDialogIndex());
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeC(gate.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().ordinal());
            buffer.writeD(gate.getActionStorage().getAction().getSpeedRate());
            buffer.writeD(gate.getActionStorage().getAction().getSlowSpeedRate());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeB(new byte[600]);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeC(gate.getMovementController().isMoving());
            final Location destination = gate.getMovementController().getDestination();
            buffer.writeF(destination.getX());
            buffer.writeF(destination.getZ());
            buffer.writeF(destination.getY());
            final Location origin = gate.getMovementController().getOrigin();
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
            buffer.writeD(gate.getOwnerGameObjId());
            buffer.writeQ(966044109L);
            buffer.writeQ(966044109L);
            buffer.writeQ(GameTimeService.getServerTimeInMillis());
        }
    }
}
