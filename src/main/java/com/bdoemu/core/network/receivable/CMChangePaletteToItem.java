// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangePaletteToItemEvent;

public class CMChangePaletteToItem extends ReceivablePacket<GameClient> {
    private int dyeItemId;
    private long dyeItemcount;
    private boolean unk;

    public CMChangePaletteToItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.dyeItemId = this.readD();
        this.dyeItemcount = this.readQ();
        this.unk = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ChangePaletteToItemEvent(player, this.dyeItemId, this.dyeItemcount));
        }
    }
}
