// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ReservationItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;

public class CancelReservationPurchaseAtItemMarketEvent extends AItemEvent {
    private int itemId;
    private int enchantLevel;
    private long moneyObjId;
    private ETerritoryKeyType territoryKeyType;
    private EItemStorageLocation storageLocation;

    public CancelReservationPurchaseAtItemMarketEvent(final Player player, final int itemId, final int enchantLevel, final ETerritoryKeyType territoryKeyType, final int regionId, final EItemStorageLocation storageLocation, final long moneyObjId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.itemId = itemId;
        this.moneyObjId = moneyObjId;
        this.enchantLevel = enchantLevel;
        this.territoryKeyType = territoryKeyType;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMNotifyItemMarketInfo(ENotifyItemMarketInfoType.CANCEL_RESERVATION, this.moneyObjId, this.itemId, this.enchantLevel));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        ReservationItemMarket reservationItemMarket = null;
        for (final ReservationItemMarket _reservationItemMarket : ItemMarketService.getInstance().getReservationItemsMarket(this.player.getAccountId())) {
            if (_reservationItemMarket.getItemId() == this.itemId && _reservationItemMarket.getEnchantLevel() == this.enchantLevel && this.territoryKeyType == _reservationItemMarket.getTerritoryKeyType()) {
                reservationItemMarket = _reservationItemMarket;
                break;
            }
        }
        if (reservationItemMarket == null) {
            return false;
        }
        final Item moneyItem = new Item(1, reservationItemMarket.getMoney() * reservationItemMarket.getCount(), 0);
        if (this.storageLocation.isWarehouse()) {
            this.addWHItem(moneyItem);
        } else {
            this.addItem(moneyItem);
        }
        return super.canAct() && ItemMarketService.getInstance().cancelReservationPurchaseAtItemMarket(this.player, this.itemId, this.enchantLevel);
    }
}
