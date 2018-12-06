// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.SetHouseLargeCraftExchangeEvent;

public class CMSetHouseLargeCraftExchange extends ReceivablePacket<GameClient> {
    private int houseId;
    private int recipeId;

    public CMSetHouseLargeCraftExchange(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseId = this.readH();
        this.recipeId = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getHouseStorage().onEvent(new SetHouseLargeCraftExchangeEvent(player, this.houseId, this.recipeId));
        }
    }
}
