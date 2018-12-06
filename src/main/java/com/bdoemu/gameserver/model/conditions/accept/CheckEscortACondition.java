package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.HashSet;
import java.util.Set;

public class CheckEscortACondition implements IAcceptConditionHandler {
    public static final Set<Integer> escortList = new HashSet<>();
    private int creatureId;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.creatureId = Integer.parseInt(conditionValue);
        CheckEscortACondition.escortList.add(this.creatureId);
    }

    @Override
    public boolean checkCondition(final Player player) {
        final Creature escort = player.getEscort();
        return escort != null && escort.getCreatureId() == this.creatureId;
    }
}
