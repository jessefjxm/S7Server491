// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMBuyCashItem;
import com.bdoemu.core.network.sendable.SMCash;
import com.bdoemu.core.network.sendable.SMListCashProductBuyCount;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.AbstractAddItemPack;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.CashProductBuyCount;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.CashItemT;
import com.bdoemu.gameserver.model.items.templates.CashProductT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BuyCashItemEvent extends AItemEvent {
    private final long receiverAccountId;
    private final CashItemT template;
    private final long count;
    private final boolean isGift;
    private final boolean isDiscountDate;
    private final BuyCashItem buyCashItem;
    private List<Long> addObjectItems;

    public BuyCashItemEvent(final Player player, final long receiverAccountId, final BuyCashItem buyCashItem, final boolean isGift, final boolean isDiscountDate) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.addObjectItems = Collections.emptyList();
        this.buyCashItem = buyCashItem;
        this.template = buyCashItem.getCashItemT();
        this.count = buyCashItem.getCount();
        this.isGift = isGift;
        this.receiverAccountId = receiverAccountId;
        this.isDiscountDate = isDiscountDate;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.addTasks != null) {
            this.addObjectItems = this.addTasks.stream().map(Item::getObjectId).collect(Collectors.toList());
        }
        this.player.sendPacket(new SMBuyCashItem(this.template.getProductNo(), this.count, this.addObjectItems, "", EStringTable.NONE));
        if (this.template.getPriceCash() > 0L) {
            this.player.sendPacket(new SMCash(this.player.getAccountId()));
        }
        if (this.isGift) {
            MailService.getInstance().sendMail(this.receiverAccountId, this.player.getAccountId(), this.player.getName(), "{3183609639|3534079484}", "{3183609639|21775159}", this.buyCashItem);
        }
    }

    @Override
    public boolean canAct() {
        int decreaseItemId = 0;
        long price = 0L;
        if (this.template.getPricePearl() > 0L) {
            decreaseItemId = 6;
            price = (this.isDiscountDate ? this.template.getDiscountPrice() : this.template.getPricePearl());
        } else if (this.template.getPriceMileage() > 0L) {
            decreaseItemId = 7;
            price = (this.isDiscountDate ? this.template.getDiscountPrice() : this.template.getPriceMileage());
        }
        if (decreaseItemId > 0) {
            final int slotIndex = AbstractAddItemPack.staticItems.get(decreaseItemId);
            final ItemTemplate decreaseTemplate = ItemData.getInstance().getItemTemplate(decreaseItemId);
            if (price > 0L) {
                this.decreaseItem(slotIndex, price * this.count, decreaseTemplate.isCash() ? EItemStorageLocation.CashInventory : EItemStorageLocation.Inventory);
            }
        }
        if (!this.isGift) {
            for (final CashProductT product : this.template.getProducts()) {
                final ItemTemplate productTemplate = ItemData.getInstance().getItemTemplate(product.getItemId());
                if (!productTemplate.isStack()) {
                    for (int i = 0; i < this.count; ++i) {
                        this.addItem(productTemplate, 1L, 0);
                    }
                } else {
                    this.addItem(productTemplate, product.getCount() * this.count, 0);
                }
            }
        }
        if (!super.canAct()) {
            return false;
        }
        final int purchaseCountLimit = this.template.getPurchaseCountLimit();
        if (purchaseCountLimit > 0) {
            CashProductBuyCount cashProductBuyCount = this.player.getAccountData().getProductByCount(this.template.getProductNo());
            int updateCashCount = (int) this.count;
            long buyDate = 0L;
            final int resetHour = this.template.getResetHour();
            if (cashProductBuyCount != null) {
                if (resetHour > 0) {
                    buyDate = GameTimeService.getServerTimeInMillis();
                    if (cashProductBuyCount.getBuyDate() + resetHour * 60 * 60 * 1000 > buyDate) {
                        return false;
                    }
                }
                updateCashCount += cashProductBuyCount.getCount();
            }
            if (updateCashCount > purchaseCountLimit) {
                return false;
            }
            cashProductBuyCount = this.player.getAccountData().putProductByCount(this.template.getProductNo(), updateCashCount, buyDate);
            this.player.sendPacket(new SMListCashProductBuyCount(Collections.singletonList(cashProductBuyCount)));
        }
        return true;
    }
}
