// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMManufactureItem;
import com.bdoemu.gameserver.dataholders.ActionEXPData;
import com.bdoemu.gameserver.dataholders.KnowledgeLearningData;
import com.bdoemu.gameserver.dataholders.LifeActionEXPData;
import com.bdoemu.gameserver.dataholders.ManufactureData;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Knowledge.templates.KnowledgeLearningT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.model.creature.player.itemPack.AbstractAddItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.manufactures.ManufactureItem;
import com.bdoemu.gameserver.model.manufactures.templates.ManufactureT;

import java.util.ArrayList;
import java.util.List;

public class ManufactureItemEvent extends AItemEvent {
    private int[][] manufactures;
    private String manufactureName;

    public ManufactureItemEvent(final Player player, final String manufactureName, final int[][] manufactures) {
        super(player, player, player, EStringTable.eErrNoItemisCreatedForManufacture, EStringTable.eErrNoItemisCreatedForManufacture, 0);
        this.manufactures = manufactures;
        this.manufactureName = manufactureName;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (!this.addTasks.isEmpty()) {
            final Item addItem = this.addTasks.peek();
            LifeActionEXPData.getInstance().reward(this.player, addItem.getItemId(), ELifeExpType.Process);
            this.player.getObserveController().notifyObserver(EObserveType.gatherItem, addItem.getItemId(), addItem.getEnchantLevel(), addItem.getCount());
            final KnowledgeLearningT knowledgeLearningT = KnowledgeLearningData.getInstance().getTemplate(addItem.getItemId());
            if (knowledgeLearningT != null && Rnd.getChance(knowledgeLearningT.getSelectRate(), 10000)) {
                this.player.getMentalCardHandler().updateMentalCard(knowledgeLearningT.getKnowledgeIndex(), ECardGradeType.C);
            }
        }
        this.player.sendPacket(new SMManufactureItem(EStringTable.NONE));
        final ActionEXPT actionEXPT = ActionEXPData.getInstance().getExpTemplate(this.player, this.player.getLifeExperienceStorage().getLifeExperience(ELifeExpType.Process).getLevel());
        if (actionEXPT != null) {
            this.player.addExp(actionEXPT.getManufacture());
        }
    }

    @Override
    public boolean canAct() {
        if (Rnd.getChance(50)) {
            this.player.sendPacket(new SMManufactureItem(EStringTable.eErrNoManufactureTimeisNotValid));
            return false;
        }
        final List<Item> materials = new ArrayList<Item>();
        for (final int[] manufacture : this.manufactures) {
            final int slotIndex = manufacture[1];
            if (slotIndex == 255) {
                break;
            }
            final EItemStorageLocation storageLocation = EItemStorageLocation.Inventory;
            final ItemPack removeItemPack = this.playerBag.getItemPack(storageLocation);
            if (removeItemPack == null) {
                return false;
            }
            final Item item = removeItemPack.getItem(slotIndex);
            if (item == null) {
                this.player.sendPacket(new SMManufactureItem(EStringTable.eErrNoInventoryNotEnoughItem));
                return false;
            }
            if (materials.contains(item)) {
                return false;
            }
            materials.add(item);
        }
        if (materials.isEmpty()) {
            return false;
        }
        final ManufactureT manufactureT = ManufactureData.getInstance().getTemplate(this.manufactureName, materials);
        if (manufactureT == null) {
            return false;
        }
        if (!Rnd.getChance(manufactureT.getSuccessPercent(), 10000)) {
            this.player.sendPacket(new SMManufactureItem(EStringTable.eErrNoFailToManufactureItem));
            return false;
        }
        for (final int[] manufacture2 : this.manufactures) {
            final int slotIndex2 = manufacture2[1];
            if (slotIndex2 == 255) {
                break;
            }
            final AbstractAddItemPack removeItemPack2 = this.playerInventory;
            final Item item = removeItemPack2.getItem(slotIndex2);
            final ManufactureItem manufactureItem = manufactureT.getItems().get(item.getItemId());
            this.decreaseItem(slotIndex2, manufactureItem.getCount(), removeItemPack2.getLocationType());
        }
        final List<ItemSubGroupT> addItems = ItemMainGroupService.getItems(this.player, manufactureT.getResultDropGroup());
        for (final ItemSubGroupT addItem : addItems) {
            this.addItem(addItem.getItemId(), Rnd.get(addItem.getMinCount(), addItem.getMaxCount()), addItem.getEnchantLevel());
        }
        if (!super.canAct()) {
            this.player.sendPacket(new SMManufactureItem(EStringTable.eErrNoInventoryNotEnoughItem));
            return false;
        }
        return this.addTasks != null;
    }
}
