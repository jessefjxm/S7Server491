// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMRepairItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMRepairItem;
import com.bdoemu.core.network.sendable.SMVaryEquipItemEndurance;
import com.bdoemu.gameserver.dataholders.AlchemyChargeData;
import com.bdoemu.gameserver.dataholders.RepairMaxEnduranceData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EAlchemyStoneType;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.AlchemyChargeT;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.items.templates.RepairMaxEnduranceT;
import com.bdoemu.gameserver.model.items.templates.RepairResourceMaxEnduranceT;

public class RepairItemEvent extends AItemEvent {
    private int slotIndex;
    private int decreaseSlotId;
    private int artisanSlotId;
    private byte repairType;
    private EItemStorageLocation storageType;
    private EItemStorageLocation decreaseStorageType;
    private EItemStorageLocation moneyStorageType;
    private EItemStorageLocation artisansStorageType;
    private Item item;
    private Item decreaseItem;
    private int endurance;
    private Integer repairPrice;
    private int recoverEndurance;
    private ItemPack itemPack;
    private long decreaseMoneyCount;
    private long count;
    private int artisansPercentage;

    public RepairItemEvent(final Player player, final int slotIndex, final int decreaseSlotId, final int artisanSlotId, final EItemStorageLocation storageType, final EItemStorageLocation decreaseStorageType, final EItemStorageLocation moneyStorageType, final EItemStorageLocation artisansStorageType, final byte repairType, final long count, final int regionId) {
        super(player, player, player, EStringTable.eErrNoPopItemForRepair, EStringTable.eErrNoPopItemForRepair, regionId);
        this.decreaseMoneyCount = 0L;
        this.slotIndex = slotIndex;
        this.artisanSlotId = artisanSlotId;
        this.storageType = storageType;
        this.decreaseStorageType = decreaseStorageType;
        this.decreaseSlotId = decreaseSlotId;
        this.moneyStorageType = moneyStorageType;
        this.artisansStorageType = artisansStorageType;
        this.repairType = repairType;
        this.count = count;
    }

    @Override
    public void onEvent() {
        if (this.artisansPercentage > 0) {
            this.recoverEndurance += this.recoverEndurance * this.artisansPercentage / 1000000;
        }
        super.onEvent();
        switch (this.repairType) {
            case 0: {
                this.item.setEndurance(this.item.getMaxEndurance());
                break;
            }
            case 1: {
                this.item.addMaxEndurance(this.recoverEndurance);
                break;
            }
            case 2: {
                this.item.addEndurance(this.recoverEndurance);
                break;
            }
        }
        this.player.sendPacket(new SMRepairItem(this.player.getGameObjectId(), this.storageType, this.slotIndex, this.item.getEndurance(), this.item.getMaxEndurance()));
        if (this.itemPack.getLocationType().isEquipments()) {
            this.player.sendBroadcastItSelfPacket(new SMVaryEquipItemEndurance(this.player.getGameObjectId(), this.slotIndex, this.endurance));
        }
    }

