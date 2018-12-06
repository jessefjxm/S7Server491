// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PushJewelToSocketEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMPushJewelToSocket extends ReceivablePacket<GameClient> {
    private EItemStorageLocation jewelStorageType;
    private EItemStorageLocation itemStorageType;
    private int jewelIndex;
    private int jewelSlot;
    private int itemSlot;

    public CMPushJewelToSocket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.jewelIndex = this.readCD();
        this.jewelStorageType = EItemStorageLocation.valueOf(this.readC());
        this.jewelSlot = this.readCD();
        this.itemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.itemSlot = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new PushJewelToSocketEvent(player, this.jewelIndex, this.jewelStorageType, this.jewelSlot, this.itemStorageType, this.itemSlot));
        }
    }
}
