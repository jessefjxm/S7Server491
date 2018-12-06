// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.GetBuffFromHouseInstallationEvent;

public class CMGetBuffFromInstallation extends ReceivablePacket<GameClient> {
    private long houseObjId;
    private long installationObjId;

    public CMGetBuffFromInstallation(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseObjId = this.readQ();
        this.installationObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getHouseStorage().onEvent(new GetBuffFromHouseInstallationEvent(player, this.houseObjId, this.installationObjId));
        }
    }
}
