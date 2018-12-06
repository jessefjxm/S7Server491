// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.events.QuickCompleteNpcWorkEvent;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMQuickCompleteNpcWork extends ReceivablePacket<GameClient> {
    private long objectId;
    private long count;
    private int completeType;

    public CMQuickCompleteNpcWork(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.objectId = this.readQ();
        this.count = this.readQ();
        this.completeType = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getNpcWorkerController().onEvent(new QuickCompleteNpcWorkEvent(player, this.objectId, this.count, this.completeType));
        }
    }
}
