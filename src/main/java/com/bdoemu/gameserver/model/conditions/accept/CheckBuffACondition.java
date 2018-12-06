package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;

public class CheckBuffACondition implements IAcceptConditionHandler {
    private ModuleBuffType moduleType;
    private Integer type;
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        if (values.length == 1) {
            this.moduleType = ModuleBuffType.valueOf(Integer.parseInt(values[0].trim()));
        } else {
            this.moduleType = ModuleBuffType.valueOf(Integer.parseInt(values[0].trim()));
            this.type = Integer.parseInt(values[1].trim());
        }
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        return this.hasExclamation != player.getBuffList().hasBuff(this.moduleType, this.type);
    }
}
