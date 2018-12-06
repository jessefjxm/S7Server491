package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckRegionTypeACondition implements IAcceptConditionHandler {
    private int regionType;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.regionType = Integer.parseInt(conditionValue);
    }

    @Override
    public boolean checkCondition(final Player player) {
        return this.regionType == 0 && player.getRegion().getTemplate().isDesert();
    }
}
