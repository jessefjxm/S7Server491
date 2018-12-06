// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.trade.Trade;

public class CompleteTradeItemEvent extends AItemEvent {
    private Player partner;
    private Trade trade;

    public CompleteTradeItemEvent(final Player player, final Player partner, final Trade trade) {
        super(player, player, player, EStringTable.eErrNoItemIsCreatedToExchangeToPlayer, EStringTable.eErrNoItemIsRemovedToExchangeToPlayer, player.getRegionId());
        this.partner = partner;
        this.trade = trade;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        for (final Item removeItem : this.trade.getPlayerTradeBag(this.player).getItems()) {
            if (removeItem != null) {
                this.decreaseItem(removeItem.getSlotIndex(), removeItem.getCount(), removeItem.getStorageLocation());
            }
        }
        for (final Item addItem : this.trade.getPlayerTradeBag(this.partner).getItems()) {
            if (addItem != null) {
                this.addItem(new Item(addItem, addItem.getCount()));
            }
        }
        this.player.setTrade(null);
        return !super.canAct() || !this.partner.hasTrade() || this.partner.getPlayerBag().onEvent(new CompleteTradeItemEvent(this.partner, this.player, this.trade));
    }
}
