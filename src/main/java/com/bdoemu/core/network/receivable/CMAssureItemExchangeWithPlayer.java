// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.trade.Trade;
import com.bdoemu.gameserver.model.trade.events.AssureItemExchangeEvent;

public class CMAssureItemExchangeWithPlayer extends ReceivablePacket<GameClient> {
    public CMAssureItemExchangeWithPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Trade trade = player.getTrade();
            if (trade != null) {
                trade.onEvent(new AssureItemExchangeEvent(player, trade));
            }
        }
    }
}
