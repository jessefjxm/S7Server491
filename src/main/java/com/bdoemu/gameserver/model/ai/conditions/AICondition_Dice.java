package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author H1X4
 */
public class AICondition_Dice extends AICondition {
    private String _cmp;
    private String _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_Dice(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        if (element.get("@value").isString())
            _value = element.getString("@value", "");
        else
            _value = element.getInt("@value", 0) + "";
    }

    @Override
    public boolean test() {
        Integer value = _value.startsWith("_")
                ? getAiExecutor().getAiHandler().getAiScript().getVariable(_value)
                : (StringUtils.isNumeric(_value)
                ? (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(_value)
                : Integer.parseInt(_value));
        int dice = Rnd.get(value);
        switch(_cmp) {
            case "==": return dice == value;
            case "!=": return dice != value;
            case "=": return dice == value;
            case ">": return dice > value;
            case "<": return dice < value;
            case ">=": return dice >= value;
            case "<=": return dice <= value;
            case "=>": return dice >= value;
            case "=<": return dice <= value;
            default: break;
        }
        return false;
    }
}
