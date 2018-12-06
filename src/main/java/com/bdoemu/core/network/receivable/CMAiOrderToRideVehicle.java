// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class CMAiOrderToRideVehicle extends ReceivablePacket<GameClient> {
    private long handlerHash;

    public CMAiOrderToRideVehicle(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.handlerHash = this.readDQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant currentVehicle = player.getCurrentVehicle();
            if (currentVehicle == null) {
                return;
            }
            currentVehicle.getAi().executeHandler(this.handlerHash, player, null);
        }
    }
}
