package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMBroadcastRegisterWorkerAtAuction;
import com.bdoemu.core.network.sendable.SMRegisterWorkerAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.NpcWorkerAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.NpcWorkersDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ECharacterGradeType;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkerState;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRegisterWorkerAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long objectId;
    private long price;

    public CMRegisterWorkerAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.objectId = this.readQ();
        this.readC();
        this.price = this.readQ();
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
            final NpcWorker npcWorker = player.getNpcWorkerController().getNpcWorker(this.objectId);
            if (npcWorker == null || npcWorker.getNpcWork() != null || !npcWorker.getState().isWorkSupervisor() || npcWorker.getActionPoints() != npcWorker.getPlantWorkerT().getActionPoint() || !npcWorker.getCreatureTemplate().getCharGrade().isGreatherOrEqual(ECharacterGradeType.Hero)) {
                return;
            }
            NpcWorkersDBCollection.getInstance().delete(this.objectId);
            npcWorker.setState(ENpcWorkerState.WorkerMarket);
            npcWorker.setAuctionRegisterType(EAuctionRegisterType.NpcWorkerMarket);
            final NpcWorkerItemMarket npcWorkerItemMarket = new NpcWorkerItemMarket(npcWorker, player.getAccountId());
            NpcWorkerAuctionDBCollection.getInstance().save(npcWorkerItemMarket);
            AuctionGoodService.getInstance().registerNpcWorker(npcWorkerItemMarket);
            player.sendPacket(new SMRegisterWorkerAuctionGoodsVer2(npcWorker));

            if (npcWorker.getLevel() > 20)
                World.getInstance().broadcastWorldPacket(new SMBroadcastRegisterWorkerAtAuction(npcWorker.getCharacterKey(), price));
        }
    }
}
