// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.EnchantItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMEnchantItem extends ReceivablePacket<GameClient> {
    private EItemStorageLocation itemStorageType;
    private EItemStorageLocation stoneStorageType;
    private EItemStorageLocation croneStorageType;
    private int itemSlot;
    private int stoneSlot;
    private int croneSlotIndex;
    private boolean isPerfect;

    public CMEnchantItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.itemSlot = this.readCD();
        this.stoneStorageType = EItemStorageLocation.valueOf(this.readC());
        this.stoneSlot = this.readCD();
        this.croneStorageType = EItemStorageLocation.valueOf(this.readC());
        this.croneSlotIndex = this.readCD();
        this.isPerfect = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new EnchantItemEvent(player, this.itemStorageType, this.itemSlot, this.stoneStorageType, this.stoneSlot, this.croneStorageType, this.croneSlotIndex, this.isPerfect));
        }
    }
}
