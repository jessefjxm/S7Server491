// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListCashItem;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.Collections;

public class CMListCashItem extends ReceivablePacket<GameClient> {
    private long date;

    public CMListCashItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.date = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMListCashItem(Collections.emptyList(), this.date, 1));
        }
    }
}
