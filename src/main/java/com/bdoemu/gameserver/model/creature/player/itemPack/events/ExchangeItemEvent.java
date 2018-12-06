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

public class ExchangeItemEvent extends AItemEvent {
    private int receiveItemId;
    private int receiveEnchantLevel;
    private int giveItemId;
    private int giveEnchantLevel;
    private long receiveCount;
    private long giveCount;

    public ExchangeItemEvent(final Player player, final int giveItemId, final int giveEnchantLevel, final long giveCount, final int receiveItemId, final int receiveEnchantLevel, final long receiveCount) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.giveItemId = giveItemId;
        this.giveEnchantLevel = giveEnchantLevel;
        this.giveCount = giveCount;
        this.receiveItemId = receiveItemId;
        this.receiveEnchantLevel = receiveEnchantLevel;
        this.receiveCount = receiveCount;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(this.giveItemId);
        if (!template.isStack() && this.receiveCount > 1L) {
            return false;
        }
        final ItemPack pack = template.isCash() ? this.cashInventory : this.playerInventory;
        Integer slotIndex = null;
        for (final int index : pack.getItemMap().keySet()) {
            final Item item = pack.getItem(index);
            if (item.getItemId() == this.giveItemId && item.getEnchantLevel() == this.giveEnchantLevel) {
                if (item.getCount() < this.giveCount) {
                    continue;
                }
                slotIndex = index;
                break;
            }
        }
        if (slotIndex == null) {
            return false;
        }
        this.addItem(this.receiveItemId, this.receiveCount, this.receiveEnchantLevel);
        this.decreaseItem(slotIndex, this.giveCount, pack.getLocationType());
        return super.canAct();
    }
}
