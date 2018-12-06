// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ConvertEnchantFailCountToItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMConvertEnchantFailCountToItem extends ReceivablePacket<GameClient> {
    private byte type;
    private EItemStorageLocation storageType;
    private int slotIndex;

    public CMConvertEnchantFailCountToItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ConvertEnchantFailCountToItemEvent(player, this.storageType, this.type, this.slotIndex));
        }
    }
}
