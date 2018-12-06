package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class GetInventoryFreeCountACondition implements IAcceptConditionHandler {
    private int value;
    private String operator;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final int freeCount = player.getInventory().getExpandSize() - player.getInventory().getItemSize();
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return freeCount < this.value;
            }
            case ">": {
                return freeCount > this.value;
            }
            case "=": {
                return freeCount == this.value;
            }
            default: {
                return false;
            }
        }
    }
}
