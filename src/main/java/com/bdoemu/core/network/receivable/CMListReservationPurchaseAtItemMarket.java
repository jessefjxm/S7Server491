// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListReservationPurchaseAtItemMarket;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;

public class CMListReservationPurchaseAtItemMarket extends ReceivablePacket<GameClient> {
    private int npcGameObjId;

    public CMListReservationPurchaseAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readQ();
        this.npcGameObjId = this.readD();
        this.readC();
        this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMListReservationPurchaseAtItemMarket(ItemMarketService.getInstance().getReservationItemsMarket(player.getAccountId()), 1));
        }
    }
}
