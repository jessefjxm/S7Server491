package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;

/**
 * @author H1X4
 */
public class AICondition_SelfHP extends AICondition {
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_SelfHP(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "=");
        _value = element.getInt("@value", 0);
    }

    @Override
    public boolean test() {
        Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        if (target != null) {
            int percentage = (target.getGameStats().getHp().getIntValue() * 100) / target.getGameStats().getHp().getIntMaxValue();

            switch (_cmp) {
                case "==":
                    return percentage == _value;
                case "!=":
                    return percentage != _value;
                case ">":
                    return percentage > _value;
                case "<":
                    return percentage < _value;
                case ">=":
                    return percentage >= _value;
                case "<=":
                    return percentage <= _value;
                case "=>":
                    return percentage >= _value;
                case "=<":
                    return percentage <= _value;
                default: break;
            }
        }
        return false;
    }
}
