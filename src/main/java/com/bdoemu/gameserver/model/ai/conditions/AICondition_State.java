package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author H1X4
 */
public class AICondition_State extends AICondition {
    private String _cmp;
    private List<Integer> _states;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_State(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        String[] states = element.getString("@value", "").split("|");

        _states = new ArrayList<>();
        for (String state : states)
            _states.add((int) HashUtil.generateHashA(state));
    }

    @Override
    public boolean test() {
        for(Integer state : _states) {
            switch(_cmp) {
                case "==": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() == state;
                case "=": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() == state;
                case ">": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() > state;
                case "<": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() < state;
                case "!=": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() != state;
                case ">=": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() >= state;
                case "<=": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() <= state;
                case "=>": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() >= state;
                case "=<": return getAiExecutor().getAiHandler().getAiScript().getCurrentStateHandler() <= state;
                default: break;
            }
        }
        return false;
    }
}
