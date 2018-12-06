// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMBuyItemAtItemMarket;
import com.bdoemu.core.network.sendable.SMListItemHeaderAtItemMarket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.databaseCollections.ItemMarketDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collections;

public class BuyItemAtItemMarketEvent extends AItemEvent {
    private final long itemMarketObjectId;
    private final long count;
    private final int itemId;
    private final int enchant;
    private ItemMarket itemMarket;
    private MasterItemMarket masterItemMarket;
    private long revenue;
    private EItemStorageLocation srcStorageType;

    public BuyItemAtItemMarketEvent(final Player player, final EItemStorageLocation srcStorageType, final long itemMarketObjectId, final long count, final int itemId, final int enchant, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemBuyFromItemMarket, EStringTable.eErrNoItemBuyFromItemMarket, regionId);
        this.itemMarketObjectId = itemMarketObjectId;
        this.srcStorageType = srcStorageType;
        this.count = count;
        this.itemId = itemId;
        this.enchant = enchant;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.itemMarket.addRevenue(this.revenue * 650000L / 1000000L);
        ItemMarketDBCollection.getInstance().update(this.itemMarket);
        this.player.sendPacket(new SMNotifyItemMarketInfo(this.itemMarket, ENotifyItemMarketInfoType.BUY, this.count));
        World.getInstance().broadcastWorldPacket(new SMListItemHeaderAtItemMarket(Collections.singletonList(this.masterItemMarket), EPacketTaskType.Update));
        MailService.getInstance().sendMail(this.itemMarket.getAccountId(), 0L, "{3183609639|3119282326}", "{3183609639|990597375}", "{3183609639|199595621|643213191|ItemInfo:Name_" + this.itemMarket.getItemId() + "|2207029135|" + this.count + "}");
    }

    @Override
    public boolean canAct() {
        if (!this.srcStorageType.isInventory() && !this.srcStorageType.isWarehouse()) {
            return false;
        }
        this.itemMarket = ItemMarketService.getInstance().getItem(this.itemMarketObjectId);
        if (this.itemMarket == null) {
            this.player.sendPacket(new SMNotifyItemMarketInfo(ENotifyItemMarketInfoType.UPDATE, this.itemMarketObjectId, this.itemId, this.enchant));
            this.player.sendPacket(new SMNak(EStringTable.eErrNoAlreadySoldOutItemAtItemMarket, CMBuyItemAtItemMarket.class));
            return false;
        }
        if (this.itemMarket.getAccountId() == this.player.getAccountId()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemExchangeCantExchangeWithMe, CMBuyItemAtItemMarket.class));
            return false;
        }
        this.revenue = this.itemMarket.getItemPrice() * this.count;
        this.masterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.itemMarket.getItemId(), this.itemMarket.getEnchantLevel());
        this.addItem(new Item(this.itemMarket.getItem(), this.count));
        this.decreaseItem(0, this.revenue, this.srcStorageType);
        return super.canAct() && this.masterItemMarket.buyItem(this.player, this.itemMarket, this.count);
    }
}
