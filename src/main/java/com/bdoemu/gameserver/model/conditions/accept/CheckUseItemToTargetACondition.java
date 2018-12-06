package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckUseItemToTargetACondition implements IAcceptConditionHandler {
    private int useItemTargetType;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.useItemTargetType = Integer.parseInt(conditionValue);
    }

    @Override
    public boolean checkCondition(final Player player) {
        return true;
    }
}
