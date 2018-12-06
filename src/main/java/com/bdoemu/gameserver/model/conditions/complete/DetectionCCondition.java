package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class DetectionCCondition implements ICompleteConditionHandler {
    private int creatureId;

    @Override
    public void load(final String... function) {
        this.creatureId = Integer.parseInt(function[0]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.creatureId) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.detection;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.creatureId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
