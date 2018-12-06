// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRepurchaseItems;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class CMUpdateRepurchaseList extends ReceivablePacket<GameClient> {
    private int npcSessionId;

    public CMUpdateRepurchaseList(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMRepurchaseItems(player.getPlayerBag().getRepurchaseList(), EPacketTaskType.Update, this.npcSessionId));
        }
    }
}
