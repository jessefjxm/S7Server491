// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSaveUiInfo extends ReceivablePacket<GameClient> {
    private String uiData;

    public CMSaveUiInfo(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readD();
        this.readD();
        this.uiData = this.reads(1001);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getAccountData().setUiData(this.uiData);
        }
    }
}
