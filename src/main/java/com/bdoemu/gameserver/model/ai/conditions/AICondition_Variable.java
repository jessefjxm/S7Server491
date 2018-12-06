package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_Variable extends AICondition {
    private String _name;
    private String _cmp;
    private String _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_Variable(AIExecution executor, JsonObject element) {
        super(executor, element);

        _name = element.getString("@name", "");
        _cmp = element.getString("@cmp", "==");
        if (element.get("@value").isString()) {
            _value = element.getString("@value", "");
        } else
            _value = "" + element.getInt("@value", 0);
    }

    @Override
    public boolean test() {
        int variableValue = getAiExecutor().getAiHandler().getAiScript().getVariable(_name);
        Integer value = _value.startsWith("_")
                ? getAiExecutor().getAiHandler().getAiScript().getVariable(_value)
                : (StringUtils.isNumeric(_value)
                ? (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(_value)
                : Integer.parseInt(_value));
        switch(_cmp) {
            case "==": return variableValue  == value;
            case "=" : return variableValue  == value;
            case ">" : return variableValue   > value;
            case "<" : return variableValue   < value;
            case "!=": return variableValue  != value;
            case ">=": return variableValue  >= value;
            case "<=": return variableValue  <= value;
            case "=>": return variableValue  >= value;
            case "=<": return variableValue  <= value;
            default: return false;
        }
    }
}
