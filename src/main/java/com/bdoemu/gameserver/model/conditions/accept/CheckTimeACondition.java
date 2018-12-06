package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.GameTimeService;

public class CheckTimeACondition implements IAcceptConditionHandler {
    private boolean hasExclamation;
    private int startHour;
    private int endHour;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.startHour = Integer.parseInt(values[0].trim());
        this.endHour = Integer.parseInt(values[1].trim());
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final int currentHour = GameTimeService.getInstance().getGameHour();
        final boolean result = currentHour >= this.startHour && currentHour <= this.endHour;
        return this.hasExclamation != result;
    }
}
