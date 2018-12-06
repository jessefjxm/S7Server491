// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMRegisterReservationPurchaseAtItemMarket;
import com.bdoemu.core.network.sendable.SMListReservationPurchaseAtItemMarket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;

public class RegisterReservationPurchaseAtItemMarketEvent extends AItemEvent {
    private int itemId;
    private int enchantLevel;
    private long moneyCount;
    private long itemCount;
    private ETerritoryKeyType territoryKeyType;
    private EItemStorageLocation storageLocation;

    public RegisterReservationPurchaseAtItemMarketEvent(final Player player, final int itemId, final int enchantLevel, final long moneyCount, final long itemCount, final EItemStorageLocation storageLocation, final ETerritoryKeyType territoryKeyType, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemBuyFromItemMarket, EStringTable.eErrNoItemBuyFromItemMarket, regionId);
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.moneyCount = moneyCount;
        this.itemCount = itemCount;
        this.territoryKeyType = territoryKeyType;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMListReservationPurchaseAtItemMarket(ItemMarketService.getInstance().getReservationItemsMarket(this.player.getAccountId()), 0));
        this.player.sendPacket(new SMNotifyItemMarketInfo(ENotifyItemMarketInfoType.REGISTER_RESERVATION, 0L, 0, 0));
    }

    @Override
    public boolean canAct() {
        if (this.itemCount <= 0L || this.moneyCount <= 0L) {
            return false;
        }
        if (!this.storageLocation.isPlayerInventories() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        final MasterItemMarket masterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.itemId, this.enchantLevel);
        if (this.moneyCount < masterItemMarket.getItemMaxPrice()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoPriceIsInvalid, CMRegisterReservationPurchaseAtItemMarket.class));
            return false;
        }
        this.decreaseItem(0, this.moneyCount * this.itemCount, this.storageLocation);
        return super.canAct() && ItemMarketService.getInstance().registerReservationAtItemMarket(this.player, this.itemId, this.enchantLevel, this.territoryKeyType, this.moneyCount, this.itemCount);
    }
}
