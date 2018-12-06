// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMPopWorkerPriceAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.NpcWorkerAuctionDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkerState;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class PopWorkerPriceAuctionGoodsItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private NpcWorkerItemMarket npcWorkerItemMarket;

    public PopWorkerPriceAuctionGoodsItemEvent(final Player player, final EItemStorageLocation storageLocation, final NpcWorkerItemMarket npcWorkerItemMarket, final int regionId) {
        super(player, player, player, EStringTable.eErrNoServantMatingGetMoney, EStringTable.eErrNoServantMatingGetMoney, regionId);
        this.storageLocation = storageLocation;
        this.npcWorkerItemMarket = npcWorkerItemMarket;
    }

    @Override
    public void onEvent() {
        this.whAddType = 2;
        super.onEvent();
        this.npcWorkerItemMarket.getNpcWorker().setState(ENpcWorkerState.WorkSupervisor);
        this.player.sendPacket(new SMPopWorkerPriceAuctionGoodsVer2(this.npcWorkerItemMarket.getNpcWorker().getObjectId()));
        NpcWorkerAuctionDBCollection.getInstance().delete(this.npcWorkerItemMarket.getObjectId());
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        final Item addItem = new Item(1, this.npcWorkerItemMarket.getPrice() * 70L / 100L, 0);
        if (this.storageLocation.isWarehouse()) {
            this.addWHItem(addItem);
        } else {
            this.addItem(addItem);
        }
        return super.canAct() && AuctionGoodService.getInstance().removeNpcWorker(this.npcWorkerItemMarket);
    }
}
