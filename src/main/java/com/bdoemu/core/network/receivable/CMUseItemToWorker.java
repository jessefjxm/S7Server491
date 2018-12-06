// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UseItemToWorkerEvent;

public class CMUseItemToWorker extends ReceivablePacket<GameClient> {
    private long objectId;
    private int slotIndex;
    private long count;

    public CMUseItemToWorker(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.slotIndex = this.readC();
        this.objectId = this.readQ();
        this.count = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new UseItemToWorkerEvent(player, this.objectId, this.slotIndex, this.count));
        }
    }
}
