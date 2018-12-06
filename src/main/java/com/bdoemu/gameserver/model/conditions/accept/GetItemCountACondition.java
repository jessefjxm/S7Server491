package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;

public class GetItemCountACondition implements IAcceptConditionHandler {
    private int itemId;
    private int enchantLevel;
    private int value;
    private String operator;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.itemId = Integer.parseInt(values[0].trim());
        this.enchantLevel = Integer.parseInt(values[1].trim());
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final Item item = player.getInventory().getItemById(this.itemId);
        long count = 0L;
        if (item != null) {
            count = item.getCount();
            if (item.getEnchantLevel() < this.enchantLevel) {
                return false;
            }
        }
        boolean result = false;
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                result = (count < this.value);
                break;
            }
            case ">": {
                result = (count > this.value);
                break;
            }
            case "=": {
                result = (count == this.value);
                break;
            }
        }
        return this.hasExclamation != result;
    }
}
