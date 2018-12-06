package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CollectItemCCondition implements ICompleteConditionHandler {
    private int count;
    private int creatureId;
    private int chance;

    @Override
    public void load(final String... function) {
        this.count = Integer.parseInt(function[0]);
        this.creatureId = Integer.parseInt(function[1].trim());
        this.chance = Integer.parseInt(function[2].replaceAll("\u00a0", "").trim());
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.creatureId && Rnd.getChance(this.chance, 10000)) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.killMonster;
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
