package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class KillPlayerCCondition implements ICompleteConditionHandler {
    private int type;
    private int count;

    @Override
    public void load(final String... function) {
        this.type = Integer.parseInt(function[0]);
        this.count = Integer.parseInt(function[1]);
    }

    @Override
    public int checkCondition(final Object... params) {
        final Player player = (Player) params[0];
        return (this.type == 0 && player.getTendency() < 0) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.killPlayer;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return null;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
