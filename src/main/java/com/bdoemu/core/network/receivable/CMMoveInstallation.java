// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.MoveHouseInstallationEvent;
import com.bdoemu.gameserver.model.world.Location;

public class CMMoveInstallation extends ReceivablePacket<GameClient> {
    private long houseObjId;
    private long installationObjId;
    private int characterKey;
    private float x;
    private float y;
    private float z;
    private float cos;
    private float sin;

    public CMMoveInstallation(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseObjId = this.readQ();
        this.installationObjId = this.readQ();
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
        this.readD();
        this.cos = this.readF();
        this.readD();
        this.sin = this.readF();
        this.readC();
        this.readQ();
        this.readD();
        this.characterKey = this.readH();
        this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = new Location(this.x, this.y, this.z, this.cos, this.sin);
            player.getHouseStorage().onEvent(new MoveHouseInstallationEvent(player, this.houseObjId, this.installationObjId, loc));
        }
    }
}
