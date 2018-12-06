package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class TradeCCondition implements ICompleteConditionHandler {
    private int itemId;
    private int minPrice;
    private int count;

    @Override
    public void load(final String... function) {
        this.itemId = Integer.parseInt(function[0].trim());
        if (function.length > 2) {
            this.minPrice = Integer.parseInt(function[1].trim());
            this.count = Integer.parseInt(function[2].trim());
        } else {
            this.minPrice = Integer.parseInt(function[1].trim());
            this.count = 1;
        }
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.itemId && (long) params[1] >= this.minPrice) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.trade;
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
