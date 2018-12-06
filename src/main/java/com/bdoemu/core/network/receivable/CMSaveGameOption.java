// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSaveGameOption extends ReceivablePacket<GameClient> {
    private String options;

    public CMSaveGameOption(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.options = this.reads(201);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getAccountData().setSaveGameOptions(this.options);
        }
    }
}
