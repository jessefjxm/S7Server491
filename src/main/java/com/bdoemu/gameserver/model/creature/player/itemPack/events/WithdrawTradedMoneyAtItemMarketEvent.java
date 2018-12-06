// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.databaseCollections.ItemMarketDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;

public class WithdrawTradedMoneyAtItemMarketEvent extends AItemEvent {
    private long itemMarketObjectId;
    private long revenue;
    private ItemMarket itemMarket;
    private EItemStorageLocation dstLocation;

    public WithdrawTradedMoneyAtItemMarketEvent(final Player player, final EItemStorageLocation dstLocation, final long itemMarketObjectId, final long revenue, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemIsCreatedOtPopFromAuction, EStringTable.eErrNoItemIsCreatedOtPopFromAuction, regionId);
        this.itemMarketObjectId = itemMarketObjectId;
        this.revenue = revenue;
        this.dstLocation = dstLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.itemMarket.getCount() <= 0L && this.itemMarket.getRevenue() <= 0L) {
            ItemMarketService.getInstance().removeItem(this.itemMarketObjectId);
        } else {
            ItemMarketDBCollection.getInstance().update(this.itemMarket);
        }
        this.player.sendPacket(new SMNotifyItemMarketInfo(this.itemMarket, ENotifyItemMarketInfoType.WITHDRAW_MONEY));
    }

    @Override
    public boolean canAct() {
        if (!this.dstLocation.isPlayerInventories() && !this.dstLocation.isWarehouse()) {
            return false;
        }
        this.itemMarket = ItemMarketService.getInstance().getItem(this.itemMarketObjectId);
        if (this.itemMarket == null || this.itemMarket.getAccountId() != this.player.getAccountId()) {
            return false;
        }
        if (this.dstLocation.isWarehouse()) {
            this.addWHItem(new Item(1, this.revenue, 0));
        } else {
            this.addItem(1, this.revenue, 0);
        }
        return super.canAct() && this.itemMarket.addRevenue(-this.revenue);
    }
}
