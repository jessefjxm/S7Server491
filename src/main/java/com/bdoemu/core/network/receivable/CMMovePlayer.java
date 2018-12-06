package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMovePlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMMovePlayer extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMMovePlayer.class);
    }

    private float x;
    private float y;
    private float z;
    private float vehicleX;
    private float vehicleY;
    private float vehicleZ;
    private float cos;
    private float sin;
    private float next;

    public CMMovePlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
        this.cos = this.readF();
        this.readD();
        this.sin = this.readF();
        this.vehicleX = this.readF();
        this.vehicleZ = this.readF();
        this.vehicleY = this.readF();
        this.next = this.readF();
        this.skipAll();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = player.getLocation();
            if (World.getInstance().getWorldMap().updateLocation(player, this.x, this.y)) {
                loc.setLocation(this.x, this.y, this.z, this.cos, this.sin);
                loc.setVehicleLocation(this.vehicleX, this.vehicleY, this.vehicleZ);
                player.sendBroadcastItSelfPacket(new SMMovePlayer(player, this.x, this.y, this.z, this.cos, this.sin, this.x, this.y, this.z, -1024, this.vehicleX, this.vehicleY, this.vehicleZ));
                final Servant servant = player.getCurrentVehicle();
                if (servant != null && servant.getOwner() == player && !servant.getTemplate().isFixed() && World.getInstance().getWorldMap().updateLocation(servant, this.x, this.y)) {
                    servant.getLocation().setLocation(this.x, this.y, this.z, this.cos, this.sin);
                }
            }
        }
    }
}
