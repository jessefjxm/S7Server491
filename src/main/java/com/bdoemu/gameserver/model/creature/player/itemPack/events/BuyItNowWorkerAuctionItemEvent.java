// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMAddMyNpcWorker;
import com.bdoemu.core.network.sendable.SMBuyItNowWorkerAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.NpcWorkerAuctionDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collections;

public class BuyItNowWorkerAuctionItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private NpcWorkerItemMarket npcWorkerItemMarket;

    public BuyItNowWorkerAuctionItemEvent(final Player player, final NpcWorkerItemMarket npcWorkerItemMarket, final EItemStorageLocation storageLocation, final int regionId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.npcWorkerItemMarket = npcWorkerItemMarket;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.npcWorkerItemMarket.setSold(true);
        final Player owner = World.getInstance().getPlayerByAccount(this.npcWorkerItemMarket.getAccountId());
        if (owner != null) {
            owner.getNpcWorkerController().removeNpcWorker(this.npcWorkerItemMarket.getNpcWorker());
            owner.sendPacket(new SMBuyItNowWorkerAuctionGoodsVer2(this.npcWorkerItemMarket.getNpcWorker().getObjectId()));
        }
        final NpcWorker npcWorker = new NpcWorker(this.npcWorkerItemMarket.getNpcWorker(), this.player.getAccountId());
        this.player.getNpcWorkerController().addNpcWorker(npcWorker);
        this.player.sendPacket(new SMBuyItNowWorkerAuctionGoodsVer2(npcWorker.getObjectId()));
        this.player.sendPacket(new SMAddMyNpcWorker(Collections.singletonList(npcWorker), EPacketTaskType.Update));
        NpcWorkerAuctionDBCollection.getInstance().update(this.npcWorkerItemMarket);
    }

    @Override
    public boolean canAct() {
        return (this.storageLocation.isInventory() || this.storageLocation.isWarehouse()) && super.canAct() && AuctionGoodService.getInstance().buyNpcWorkerItemMarket(this.npcWorkerItemMarket, this.player);
    }
}
