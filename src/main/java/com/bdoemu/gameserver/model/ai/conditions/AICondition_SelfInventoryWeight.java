package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;

/**
 * @author H1X4
 */
public class AICondition_SelfInventoryWeight extends AICondition {
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_SelfInventoryWeight(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "");
        _value = element.getInt("@value", 0);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature owner = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        int weight = (100 * owner.getGameStats().getWeight().getIntValue()) / owner.getGameStats().getWeight().getIntMaxValue();
        switch(_cmp) {
            case ">": return weight > _value;
            case "<": return weight < _value;
            case "==": return weight == _value;
            case "!=": return weight != _value;
            case ">=": return weight >= _value;
            case "<=": return weight <= _value;
            case "=>": return weight >= _value;
            case "=<": return weight <= _value;
            default: return true;
        }
    }
}
