package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class GetIntimacyACondition implements IAcceptConditionHandler {
    private String operator;
    private int value;
    private int creatureId;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.creatureId = Integer.parseInt(conditionValue);
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return this.hasExclamation != player.getIntimacyHandler().getIntimacy(this.creatureId) < this.value;
            }
            case ">": {
                return this.hasExclamation != player.getIntimacyHandler().getIntimacy(this.creatureId) > this.value;
            }
            case "=": {
                return this.hasExclamation != (player.getIntimacyHandler().getIntimacy(this.creatureId) == this.value);
            }
            default: {
                return false;
            }
        }
    }
}
