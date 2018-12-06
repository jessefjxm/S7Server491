// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PopJewelFromSocketEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMPopJewelFromSocket extends ReceivablePacket<GameClient> {
    private EItemStorageLocation itemStorageType;
    private EItemStorageLocation extractStorageType;
    private int itemSlot;
    private int jewelIndex;
    private int extractSlotIndex;

    public CMPopJewelFromSocket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.jewelIndex = this.readCD();
        this.itemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.itemSlot = this.readCD();
        this.extractStorageType = EItemStorageLocation.valueOf(this.readC());
        this.extractSlotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new PopJewelFromSocketEvent(player, this.jewelIndex, this.itemStorageType, this.itemSlot, this.extractStorageType, this.extractSlotIndex));
        }
    }
}
