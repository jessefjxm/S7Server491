// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExchangeItemToClassEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMExchangeItemToClass extends ReceivablePacket<GameClient> {
    private EItemStorageLocation ticketStorageType;
    private EItemStorageLocation itemStorageType;
    private int ticketSlotIndex;
    private int itemSlotIndex;

    public CMExchangeItemToClass(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.ticketStorageType = EItemStorageLocation.valueOf(this.readC());
        this.ticketSlotIndex = this.readCD();
        this.itemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.itemSlotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ExchangeItemToClassEvent(player, this.ticketStorageType, this.ticketSlotIndex, this.itemStorageType, this.itemSlotIndex));
        }
    }
}
