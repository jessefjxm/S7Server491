// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMovePlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

public class CMMovePlayerOnCarrier extends ReceivablePacket<GameClient> {
    private float x;
    private float y;
    private float z;
    private float cos;
    private float sin;
    private float carrierX;
    private float carrierY;
    private float carrierZ;
    private float carrierCos;
    private float carrierSin;
    private float vehicleX;
    private float vehicleY;
    private float vehicleZ;
    private int carrierGameObjId;

    public CMMovePlayerOnCarrier(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
        this.cos = this.readF();
        this.readD();
        this.sin = this.readF();
        this.readD();
        this.readD();
        this.readD();
        this.carrierGameObjId = this.readD();
        this.carrierX = this.readF();
        this.carrierZ = this.readF();
        this.carrierY = this.readF();
        this.carrierCos = this.readF();
        this.readD();
        this.carrierSin = this.readF();
        this.vehicleX = this.readF();
        this.vehicleZ = this.readF();
        this.vehicleY = this.readF();
        this.skipAll();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = player.getLocation();
            if (World.getInstance().getWorldMap().updateLocation(player, this.x, this.y)) {
                loc.setLocation(this.x, this.y, this.z, this.cos, this.sin);
                player.sendBroadcastItSelfPacket(new SMMovePlayer(player, this.x, this.y, this.z, this.cos, this.sin, this.carrierX, this.carrierY, this.carrierZ, this.carrierGameObjId, this.vehicleX, this.vehicleY, this.vehicleZ));
            }
        }
    }
}
