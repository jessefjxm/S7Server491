package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class ClearQuestCCondition implements ICompleteConditionHandler {
    private int group;
    private int id;

    @Override
    public void load(final String... function) {
        this.group = Integer.parseInt(function[0]);
        this.id = Integer.parseInt(function[1]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.group && (int) params[1] == this.id) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.questComplete;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.group;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
