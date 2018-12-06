package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class GetWpACondition implements IAcceptConditionHandler {
    private int value;
    private String operator;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final int wp = player.getCurrentWp();
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return wp < this.value;
            }
            case ">": {
                return wp > this.value;
            }
            case "=": {
                return wp == this.value;
            }
            default: {
                return false;
            }
        }
    }
}
