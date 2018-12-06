// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CreatePaletteFromItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMCreatePaletteFromItem extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int inventorySlot;
    private long dyeItemObjId;
    private int dyeItemId;
    private long dyeItemcount;
    private boolean unk;

    public CMCreatePaletteFromItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.inventorySlot = this.readCD();
        this.readB(7);
        this.dyeItemObjId = this.readQ();
        this.dyeItemId = this.readD();
        this.dyeItemcount = this.readQ();
        this.unk = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new CreatePaletteFromItemEvent(player, this.storageType, this.inventorySlot));
        }
    }
}
