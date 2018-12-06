package com.bdoemu.gameserver.model.items.services;

import com.bdoemu.MainServer;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMBuyCashItem;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.dataholders.CashProductData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyCashItemEvent;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.templates.CashItemT;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class CashItemService {
    private static final AtomicReference<Object> instance;

    static {
        instance = new AtomicReference<Object>();
    }

    private long shopTime;
    private ConcurrentHashMap<Integer, CashItemT> cashItems;

    private CashItemService() {
        this.cashItems = new ConcurrentHashMap<Integer, CashItemT>();
        for (final CashItemT template : CashProductData.getInstance().getTemplates().values()) {
            if (!template.getProducts().isEmpty() /*&& template.getSalesStartPeriod() != -2 was originally a mod.. lol*/) {
                this.cashItems.put(template.getProductNo(), template);
            }
        }
        this.shopTime = GameTimeService.getServerTimeInSecond();
    }

    public static CashItemService getInstance() {
        Object value = CashItemService.instance.get();
        if (value == null) {
            synchronized (CashItemService.instance) {
                value = CashItemService.instance.get();
                if (value == null) {
                    final CashItemService actualValue = new CashItemService();
                    value = ((actualValue == null) ? CashItemService.instance : actualValue);
                    CashItemService.instance.set(value);
                }
            }
        }
        return (CashItemService) ((value == CashItemService.instance) ? null : value);
    }

    public void buyItem(final Player player, final List<BuyCashItem> buyCashItemList, final boolean isGift) {
        for (final BuyCashItem buyCashItem : buyCashItemList) {
            final long count = buyCashItem.getCount();
            if (count < 1L || count > 20L) {
                return;
            }
            final CashItemT cashItemT = buyCashItem.getCashItemT();
            if (cashItemT == null) {
                continue;
            }
            final long currentTime = GameTimeService.getServerTimeInSecond();
            if (cashItemT.getSalesStartPeriod() > currentTime || cashItemT.getSalesEndPeriod() <= currentTime) {
                continue;
            }
            final boolean isDiscountDate = cashItemT.getDiscountStartPeriod() <= currentTime && cashItemT.getDiscountEndPeriod() > currentTime;
            if (cashItemT.getPriceCash() > 0L) {
                final long price = isDiscountDate ? cashItemT.getDiscountPrice() : cashItemT.getPriceCash();
                if (MainServer.getRmi().addCash(player.getAccountId(), -(price * count))) {
                    final BuyCashItem cashItem = new BuyCashItem(cashItemT.getProductNo(), count, 0L, "", "");
                    player.getPlayerBag().onEvent(new BuyCashItemEvent(player, -1L, cashItem, false, false));
                    return;
                }
                continue;
            } else {
                long accountId = -1L;
                if (isGift) {
                    if (!buyCashItem.getName().isEmpty()) {
                        accountId = PlayersDBCollection.getInstance().getAccountId(buyCashItem.getName());
                    } else if (!buyCashItem.getFamily().isEmpty()) {
                        accountId = AccountsDBCollection.getInstance().getAccountIdByFamily(buyCashItem.getFamily());
                    }
                    if (accountId < 0L) {
                        return;
                    }
                    if (accountId == player.getAccountId()) {
                        player.sendPacket(new SMBuyCashItem(0, 0L, Collections.singletonList(-1L), "", EStringTable.eErrNoCantDoToMyself));
                        return;
                    }
                }
                player.getPlayerBag().onEvent(new BuyCashItemEvent(player, accountId, buyCashItem, isGift, isDiscountDate));
            }
        }
    }

    public long getShopTime() {
        return this.shopTime;
    }

    public Collection<CashItemT> getCashItems() {
        return this.cashItems.values();
    }
}
