package com.bdoemu.gameserver.model.trade.events;

import com.bdoemu.core.network.sendable.SMRegisterItemExchangeWithPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.trade.PlayerTradeBag;
import com.bdoemu.gameserver.model.trade.Trade;

public class RegisterItemExchangeEvent implements ITradeEvent {
    private Trade trade;
    private Player player;
    private int tradeSlot;
    private int invSlot;
    private int tradeType;
    private PlayerTradeBag tradeBag;
    private EItemStorageLocation storageLocation;
    private long count;

    public RegisterItemExchangeEvent(final Player player, final Trade trade, final EItemStorageLocation storageLocation, final int tradeSlot, final int invSlot, final int tradeType, final long count) {
        this.trade = trade;
        this.player = player;
        this.storageLocation = storageLocation;
        this.tradeSlot = tradeSlot;
        this.invSlot = invSlot;
        this.tradeType = tradeType;
        this.tradeBag = trade.getPlayerTradeBag(player);
        this.count = count;
    }

    @Override
    public void onEvent() {
        boolean result = false;
        Item item;
        if (this.invSlot == 255) {
            item = this.trade.removeItem(this.player, this.tradeSlot);
            if (item == null) {
                return;
            }
        } else {
            result = true;
            final ItemPack itemPack = this.player.getPlayerBag().getItemPack(this.storageLocation);
            item = itemPack.getItem(this.invSlot);
            if (item == null || item.getCount() < this.count) {
                return;
            }
            if (!item.getTemplate().isPersonalTrade()) {
                return;
            }
            final Item tItem = new Item(item, this.count);
            tItem.setSlotIndex(item.getSlotIndex());
            tItem.setObjectId(item.getObjectId());
            tItem.setStorageLocation(item.getStorageLocation());
            item = tItem;
            if (!this.trade.putItem(this.player, item, this.tradeSlot)) {
                return;
            }
        }
        this.trade.sendBroadcastPacket(new SMRegisterItemExchangeWithPlayer(this.player, item, this.tradeSlot, this.invSlot, this.tradeType, result));
    }

    @Override
    public boolean canAct() {
        return this.storageLocation.isPlayerInventories() && this.player.getTrade() == this.trade && !this.tradeBag.isLock();
    }
}
