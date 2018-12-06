package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class ExploreCCondtion implements ICompleteConditionHandler {
    private int waypointKey;

    @Override
    public void load(final String... function) {
        this.waypointKey = Integer.parseInt(function[0]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.waypointKey) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.explore;
    }

    @Override
    public boolean checkStep(final Player player) {
        return player.getExploration().contains(this.waypointKey);
    }

    @Override
    public Object getKey() {
        return this.waypointKey;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
