// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMGetShopItems;
import com.bdoemu.core.network.sendable.SMGetNormalShopItems;
import com.bdoemu.core.network.sendable.SMGetTradeShopItems;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.enums.EShopType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.ShopItem;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.GameTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GetShopItemsEvent implements IBagEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) GetShopItemsEvent.class);
    }

    private Player player;
    private Npc npc;
    private PlayerBag bag;
    private EShopType shopType;
    private CreatureFunctionT function;
    private List<ShopItem> items;
    private int item, tradeShopType;

    public GetShopItemsEvent(final Player player, final Npc npc) {
        this.player = player;
        this.npc = npc;
        this.bag = player.getPlayerBag();
    }
    
    public GetShopItemsEvent(final Player player, final Npc npc, final int item, final int tradeShopType) {
        this.player = player;
        this.npc = npc;
        this.bag = player.getPlayerBag();
        this.item = item;
        this.shopType = EShopType.valueOf(tradeShopType);
    }

    @Override
    public void onEvent() {
        switch (this.shopType) {
            case RandomShop:
            case RandomNpcWorkerShop: {
                this.bag.setRandomShopItem(this.items.get(0));
                this.player.sendPacket(new SMGetNormalShopItems(this.npc.getGameObjectId(), this.items));
                break;
            }
            case Shop: {
                final AtomicInteger sell = new AtomicInteger(0);
                final AtomicInteger buy = new AtomicInteger(0);
                final ListSplitter<ShopItem> itemSplitter = new ListSplitter<>(this.items, 90);
                while (!itemSplitter.isLast()) {
                    this.player.sendPacket(new SMGetTradeShopItems(this.npc, this.items.size(), itemSplitter.getNext(), itemSplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, buy, sell, this.item, this.tradeShopType));
                }
                break;
            }
        }
    }

    @Override
    public boolean canAct() {
        this.function = this.npc.getTemplate().getCreatureFunctionT();
        if (this.function == null) {
            return false;
        }
        this.shopType = this.function.getShopType();
        this.items = new ArrayList<>();
        switch (this.shopType) {
            case RandomShop:
            case RandomNpcWorkerShop: {
                final List<ItemSubGroupT> drops = ItemMainGroupService.getItems(this.player, this.function.getBuyItemMainGroup());
                if (drops.isEmpty()) {
                    return false;
                }
                final ItemSubGroupT itemSubGroupT = drops.get(0);
                final ShopItem item = new ShopItem(itemSubGroupT, -1, 1000000);
                this.items.add(item);
                if (!this.player.addWp(this.shopType.isRandomShop() ? -10 : -5)) {
                    return false;
                }
                break;
            }
            case Shop: {
                List<ItemSubGroupT> drops;
                if (this.function.getTradeItemMainGroup() > 0) {
                    drops = ItemMainGroupService.getItems(this.player, this.function.getTradeItemMainGroup());
                    if (drops.isEmpty()) {
                        return false;
                    }
                    //final long timeRemaining = 600000L - (GameTimeService.getServerTimeInMillis() - this.player.getLastLogin());
                    //if (timeRemaining > 0L) {
                    //    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemTradeEnterTimeIsRestricted, CMGetShopItems.class, timeRemaining));
                    //    return false;
                    //}
                } else {
                    drops = ItemMainGroupService.getItems(this.player, this.function.getSellItemMainGroup());
                    if (drops.isEmpty()) {
                        return false;
                    }
                }
                this.items.addAll(drops.stream().map(itemSubGroup -> new ShopItem(itemSubGroup, -1, 1000000)).collect(Collectors.toList()));
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}
