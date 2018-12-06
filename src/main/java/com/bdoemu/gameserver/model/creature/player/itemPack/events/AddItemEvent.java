// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AddItemEvent extends AItemEvent {
    private ItemTemplate template;
    private int enchantLevel;
    private long count;

    public AddItemEvent(final Player player, final int itemId, final int enchantLevel, final long count) {
        this(player, itemId, enchantLevel, count, EStringTable.NONE);
    }

    public AddItemEvent(final Player player, final ConcurrentLinkedQueue<Item> addTasks) {
        this(player, addTasks, EStringTable.NONE);
    }

    public AddItemEvent(final Player player, final int itemId, final int enchantLevel, final long count, final EStringTable addMessageId) {
        super(player, player, player, addMessageId, EStringTable.NONE, 0);
        this.template = ItemData.getInstance().getItemTemplate(itemId);
        this.enchantLevel = enchantLevel;
        this.count = count;
    }

    public AddItemEvent(final Player player, final ConcurrentLinkedQueue<Item> addTasks, final EStringTable addMessageId) {
        super(player, player, player, addMessageId, EStringTable.NONE, 0);
        this.addTasks = addTasks;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (this.addTasks == null && this.template != null) {
            if (this.enchantLevel > 0 && this.template.getEnchantTemplate(this.enchantLevel) == null) {
                return false;
            }
            if (this.count > 1L && !this.template.isStack()) {
                for (int i = 0; i < this.count; ++i) {
                    this.addItem(this.template, 1L, this.enchantLevel);
                }
            } else {
                this.addItem(this.template, this.count, this.enchantLevel);
            }
        }
        return super.canAct();
    }
}
