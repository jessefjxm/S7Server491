// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExchangeItemSlotEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMExchangeItemSlotInInventory extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int oldSlot;
    private int nextSlot;

    public CMExchangeItemSlotInInventory(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.oldSlot = this.readCD();
        this.nextSlot = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ExchangeItemSlotEvent(player, this.storageType, this.oldSlot, this.nextSlot));
        }
    }
}
