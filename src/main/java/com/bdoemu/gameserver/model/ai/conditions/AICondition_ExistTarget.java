package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;

/**
 * @author H1X4
 */
public class AICondition_ExistTarget extends AICondition {
    private boolean _inverse;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_ExistTarget(AIExecution executor, JsonObject element) {
        super(executor, element);
    }

    @Override
    public boolean test() {
        boolean target = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget() != null;
        return _inverse != target;
    }
}
