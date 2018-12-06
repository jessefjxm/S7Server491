package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class IsTitleCondition implements IAcceptConditionHandler {
    private int titleId;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.titleId = Integer.parseInt(conditionValue);
    }

    @Override
    public boolean checkCondition(final Player player) {
        return player.getTitleHandler().getTitleId() == this.titleId;
    }
}
