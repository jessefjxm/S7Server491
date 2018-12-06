package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.core.configs.PlayerBagConfig;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;
import com.mongodb.BasicDBObject;

public class PlayerInventory extends AbstractAddItemPack {
    public PlayerInventory(final Player player) {
        super(EItemStorageLocation.Inventory, new Stat(player, new BaseElement(PlayerBagConfig.INVENTORY_BASE_SIZE)), player);
    }

    public PlayerInventory(final BasicDBObject dbObject, final Player player) {
        super(EItemStorageLocation.Inventory, dbObject, player);
    }

    @Override
    public int getMaxExpandSize() {
        return PlayerBagConfig.INVENTORY_MAX_SIZE;
    }

    public void expandBase(final int expandCount) {
        this.expandStat.increaseElement(this.expandStat.getBase(), (this.getBaseExpandSize() + expandCount > this.getMaxExpandSize()) ? ((float) (this.getMaxExpandSize() - this.getBaseExpandSize())) : ((float) expandCount));
    }

    public void addExpandElement(final ExpandElement element) {
        this.expandStat.addElement(element);
    }

    public boolean canDecreaseItem(final int index, final long count) {
        final Item item = this.getItem(index);
        return item != null && item.getCount() >= count;
    }
}
