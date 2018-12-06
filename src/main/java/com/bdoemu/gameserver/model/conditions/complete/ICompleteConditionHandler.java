package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public interface ICompleteConditionHandler {
    void load(final String... p0);

    int checkCondition(final Object... p0);

    int getStepCount();

    EObserveType getObserveType();

    boolean checkStep(final Player p0);

    Object getKey();

    boolean canInteractForQuest(final Creature p0);
}
