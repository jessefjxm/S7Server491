// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMAlchemyEvolve;
import com.bdoemu.gameserver.dataholders.AlchemyStoneChangeData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.AlchemyStoneChangeT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;

import java.util.List;

public class AlchemyEvolveItemEvent extends AItemEvent {
    private EItemStorageLocation stoneStorageLocation;
    private EItemStorageLocation storageLocation;
    private int stoneSlotIndex;
    private int slotIndex;
    private boolean isBreak;
    private int alchemyExp;
    private Item alchemyItem;

    public AlchemyEvolveItemEvent(final Player player, final EItemStorageLocation stoneStorageLocation, final EItemStorageLocation storageLocation, final int stoneSlotIndex, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.isBreak = false;
        this.stoneStorageLocation = stoneStorageLocation;
        this.storageLocation = storageLocation;
        this.stoneSlotIndex = stoneSlotIndex;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.isBreak) {
            this.alchemyItem.upgradeAlchemyStone(this.player, -(this.alchemyExp / 2));
        }
        this.player.sendPacket(new SMAlchemyEvolve());
    }

    @Override
    public boolean canAct() {
        if ((!this.storageLocation.isInventory() && !this.storageLocation.isCashInventory()) || (!this.stoneStorageLocation.isInventory() && !this.stoneStorageLocation.isCashInventory())) {
            return false;
        }
        this.alchemyItem = this.playerBag.getItemPack(this.stoneStorageLocation).getItem(this.stoneSlotIndex);
        if (this.alchemyItem == null) {
            return false;
        }
        int breakChance = 20000;
        this.alchemyExp = this.alchemyItem.getAlchemyStoneExp();
        if (this.alchemyExp < 800000) {
            return false;
        }
        final EContentsEventType contentsEventType = this.alchemyItem.getTemplate().getContentsEventType();
        if (!contentsEventType.isRubbing() && !contentsEventType.isStone()) {
            return false;
        }
        final AlchemyStoneChangeT alchemyStoneChangeT = AlchemyStoneChangeData.getInstance().getTemplate(this.alchemyItem.getItemId());
        if (alchemyStoneChangeT == null) {
            return false;
        }
        if (!ConditionService.checkCondition(alchemyStoneChangeT.getConditions(), this.player)) {
            return false;
        }
        final Item changeItem = this.playerBag.getItemPack(this.storageLocation).getItem(this.slotIndex);
        if (changeItem == null || alchemyStoneChangeT.getNeedItemId() != changeItem.getItemId()) {
            return false;
        }
        List<ItemSubGroupT> items;
        if (alchemyStoneChangeT.getDownRate() > 0 && Rnd.getChance(alchemyStoneChangeT.getDownGroup() / 10000.0)) {
            items = ItemMainGroupService.getItems(this.player, alchemyStoneChangeT.getDownGroup());
        } else {
            items = ItemMainGroupService.getItems(this.player, alchemyStoneChangeT.getMainGroup());
        }
        for (final ItemSubGroupT itemSubGroupT : items) {
            this.addItem(itemSubGroupT.getItemId(), Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount()), itemSubGroupT.getEnchantLevel());
        }
        this.decreaseItem(this.slotIndex, alchemyStoneChangeT.getNeedItemCount(), this.storageLocation);
        this.decreaseItem(this.stoneSlotIndex, 1L, this.stoneStorageLocation);
        breakChance += 200000;
        final int breakRate = alchemyStoneChangeT.getBreakRate();
        breakChance -= breakRate;
        if (breakRate != 0 && !Rnd.getChance(breakChance / 10000.0)) {
            this.isBreak = true;
        }
        return super.canAct();
    }
}
