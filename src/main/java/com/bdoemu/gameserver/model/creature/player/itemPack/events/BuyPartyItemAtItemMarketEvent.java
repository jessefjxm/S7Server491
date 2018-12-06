// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class BuyPartyItemAtItemMarketEvent extends AItemEvent {
    private PartyItemMarket partyItemMarket;
    private long itemPrice;
    private EItemStorageLocation storageLocation;

    public BuyPartyItemAtItemMarketEvent(final Player player, final PartyItemMarket partyItemMarket, final long itemPrice, final EItemStorageLocation storageLocation, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemBuyFromItemMarket, EStringTable.eErrNoItemBuyFromItemMarket, regionId);
        this.partyItemMarket = partyItemMarket;
        this.itemPrice = itemPrice;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        this.decreaseItem(0, this.itemPrice, this.storageLocation);
        this.addItem(this.partyItemMarket.getItemId(), this.partyItemMarket.getCount(), this.partyItemMarket.getEnchantLevel());
        return super.canAct() && this.partyItemMarket.buy(this.player, this.itemPrice);
    }
}
