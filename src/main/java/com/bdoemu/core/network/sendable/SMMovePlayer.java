// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMMovePlayer extends SendablePacket<GameClient> {
    private final double x;
    private final double y;
    private final double z;
    private final double cos;
    private final double sin;
    private final double carrierX;
    private final double carrierY;
    private final double carrierZ;
    private final double vehicleX;
    private final double vehicleY;
    private final double vehicleZ;
    private final Player player;
    private final int carrierGameObjId;

    public SMMovePlayer(final Player player, final double x, final double y, final double z, final double cos, final double sin, final double carrierX, final double carrierY, final double carrierZ, final int carrierGameObjId, final double vehicleX, final double vehicleY, final double vehicleZ) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.sin = sin;
        this.carrierX = carrierX;
        this.carrierY = carrierY;
        this.carrierZ = carrierZ;
        this.carrierGameObjId = carrierGameObjId;
        this.vehicleX = vehicleX;
        this.vehicleY = vehicleY;
        this.vehicleZ = vehicleZ;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeF(this.carrierX);
        buffer.writeF(this.carrierZ);
        buffer.writeF(this.carrierY);
        buffer.writeF(this.x);
        buffer.writeF(this.z);
        buffer.writeF(this.y);
        buffer.writeF(this.cos);
        buffer.writeD(0);
        buffer.writeF(this.sin);
        buffer.writeF(this.vehicleX);
        buffer.writeF(this.vehicleZ);
        buffer.writeF(this.vehicleY);
        buffer.writeD(this.player.getActionStorage().getActionHash());
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeD(this.carrierGameObjId);
    }
}
