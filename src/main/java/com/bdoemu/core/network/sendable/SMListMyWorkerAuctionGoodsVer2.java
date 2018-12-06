// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMListMyWorkerAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private Collection<NpcWorkerItemMarket> npcWorkers;
    private EPacketTaskType packetTaskType;
    private int auctionKey;

    public SMListMyWorkerAuctionGoodsVer2(final EPacketTaskType packetTaskType, final Collection<NpcWorkerItemMarket> npcWorkers, final int auctionKey) {
        this.packetTaskType = packetTaskType;
        this.npcWorkers = npcWorkers;
        this.auctionKey = auctionKey;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.auctionKey);
        buffer.writeH(this.npcWorkers.size());
        for (final NpcWorkerItemMarket npcWorkerItemMarket : this.npcWorkers) {
            buffer.writeQ(npcWorkerItemMarket.getObjectId());
            buffer.writeC(0);
            buffer.writeQ(npcWorkerItemMarket.getPrice());
            buffer.writeQ(22244722669L);
            buffer.writeQ(npcWorkerItemMarket.getNpcWorker().getObjectId());
            buffer.writeH(npcWorkerItemMarket.getNpcWorker().getCharacterKey());
            buffer.writeD(npcWorkerItemMarket.getNpcWorker().getActionPoints());
            buffer.writeD(npcWorkerItemMarket.getNpcWorker().getWaypointKey());
            buffer.writeD(npcWorkerItemMarket.getNpcWorker().getLevel());
            buffer.writeD(npcWorkerItemMarket.getNpcWorker().getExp());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            for (final int skill : npcWorkerItemMarket.getNpcWorker().getArrPassiveSkills()) {
                buffer.writeH(skill);
            }
            buffer.writeC(npcWorkerItemMarket.getNpcWorker().getUpgradeCount());
            buffer.writeC(npcWorkerItemMarket.getNpcWorker().getState().ordinal());
            buffer.writeC(npcWorkerItemMarket.isSold());
        }
    }
}
