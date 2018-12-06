package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;

public class CheckEquipItemACondition implements IAcceptConditionHandler {
    private int itemId;
    private int enchantLevel;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.itemId = Integer.parseInt(values[0].trim());
        this.enchantLevel = Integer.parseInt(values[1].trim());
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final Item itemInSlot = player.getEquipments().getItemById(this.itemId);
        return this.hasExclamation != (itemInSlot != null && itemInSlot.getEnchantLevel() == this.enchantLevel);
    }
}
