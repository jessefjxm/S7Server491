// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExtractBlackStoneEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMExtractBlackStone extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int slotIndex;

    public CMExtractBlackStone(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ExtractBlackStoneEvent(player, this.storageType, this.slotIndex));
        }
    }
}
