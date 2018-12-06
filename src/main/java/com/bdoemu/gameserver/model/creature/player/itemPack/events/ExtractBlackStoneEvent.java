// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMExtractBlackStone;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;

import java.util.HashMap;
import java.util.Map;

public class ExtractBlackStoneEvent extends AItemEvent {
    private EItemStorageLocation storageType;
    private int slotIndex;
    private Item item;

    public ExtractBlackStoneEvent(final Player player, final EItemStorageLocation storageType, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageType = storageType;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMExtractBlackStone());
    }

    @Override
    public boolean canAct() {
        if (!this.storageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageType);
        if (itemPack == null) {
            return false;
        }
        if (this.slotIndex < itemPack.getDefaultSlotIndex()) {
            return false;
        }
        this.item = itemPack.getItem(this.slotIndex);
        if (this.item == null || this.item.getEnchantLevel() <= 0) {
            return false;
        }
        final HashMap<Integer, Long> itemsToAdd = new HashMap<Integer, Long>();
        for (int i = 0; i < this.item.getEnchantLevel(); ++i) {
            final ItemEnchantT enchantTemplate = this.item.getTemplate().getEnchantTemplates().get(i);
            if (!itemsToAdd.containsKey(enchantTemplate.getReqEnchantItemId())) {
                itemsToAdd.put(enchantTemplate.getReqEnchantItemId(), enchantTemplate.getReqEnchantItemCount());
            } else {
                itemsToAdd.put(enchantTemplate.getReqEnchantItemId(), itemsToAdd.get(enchantTemplate.getReqEnchantItemId()) + enchantTemplate.getReqEnchantItemCount());
            }
        }
        for (final Map.Entry<Integer, Long> entry : itemsToAdd.entrySet()) {
            this.addItem(entry.getKey(), entry.getValue(), 0);
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageType);
        return super.canAct();
    }
}
