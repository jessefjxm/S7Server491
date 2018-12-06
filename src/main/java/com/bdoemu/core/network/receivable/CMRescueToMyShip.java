// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMovePlayer;
import com.bdoemu.core.network.sendable.SMMovePlayerNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRescueToMyShip extends ReceivablePacket<GameClient> {
    private int gameObjId;
    private float x;
    private float y;
    private float z;

    public CMRescueToMyShip(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = player.getLocation();
            final Servant servant = player.getServantController().getServant(this.gameObjId);
            if (servant != null && servant.getServantType().isShip() && World.getInstance().getWorldMap().updateLocation(player, this.x, this.y)) {
                loc.setLocation(this.x, this.y, this.z, loc.getCos(), loc.getSin());
                player.sendBroadcastItSelfPacket(new SMMovePlayer(player, this.x, this.y, this.z, loc.getCos(), loc.getSin(), 0.0, 0.0, 0.0, 0, 0.0, 0.0, 0.0));
                player.sendPacket(new SMMovePlayerNak(this.x, this.y, this.z, EStringTable.NONE));
            }
        }
    }
}
