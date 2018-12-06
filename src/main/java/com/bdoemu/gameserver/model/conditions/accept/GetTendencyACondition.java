package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class GetTendencyACondition implements IAcceptConditionHandler {
    private String operator;
    private int value;
    private int type;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.type = Integer.parseInt(conditionValue);
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final int tendencyValue = (this.type == 0) ? player.getTendency() : 0;
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return tendencyValue < this.value;
            }
            case ">": {
                return tendencyValue > this.value;
            }
            case "=": {
                return tendencyValue == this.value;
            }
            default: {
                return false;
            }
        }
    }
}
