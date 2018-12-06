package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * An AI Condition, that is being used by the executor.
 * It must always be parsed with respect you know (:
 *
 * @author H1X4
 */
public abstract class AICondition {

    /**
     * A reference to AI AIExecution, which
     * currently owns this condition.
     */
    private AIExecution _aiExecutor;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element AI XML element that will be read from.
     */
    public AICondition(AIExecution executor, JsonObject element) {
        _aiExecutor = executor;
    }

    /**
     * This AI Executor is shared between all conditions
     *
     * @return AI Executor that owns this condition.
     */
    protected AIExecution getAiExecutor() {
        return _aiExecutor;
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    public abstract boolean test();
}