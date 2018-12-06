// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;

public class SMRegisterWorkerAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private NpcWorker npcWorker;

    public SMRegisterWorkerAuctionGoodsVer2(final NpcWorker npcWorker) {
        this.npcWorker = npcWorker;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.npcWorker.getObjectId());
        buffer.writeQ(22158322669L);
        buffer.writeQ(22244722669L);
    }
}
