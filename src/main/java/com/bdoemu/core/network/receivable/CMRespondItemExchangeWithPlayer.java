// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.trade.services.TradeService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRespondItemExchangeWithPlayer extends ReceivablePacket<GameClient> {
    private int gameObjId;

    public CMRespondItemExchangeWithPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Player owner = World.getInstance().getPlayer(this.gameObjId);
            if (owner != null) {
                TradeService.trade(player, owner);
            }
        }
    }
}
