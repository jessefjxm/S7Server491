// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCancelWorkerAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.NpcWorkerAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.NpcWorkersDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkerState;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMCancelWorkerAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long objectId;
    private long npcWorkerObjectId;

    public CMCancelWorkerAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.objectId = this.readQ();
        this.npcWorkerObjectId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null || function.getAuctionKey() == null) {
                return;
            }
            final NpcWorkerItemMarket npcWorkerItemMarket = AuctionGoodService.getInstance().removeNpcWorker(player.getAccountId(), this.objectId);
            if (npcWorkerItemMarket != null) {
                npcWorkerItemMarket.getNpcWorker().setState(ENpcWorkerState.WorkSupervisor);
                npcWorkerItemMarket.getNpcWorker().setAuctionRegisterType(EAuctionRegisterType.None);
                NpcWorkersDBCollection.getInstance().save(npcWorkerItemMarket.getNpcWorker());
                NpcWorkerAuctionDBCollection.getInstance().delete(npcWorkerItemMarket.getObjectId());
                player.sendPacket(new SMCancelWorkerAuctionGoodsVer2(this.npcWorkerObjectId));
            }
        }
    }
}
