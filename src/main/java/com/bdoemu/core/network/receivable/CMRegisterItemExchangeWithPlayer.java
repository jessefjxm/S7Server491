// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.trade.Trade;
import com.bdoemu.gameserver.model.trade.events.RegisterItemExchangeEvent;

public class CMRegisterItemExchangeWithPlayer extends ReceivablePacket<GameClient> {
    private int invSlot;
    private int tradeSlot;
    private int tradeType;
    private long count;
    private int unk1;
    private int unk2;
    private EItemStorageLocation storageLocation;

    public CMRegisterItemExchangeWithPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.tradeType = this.readC();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.invSlot = this.readCD();
        this.tradeSlot = this.readCD();
        this.count = this.readQ();
        this.unk1 = this.readD();
        this.unk2 = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Trade trade = player.getTrade();
            if (trade != null) {
                trade.onEvent(new RegisterItemExchangeEvent(player, trade, this.storageLocation, this.tradeSlot, this.invSlot, this.tradeType, this.count));
            }
        }
    }
}
