// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMStartCharacterMoving extends SendablePacket<GameClient> {
    private Creature creature;

    public SMStartCharacterMoving(final Creature creature) {
        this.creature = creature;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.creature.getGameObjectId());
        buffer.writeD(this.creature.getAggroList().getTargetGameObjId());
        buffer.writeD(-1024);
        final Location destination = this.creature.getMovementController().getDestination();
        buffer.writeF(destination.getX());
        buffer.writeF(destination.getZ());
        buffer.writeF(destination.getY());
        final Location origin = this.creature.getMovementController().getOrigin();
        buffer.writeF(origin.getX());
        buffer.writeF(origin.getZ());
        buffer.writeF(origin.getY());
        buffer.writeB(new byte[44]);
        buffer.writeD(0); // what do you do!?
        buffer.writeB(new byte[16]);
        buffer.writeD(this.creature.getGameStats().getMoveSpeedRate().getMoveSpeedRate());
        buffer.writeD(this.creature.getActionStorage().getActionHash());
        buffer.writeF(this.creature.getActionStorage().getAction().getBlendTime());
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeB(new byte[9]);
        buffer.writeC(this.creature.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().ordinal());
        buffer.writeD(this.creature.getActionStorage().getAction().getSpeedRate());
        buffer.writeD(0);
        buffer.writeF(this.creature.getLocation().getCos());
        buffer.writeD(0);
        buffer.writeF(this.creature.getLocation().getSin());
        buffer.writeF(this.creature.getLocation().getCos());
        buffer.writeD(0);
        buffer.writeF(this.creature.getLocation().getSin());
    }
}
