// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMPrepareItemBox;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ADBItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMPrepareItemBox extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int slotIndex;

    public CMPrepareItemBox(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final ADBItemPack pack = (this.storageType == EItemStorageLocation.Inventory) ? player.getPlayerBag().getInventory() : player.getPlayerBag().getCashInventory();
            final Item item = pack.getItem(this.slotIndex);
            if (item != null) {
                final EContentsEventType type = item.getTemplate().getContentsEventType();
                if (type != null && type.isRoulette()) {
                    player.sendPacket(new SMPrepareItemBox(this.slotIndex, this.storageType));
                }
            }
        }
    }
}
