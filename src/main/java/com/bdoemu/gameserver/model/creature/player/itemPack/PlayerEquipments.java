package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.mongodb.BasicDBObject;

public class PlayerEquipments extends EquipmentsBag {
    public PlayerEquipments(final Player player) {
        super(EItemStorageLocation.Equipments, new Stat(player, new BaseElement(31.0f)), player);
    }

    public PlayerEquipments(final BasicDBObject dbObject, final Playable playable) {
        super(EItemStorageLocation.Equipments, dbObject, playable);
    }

    public Item getItem(final EEquipSlot equipSlot) {
        return this.getItem(equipSlot.ordinal());
    }

    @Override
    public int getMaxExpandSize() {
        return this.expandStat.getBase().getIntValue();
    }
}
