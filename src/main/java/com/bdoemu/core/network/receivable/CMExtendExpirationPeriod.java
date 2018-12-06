// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExtendExpirationPeriodItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMExtendExpirationPeriod extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageLocation;
    private EItemStorageLocation chargeStorageLocation;
    private int slotIndex;
    private int chargeSlotIndex;
    private long count;

    public CMExtendExpirationPeriod(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.chargeStorageLocation = EItemStorageLocation.valueOf(this.readC());
        this.chargeSlotIndex = this.readCD();
        this.count = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ExtendExpirationPeriodItemEvent(player, this.storageLocation, this.slotIndex, this.chargeStorageLocation, this.chargeSlotIndex));
        }
    }
}