    @Override
    public boolean canAct() {
        this.itemPack = this.playerBag.getItemPack(this.storageType);
        if (this.itemPack == null || this.slotIndex > this.itemPack.getExpandSize()) {
            return false;
        }
        if (this.slotIndex < this.itemPack.getDefaultSlotIndex()) {
            return false;
        }
        this.item = this.itemPack.getItem(this.slotIndex);
        if (this.item == null) {
            return false;
        }
        if (this.artisansStorageType.isPlayerInventories() && this.artisanSlotId > 0) {
            final ItemPack artisansItemPack = this.playerBag.getItemPack(this.artisansStorageType);
            final Item artisansItem = artisansItemPack.getItem(this.artisanSlotId);
            if (artisansItem != null) {
                final EContentsEventType contentsEventType = artisansItem.getTemplate().getContentsEventType();
                if (contentsEventType == null || !contentsEventType.isHelpEndurance()) {
                    return false;
                }
                this.decreaseItem(this.artisanSlotId, 1L, this.artisansStorageType);
                this.artisansPercentage = artisansItem.getTemplate().getContentsEventParam1();
            }
        }
        switch (this.repairType) {
            case 0: {
                final ItemPack decreaseItemPack = this.playerBag.getItemPack(this.moneyStorageType, 0, this.regionId);
                this.decreaseItem = decreaseItemPack.getItem(0);
                if (this.decreaseItem == null || (!this.moneyStorageType.isInventory() && !this.moneyStorageType.isWarehouse())) {
                    return false;
                }
                this.repairPrice = this.item.getTemplate().getRepairPrice();
                if (this.repairPrice == null) {
                    return false;
                }
                this.endurance = this.item.getMaxEndurance() - this.item.getEndurance();
                if (this.endurance <= 0) {
                    return false;
                }
                this.decreaseMoneyCount = this.repairPrice / this.item.getTemplate().getEndurance() * this.endurance;
                break;
            }
            case 1: {
                final ItemPack decreaseItemPack = this.playerBag.getItemPack(this.decreaseStorageType);
                if (decreaseItemPack == null || !this.decreaseStorageType.isInventory()) {
                    return false;
                }
                this.decreaseItem = decreaseItemPack.getItem(this.decreaseSlotId);
                if (this.decreaseItem == null) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoInventoryInvalidSlotNo, CMRepairItem.class));
                    return false;
                }
                final ItemEnchantT itemEnchantT = this.item.getEnchantTemplate();
                if (itemEnchantT == null) {
                    return false;
                }
                if (this.decreaseItem.getItemId() == 44195) {
                    switch (this.item.getTemplate().getGradeType()) {
                        case white: {
                            this.recoverEndurance = 10;
                            break;
                        }
                        case green: {
                            this.recoverEndurance = 5;
                            break;
                        }
                        case blue: {
                            this.recoverEndurance = 2;
                            break;
                        }
                        case yellow: {
                            this.recoverEndurance = 1;
                            break;
                        }
                    }

                    this.decreaseItem(this.decreaseSlotId, 1L, decreaseItemPack.getLocationType());
                    break;
                }
                if (this.item.getItemId() == this.decreaseItem.getItemId()) {
                    this.recoverEndurance = itemEnchantT.getRecoverMaxEndurance();
                    if (this.recoverEndurance <= 0) {
                        return false;
                    }
                    this.decreaseItem(this.decreaseSlotId, 1L, decreaseItemPack.getLocationType());
                } else {
                    final RepairMaxEnduranceT repairMaxEnduranceT = RepairMaxEnduranceData.getInstance().getTemplate(this.item.getItemId(), 0); // enchant level not used tho
                    if (repairMaxEnduranceT == null) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoLuaCantGetTable, CMRepairItem.class));
                        return false;
                    }
                    final RepairResourceMaxEnduranceT repairResourceMaxEnduranceT = repairMaxEnduranceT.getTemplate(this.decreaseItem.getItemId());
                    if (repairResourceMaxEnduranceT == null) {
                        return false;
                    }
                    this.decreaseMoneyCount = repairResourceMaxEnduranceT.getNeedMoney();
                    this.recoverEndurance = repairResourceMaxEnduranceT.getRepairCount();
                    this.decreaseItem(this.decreaseSlotId, 1L, decreaseItemPack.getLocationType());
                }
                break;
            }
            case 2: {
                final EContentsEventType contentsEventType = this.item.getTemplate().getContentsEventType();
                if (!contentsEventType.isStone() || this.count <= 0L) {
                    return false;
                }
                final ItemPack decreaseItemPack = this.playerBag.getItemPack(this.decreaseStorageType);
                if (decreaseItemPack == null || !this.decreaseStorageType.isPlayerInventories()) {
                    return false;
                }
                this.decreaseItem = decreaseItemPack.getItem(this.decreaseSlotId);
                if (this.decreaseItem == null) {
                    return false;
                }
                final AlchemyChargeT alchemyChargeT = AlchemyChargeData.getInstance().getTemplate(this.decreaseItem.getItemId());
                if (alchemyChargeT == null || alchemyChargeT.getStoneType() != EAlchemyStoneType.valueOf(this.item.getTemplate().getContentsEventParam1())) {
                    return false;
                }
                this.recoverEndurance = (int) (alchemyChargeT.getChargePoint() * this.count);
                this.decreaseItem(this.decreaseSlotId, this.count, decreaseItemPack.getLocationType());
                break;
            }
            default: {
                return false;
            }
        }
        if (this.decreaseMoneyCount > 0L) {
            this.decreaseItem(0, this.decreaseMoneyCount, this.moneyStorageType);
        }
        return super.canAct();
    }
}
