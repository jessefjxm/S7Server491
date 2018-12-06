package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_OBSOLOTED_IsProtectedRegion extends AICondition {
    private boolean _inverse;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_OBSOLOTED_IsProtectedRegion(AIExecution executor, JsonObject element) {
        super(executor, element);

        _inverse = element.getBoolean("@inverse", false);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget();
        return _inverse != (target != null && target.getRegion().getTemplate().isSafe());
    }
}
