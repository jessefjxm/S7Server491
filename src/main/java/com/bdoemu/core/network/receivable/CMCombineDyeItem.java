// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CombineDyeItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMCombineDyeItem extends ReceivablePacket<GameClient> {
    private int slot1;
    private int slot2;
    private EItemStorageLocation storageType1;
    private EItemStorageLocation storageType2;

    public CMCombineDyeItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType1 = EItemStorageLocation.valueOf(this.readC());
        this.slot1 = this.readCD();
        this.storageType2 = EItemStorageLocation.valueOf(this.readC());
        this.slot2 = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new CombineDyeItemEvent(player, this.slot1, this.slot2, this.storageType1, this.storageType2));
        }
    }
}
