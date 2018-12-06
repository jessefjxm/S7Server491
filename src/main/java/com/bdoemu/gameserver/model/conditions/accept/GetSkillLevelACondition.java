package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.CreatureSkill;

public class GetSkillLevelACondition implements IAcceptConditionHandler {
    private int skillId;
    private int value;
    private String operator;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.skillId = Integer.parseInt(values[1].trim());
        this.value = Integer.parseInt(operatorValue.trim());
        this.operator = operator;
    }

    @Override
    public boolean checkCondition(final Player player) {
        int skillLevel = 0;
        final CreatureSkill skill = player.getSkillList().getSkill(this.skillId);
        if (skill != null) {
            skillLevel = skill.getSkillLevel();
        }
        final String operator = this.operator;
        switch (operator) {
            case "<": {
                return skillLevel < this.value;
            }
            case ">": {
                return skillLevel > this.value;
            }
            case "=": {
                return skillLevel == this.value;
            }
            default: {
                return false;
            }
        }
    }
}
