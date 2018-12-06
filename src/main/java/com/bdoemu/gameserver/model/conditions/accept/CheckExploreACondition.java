package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckExploreACondition implements IAcceptConditionHandler {
    private int wayPointId;
    private int unk;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.wayPointId = Integer.parseInt(values[0].trim());
        this.unk = Integer.parseInt(values[1].trim());
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        boolean result;
        if (this.unk == 1) {
            result = player.getExploration().contains(this.wayPointId);
        } else {
            result = !player.getExploration().contains(this.wayPointId);
        }
        return this.hasExclamation != result;
    }
}
