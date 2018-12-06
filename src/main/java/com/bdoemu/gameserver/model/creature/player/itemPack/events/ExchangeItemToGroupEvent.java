// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExchangeItemToGroupEvent extends AItemEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) ExchangeItemToGroupEvent.class);
    }

    private int itemId;
    private int enchantLevel;
    private int mainGroup;
    private long count;

    public ExchangeItemToGroupEvent(final Player player, final int itemId, final int enchantLevel, final long count, final int mainGroup) {
        super(player, player, player, EStringTable.eErrNoScriptReward, EStringTable.eErrNoScriptReward, 0);
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.count = count;
        this.mainGroup = mainGroup;
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
        final List<ItemSubGroupT> drops = ItemMainGroupService.getItems(this.player, this.mainGroup);
        if (drops.isEmpty()) {
            ExchangeItemToGroupEvent.log.warn(" ExchangeItemToGroupEvent isEmpty for itemId: {} ", (Object) this.itemId);
            return false;
        }
        final ItemSubGroupT itemSubGroup = drops.get(0);
        this.addItem(itemSubGroup.getItemId(), Rnd.get(itemSubGroup.getMinCount(), itemSubGroup.getMaxCount()), itemSubGroup.getEnchantLevel());
        this.decreaseItem(slotIndex, this.count, pack.getLocationType());
        return super.canAct();
    }
}
