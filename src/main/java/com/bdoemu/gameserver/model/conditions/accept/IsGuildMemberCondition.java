package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public class IsGuildMemberCondition implements IAcceptConditionHandler {
    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
    }

    @Override
    public boolean checkCondition(final Player player) {
        return player.hasGuild();
    }
}
