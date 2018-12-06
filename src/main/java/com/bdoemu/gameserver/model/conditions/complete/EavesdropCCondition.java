package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.utils.MathUtils;

public class EavesdropCCondition implements ICompleteConditionHandler {
    private double x;
    private double y;
    private double z;
    private int range;

    @Override
    public void load(final String... function) {
        this.x = Double.parseDouble(function[0]);
        this.z = Double.parseDouble(function[1]);
        this.y = Double.parseDouble(function[2]);
        this.range = Integer.parseInt(function[3].trim());
    }

    @Override
    public int checkCondition(final Object... params) {
        return MathUtils.isInRange(this.x, this.y, this.z, (double) params[0], (double) params[1], (double) params[2], this.range) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.eavesdrop;
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
