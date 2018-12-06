package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckCreationIndexACondition implements IAcceptConditionHandler {
    private int result;
    private int module;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.result = Integer.parseInt(values[0]);
        this.module = Integer.parseInt(values[1]);
    }

    @Override
    public boolean checkCondition(final Player player) {
        return player.getCreationIndex() % this.module == this.result;
    }
}
