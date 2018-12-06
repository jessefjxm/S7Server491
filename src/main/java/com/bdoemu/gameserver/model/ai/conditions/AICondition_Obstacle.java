package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_Obstacle extends AICondition {
    private boolean _inverse;
    private boolean _isPhysicalCheck;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_Obstacle(AIExecution executor, JsonObject element) {
        super(executor, element);

        _inverse = element.getBoolean("@inverse", false);
        _isPhysicalCheck = element.getBoolean("@isphysicalcheck", false);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        return true; // TODO: Needs proper geo-data implementation to implement this method.
    }
}
