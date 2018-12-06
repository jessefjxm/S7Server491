// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSaveQuestSelectType extends ReceivablePacket<GameClient> {
    private byte[] data;

    public CMSaveQuestSelectType(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.data = this.readB(25);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerQuestHandler().setQuestSelectType(this.data);
        }
    }
}
