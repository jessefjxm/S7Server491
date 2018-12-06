// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PopWorkerPriceAuctionGoodsItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMPopWorkerPriceAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long itemMarketObjId;
    private long workerObjId;
    private long price;
    private long moneyObjId;
    private EItemStorageLocation storageLocation;

    public CMPopWorkerPriceAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.itemMarketObjId = this.readQ();
        this.readC();
        this.workerObjId = this.readQ();
        this.price = this.readQ();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjId = this.readQ();
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
            final NpcWorkerItemMarket npcWorkerItemMarket = AuctionGoodService.getInstance().getNpcWorkerItemMarket(this.itemMarketObjId);
            if (npcWorkerItemMarket == null || !npcWorkerItemMarket.isSold()) {
                return;
            }
            player.getPlayerBag().onEvent(new PopWorkerPriceAuctionGoodsItemEvent(player, this.storageLocation, npcWorkerItemMarket, npc.getRegionId()));
        }
    }
}
