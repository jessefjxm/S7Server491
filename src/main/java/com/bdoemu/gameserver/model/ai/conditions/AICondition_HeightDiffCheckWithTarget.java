package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_HeightDiffCheckWithTarget extends AICondition {
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_HeightDiffCheckWithTarget(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        if (element.get("@value").isString()) {
            String value = element.getString("@value", "");
            if (value.startsWith("_"))
                _value = getAiExecutor().getAiHandler().getAiScript().getVariable(value);
            else
                _value = (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(value);
        }
    }

    @Override
    public boolean test()
    {
        Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget();
        int heightDiff = (int) ((target != null) ? (-target.getLocation().getZ() - -getAiExecutor().getAiHandler().getAiScript().getAiOwner().getLocation().getZ()) : 0.0);
        switch(_cmp) {
            case "==": return heightDiff == _value;
            case "=": return heightDiff == _value;
            case ">": return heightDiff > _value;
            case "<": return heightDiff < _value;
            case "!=": return heightDiff != _value;
            case ">=": return heightDiff >= _value;
            case "<=": return heightDiff <= _value;
            case "=>": return heightDiff >= _value;
            case "=<": return heightDiff <= _value;
            default: break;
        }
        return false;
    }
}