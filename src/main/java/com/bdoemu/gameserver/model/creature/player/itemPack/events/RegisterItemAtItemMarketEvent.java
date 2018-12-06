package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.network.receivable.CMRegisterItemAtItemMarket;
import com.bdoemu.core.network.sendable.SMBroadcastRegisterItemAtItemMarket;
import com.bdoemu.core.network.sendable.SMListRegisterItemsAtItemMarket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.service.ShutdownService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collections;

public class RegisterItemAtItemMarketEvent extends AItemEvent {
    private EItemStorageLocation storageType;
    private int slotIndex;
    private int territoryKey;
    private long count;
    private long price;
    private long itemObjId;
    private Item item;
    private String paymentPassword;

    public RegisterItemAtItemMarketEvent(final Player player, final EItemStorageLocation storageType, final int slotIndex, final long count, final long price, final int territoryKey, final String paymentPassword, final long itemObjId, final int regionId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.itemObjId = itemObjId;
        this.storageType = storageType;
        this.slotIndex = slotIndex;
        this.count = count;
        this.price = price;
        this.territoryKey = territoryKey;
        this.paymentPassword = paymentPassword;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final ItemMarket itemMarket = ItemMarket.newItemMarket(this.player.getAccountId(), new Item(this.item, this.count, this.price), this.count, this.price, this.territoryKey, 0L);
        ItemMarketService.getInstance().putItem(itemMarket);
        this.player.sendPacket(new SMListRegisterItemsAtItemMarket(Collections.singletonList(itemMarket), itemMarket.getRegisteredDate(), (byte) 0));
        this.player.sendPacket(new SMNotifyItemMarketInfo(itemMarket, ENotifyItemMarketInfoType.REGISTER));
        final MasterItemMarket masterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.item.getItemId(), this.item.getEnchantLevel());
        int announceType = -1;
        if (itemMarket.getItemPrice() >= 3000000L) {
            announceType = (masterItemMarket.getItems().isEmpty() ? 2 : 0);
        } else if (masterItemMarket.getItems().isEmpty()) {
            announceType = 1;
        }
        if (announceType >= 0) {
            World.getInstance().broadcastWorldPacket(new SMBroadcastRegisterItemAtItemMarket(itemMarket, announceType));
        }
    }

    @Override
    public boolean canAct() {
        if (ShutdownService.getInstance().isShutdownInProgress()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoFailedUpdateCharacterInformation, CMRegisterItemAtItemMarket.class));
            return false;
        }
        if (!this.storageType.isPlayerInventories() && !this.storageType.isWarehouse()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoItemIsTooHeavyForPickup, CMRegisterItemAtItemMarket.class));
            return false;
        }
        if (this.slotIndex < 2) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoInventoryInvalidSlotNo, CMRegisterItemAtItemMarket.class));
            return false;
        }
        final ItemPack decreasePack = this.playerBag.getItemPack(this.storageType, 0, this.regionId);
        if (decreasePack == null) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoInventoryNotEnoughItem, CMRegisterItemAtItemMarket.class));
            return false;
        }
        if (this.storageType.isWarehouse()) {
            this.slotIndex = decreasePack.getItemIndex(this.itemObjId);
        }
        this.item = decreasePack.getItem(this.slotIndex);
        if (this.item == null || this.item.isVested()) {
            return false;
        }
        if (this.item.getTemplate().getNeedContribute() != null) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoItemIsntForItemMarket, CMRegisterItemAtItemMarket.class));
            return false;
        }
        if (this.item.getTemplate().isCash()) {
            final String currentPassword = this.player.getAccountData().getPaymentPin();
            if (this.paymentPassword.isEmpty() || currentPassword.isEmpty() || !this.paymentPassword.equals(currentPassword)) {
                player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoUserNotEqualSecondPassword, CMRegisterItemAtItemMarket.class));
                return false;
            }
        }
        final ItemTemplate template = this.item.getTemplate();
        if (this.count < 1L || this.count > template.getMaxRegisterCountForItemMarket()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoItemIsInvalidChangedCount, CMRegisterItemAtItemMarket.class));
            return false;
        }
        final int basePrice = template.getBasePriceForItemMarket();
        if (basePrice <= 0) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoItemIsInvalidChangedCount, CMRegisterItemAtItemMarket.class));
            return false;
        }
        final MasterItemMarket masterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.item.getItemId(), this.item.getEnchantLevel());
        if (this.price < masterItemMarket.getItemMinPrice() || this.price > masterItemMarket.getItemMaxPrice()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoPriceMinIsUnder, CMRegisterItemAtItemMarket.class));
            return false;
        }
        if (ItemMarketService.getInstance().getRegisteredItemSize(this.player.getAccountId()) >= ItemMarketConfig.REGISTER_COUNT_PER_USER) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoNoMoreInsertAuctionGoods, CMRegisterItemAtItemMarket.class));
            return false;
        }
        this.decreaseItem(0, ItemMarketConfig.REGISTER_FEE_PRICE, EItemStorageLocation.Inventory);
        this.decreaseItem(this.slotIndex, this.count, this.storageType);
        if (!super.canAct()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoTaxAmountIsInvalid, CMRegisterItemAtItemMarket.class));
            return false;
        }
        return true;
    }
}
