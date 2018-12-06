package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_OriginPos extends AICondition {
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_OriginPos(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp","==");
        if (element.get("@value").isString()) {
            String value = element.getString("@value", "");
            if (value.startsWith("_"))
                _value = getAiExecutor().getAiHandler().getAiScript().getVariable(value);
            else
                _value = (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(value);
        }
    }

    @Override
    public boolean test() {
        Location currentPos = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getLocation();
        Location originPos = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getSpawnPlacement().getLocation();
        int distance = (int) MathUtils.getDistance(currentPos, originPos);

        switch(_cmp) {
            case ">": return distance > _value;
            case "<": return distance < _value;
            case "=": return distance == _value;
            case "==": return distance == _value;
            case "!=": return distance != _value;
            case ">=": return distance >= _value;
            case "<=": return distance <= _value;
            case "=>": return distance >= _value;
            case "=<": return distance <= _value;
            default: return true;
        }
    }
}
