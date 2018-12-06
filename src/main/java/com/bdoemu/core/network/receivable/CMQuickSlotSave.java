// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.ui.QuickSlot;

public class CMQuickSlotSave extends ReceivablePacket<GameClient> {
    private String quickSlotData;

    public CMQuickSlotSave(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.quickSlotData = this.reads(757);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final QuickSlot quickSlot = player.getQuickSlot();
            if (!this.quickSlotData.isEmpty()) {
                quickSlot.update(this.quickSlotData);
            }
        }
    }
}
