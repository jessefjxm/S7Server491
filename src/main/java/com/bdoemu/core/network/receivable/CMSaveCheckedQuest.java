// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSaveCheckedQuest extends ReceivablePacket<GameClient> {
    private byte[] questListData;

    public CMSaveCheckedQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.questListData = this.readB(301);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerQuestHandler().setCheckedQuestData(this.questListData);
        }
    }
}
