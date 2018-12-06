// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMPopServantPriceAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class PopServantPriceAuctionGoodsItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private ServantItemMarket servantItemMarket;

    public PopServantPriceAuctionGoodsItemEvent(final Player player, final EItemStorageLocation storageLocation, final ServantItemMarket servantItemMarket, final int regionId) {
        super(player, player, player, EStringTable.eErrNoServantMarketGetMoney, EStringTable.eErrNoServantMarketGetMoney, regionId);
        this.storageLocation = storageLocation;
        this.servantItemMarket = servantItemMarket;
    }

    @Override
    public void onEvent() {
        this.whAddType = 2;
        super.onEvent();
        if (this.servantItemMarket.getServant().getAuctionRegisterType().isServantMatingMarket()) {
            this.servantItemMarket.getServant().setServantState(EServantState.Stable);
            this.servantItemMarket.getServant().setAuctionRegisterType(EAuctionRegisterType.None);
            ServantsDBCollection.getInstance().save(this.servantItemMarket.getServant());
        }
        this.player.sendPacket(new SMPopServantPriceAuctionGoodsVer2(this.servantItemMarket.getServant().getObjectId()));
        ServantAuctionDBCollection.getInstance().delete(this.servantItemMarket.getObjectId());
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        final Item addItem = new Item(1, this.servantItemMarket.getPrice(), 0);
        if (this.storageLocation.isWarehouse()) {
            this.addWHItem(addItem);
        } else {
            this.addItem(addItem);
        }
        return super.canAct() && AuctionGoodService.getInstance().removeServant(this.servantItemMarket);
    }
}
