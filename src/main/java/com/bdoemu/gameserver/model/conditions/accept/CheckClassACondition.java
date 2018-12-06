package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckClassACondition implements IAcceptConditionHandler {
    private int classId;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.classId = Integer.parseInt(conditionValue);
    }

    @Override
    public boolean checkCondition(final Player player) {
        return player.getClassType().getId() == this.classId;
    }
}
