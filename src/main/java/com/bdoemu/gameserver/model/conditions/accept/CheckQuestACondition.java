package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckQuestACondition implements IAcceptConditionHandler {
    private int groupId;
    private int stepIndex;
    private int questId;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.groupId = Integer.parseInt(values[0].trim());
        this.questId = Integer.parseInt(values[1].trim());
        this.stepIndex = Integer.parseInt(values[2].trim());
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        return this.hasExclamation != player.getPlayerQuestHandler().isCompleteStep(this.groupId, this.questId, this.stepIndex);
    }
}
