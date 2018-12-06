package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckSiegeACondition implements IAcceptConditionHandler {
    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        //
    }

    @Override
    public boolean checkCondition(final Player player) {
        // TODO: Need to check if we are in siege.
        // aka Node Wars.

        return false; //player.getLocation().getRegion().getTemplate().isVillageWarArea();
    }
}
