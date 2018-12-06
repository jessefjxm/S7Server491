package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;

public class GetWeatherFactorACondition implements IAcceptConditionHandler {
    private EWeatherFactorType weatherFactorType;
    private int unk;
    private int value;
    private String operator;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.weatherFactorType = EWeatherFactorType.valueOf(values[0].trim());
        this.unk = Integer.parseInt(values[1].trim());
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final float weatherFactorValue = player.getRegion().getTemplate().getWeatherFactorValue(this.weatherFactorType);
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return weatherFactorValue < this.value;
            }
            case ">": {
                return weatherFactorValue > this.value;
            }
            case "=": {
                return weatherFactorValue == this.value;
            }
            default: {
                return false;
            }
        }
    }
}
