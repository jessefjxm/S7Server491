package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class NpcWorkerMakingCCondition implements ICompleteConditionHandler {
    private int itemId;
    private int count;

    @Override
    public void load(final String... function) {
        this.itemId = Integer.parseInt(function[0]);
        this.count = Integer.parseInt(function[1]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.itemId) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.npcworkermaking;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.itemId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
