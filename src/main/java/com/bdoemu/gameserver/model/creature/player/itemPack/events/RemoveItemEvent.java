// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class RemoveItemEvent extends AItemEvent {
    private int itemId;
    private int enchantLevel;
    private long count;

    public RemoveItemEvent(final Player player, final int itemId, final int enchantLevel, final long count) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(this.itemId);
        final ItemPack pack = template.isCash() ? this.cashInventory : this.playerInventory;
        Integer slotIndex = null;
        for (final int index : pack.getItemMap().keySet()) {
            final Item item = pack.getItem(index);
            if (item.getItemId() == this.itemId && item.getEnchantLevel() == this.enchantLevel) {
                if (item.getCount() < this.count) {
                    continue;
                }
                slotIndex = index;
                break;
            }
        }
        if (slotIndex == null) {
            return false;
        }
        this.decreaseItem(slotIndex, this.count, pack.getLocationType());
        return super.canAct();
    }
}
