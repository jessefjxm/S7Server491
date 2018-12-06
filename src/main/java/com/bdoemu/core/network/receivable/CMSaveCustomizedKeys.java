// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSaveCustomizedKeys extends ReceivablePacket<GameClient> {
    private byte[] customizedKeysData;

    public CMSaveCustomizedKeys(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.customizedKeysData = this.readB(605);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.setCustomizedKeys(this.customizedKeysData);
        }
    }
}
