// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.trade.Trade;
import com.bdoemu.gameserver.model.trade.events.CancelItemExchangeEvent;

public class CMCancelItemExchangeWithPlayer extends ReceivablePacket<GameClient> {
    private int gameObjId;
    private long message;

    public CMCancelItemExchangeWithPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
        this.message = this.readDQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Trade trade = player.getTrade();
            if (trade != null) {
                trade.onEvent(new CancelItemExchangeEvent(player, trade));
            }
        }
    }
}
