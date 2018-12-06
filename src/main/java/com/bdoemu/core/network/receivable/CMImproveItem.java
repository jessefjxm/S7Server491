// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ImproveItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMImproveItem extends ReceivablePacket<GameClient> {
    private EItemStorageLocation itemStorageLocation;
    private EItemStorageLocation storageLocation;
    private int itemSlotIndex;
    private int slotIndex;

    public CMImproveItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemStorageLocation = EItemStorageLocation.values()[this.readC()];
        this.itemSlotIndex = this.readCD();
        this.readD();
        this.readH();
        this.readC();
        this.storageLocation = EItemStorageLocation.values()[this.readC()];
        this.slotIndex = this.readCD();
        this.readD();
        this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ImproveItemEvent(player, this.itemStorageLocation, this.itemSlotIndex, this.storageLocation, this.slotIndex));
        }
    }
}
