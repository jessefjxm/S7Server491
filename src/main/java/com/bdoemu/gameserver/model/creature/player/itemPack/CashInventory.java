package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.core.configs.PlayerBagConfig;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.mongodb.BasicDBObject;

public class CashInventory extends AbstractAddItemPack {
    public CashInventory(final Player player) {
        super(EItemStorageLocation.CashInventory, new Stat(player, new BaseElement(PlayerBagConfig.CASH_INVENTORY_BASE_SIZE)), player);
    }

    public CashInventory(final BasicDBObject dbObject, final Player player) {
        super(EItemStorageLocation.CashInventory, dbObject, player);
    }

    @Override
    public int getMaxExpandSize() {
        return PlayerBagConfig.CASH_INVENTORY_MAX_SIZE;
    }
}
