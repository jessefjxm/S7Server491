package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class GatherCCondition implements ICompleteConditionHandler {
    private int itemId;
    private int enchantLevel;
    private int count;

    @Override
    public void load(final String... function) {
        this.itemId = Integer.parseInt(function[0]);
        this.enchantLevel = Integer.parseInt(function[1].trim());
        this.count = Integer.parseInt(function[2].trim());
    }

    @Override
    public int checkCondition(final Object... params) {
        return (((int) params[0]) == itemId && ((int) params[1]) == enchantLevel) ? ((Long) params[2]).intValue() : 0;
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.gatherItem;
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
