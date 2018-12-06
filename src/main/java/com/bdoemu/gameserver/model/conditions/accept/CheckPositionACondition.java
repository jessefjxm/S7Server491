package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;

public class CheckPositionACondition implements IAcceptConditionHandler {
    private Location location;
    private int radius;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.location = new Location(Double.parseDouble(values[0]), Double.parseDouble(values[2]), Double.parseDouble(values[1]));
        this.radius = Integer.parseInt(values[3].trim());
    }

    @Override
    public boolean checkCondition(final Player player) {
        return MathUtils.isInRange(player.getLocation(), this.location, this.radius);
    }
}
