package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CheckLevelUpCCondition implements ICompleteConditionHandler {
    private int levelUp;

    @Override
    public void load(final String... function) {
        this.levelUp = Integer.parseInt(function[0]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] >= this.levelUp) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.levelUp;
    }

    @Override
    public boolean checkStep(final Player player) {
        return player.getLevel() >= this.levelUp;
    }

    @Override
    public Object getKey() {
        return this.levelUp;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
