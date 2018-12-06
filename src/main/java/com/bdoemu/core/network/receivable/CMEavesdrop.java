// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;

public class CMEavesdrop extends ReceivablePacket<GameClient> {
    public CMEavesdrop(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = player.getLocation();
            player.getObserveController().notifyObserver(EObserveType.eavesdrop, loc.getX(), loc.getY(), loc.getZ());
        }
    }
}
