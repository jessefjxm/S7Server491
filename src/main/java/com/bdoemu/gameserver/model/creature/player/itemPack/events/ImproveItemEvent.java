// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMImproveItem;
import com.bdoemu.gameserver.dataholders.ItemImprovementResultData;
import com.bdoemu.gameserver.dataholders.ItemImprovementSourceData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemImprovementResultT;
import com.bdoemu.gameserver.model.items.templates.ItemImprovementSourceT;

public class ImproveItemEvent implements IBagEvent {
    private Player player;
    private PlayerBag playerBag;
    private EItemStorageLocation itemStorageLocation;
    private EItemStorageLocation storageLocation;
    private int itemSlotIndex;
    private int slotIndex;
    private ItemImprovementSourceT itemImprovementSourceT;
    private ItemImprovementResultT itemImprovementResultT;
    private ItemPack stoneItemPack;
    private ItemPack itemPack;
    private Item stoneItem;
    private Item resultItem;

    public ImproveItemEvent(final Player player, final EItemStorageLocation itemStorageLocation, final int itemSlotIndex, final EItemStorageLocation storageLocation, final int slotIndex) {
        this.player = player;
        this.playerBag = player.getPlayerBag();
        this.itemStorageLocation = itemStorageLocation;
        this.itemSlotIndex = itemSlotIndex;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        this.stoneItem.addCount(-1L);
        if (this.stoneItem.getCount() <= 0L) {
            this.stoneItemPack.removeItem(this.slotIndex);
        }
        this.player.getGameStats().getWeight().addWeight(-this.stoneItem.getTemplate().getWeight());
        final Item removedItem = this.itemPack.removeItem(this.itemSlotIndex);
        if (removedItem != null) {
            this.player.getGameStats().getWeight().addWeight(-removedItem.getTemplate().getWeight());
        }
        this.itemPack.addItem(this.resultItem, this.itemSlotIndex);
        this.player.getGameStats().getWeight().addWeight(this.resultItem.getTemplate().getWeight());
        this.player.sendPacket(new SMImproveItem(this.itemStorageLocation, this.storageLocation, this.itemSlotIndex, this.slotIndex, this.resultItem));
    }

    @Override
    public boolean canAct() {
        if (!this.itemStorageLocation.isPlayerInventories() || !this.storageLocation.isPlayerInventories()) {
            return false;
        }
        this.stoneItemPack = this.playerBag.getItemPack(this.storageLocation);
        this.stoneItem = this.stoneItemPack.getItem(this.slotIndex);
        if (this.stoneItem == null) {
            return false;
        }
        this.itemImprovementSourceT = ItemImprovementSourceData.getInstance().getTemplate(this.stoneItem.getItemId());
        if (this.itemImprovementSourceT == null) {
            return false;
        }
        this.itemPack = this.playerBag.getItemPack(this.itemStorageLocation);
        this.resultItem = this.itemPack.getItem(this.itemSlotIndex);
        if (this.resultItem == null) {
            return false;
        }
        this.itemImprovementResultT = ItemImprovementResultData.getInstance().getTemplate(this.resultItem.getItemId());
        if (this.itemImprovementResultT == null) {
            return false;
        }
        int chance = 0;
        final int result = Rnd.get(0, 1000000);
        for (int index = 0; index < this.itemImprovementSourceT.getResults().length; ++index) {
            chance += this.itemImprovementSourceT.getResults()[index];
            if (result <= chance) {
                final Item item = new Item(this.itemImprovementResultT.getGroupItems()[index], this.resultItem.getCount(), this.resultItem.getEnchantLevel());
                item.setMaxEndurance(this.resultItem.getMaxEndurance());
                item.setEndurance(this.resultItem.getEndurance());
                item.setColorPalettes(this.resultItem.getColorPalettes());
                item.setJewels(this.resultItem.getJewels());
                this.resultItem = item;
                break;
            }
        }
        return true;
    }
}
