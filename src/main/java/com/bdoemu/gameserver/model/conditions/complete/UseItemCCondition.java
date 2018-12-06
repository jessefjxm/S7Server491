package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class UseItemCCondition implements ICompleteConditionHandler {
    private int itemId;
    private int enchantLevel;

    @Override
    public void load(final String... function) {
        this.itemId = Integer.parseInt(function[0].trim());
        this.enchantLevel = Integer.parseInt(function[1].trim());
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.itemId && (int) params[1] == this.enchantLevel) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.useItem;
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
