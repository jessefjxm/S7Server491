package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;

/**
 * @author H1X4
 */
public class AICondition_Ridable extends AICondition {
    private int _type;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_Ridable(AIExecution executor, JsonObject element) {
        super(executor, element);

        String type = element.getString("@type", "");
        if (type.equals("self"))
            _type = 1;
        else if (type.equals("target"))
            _type = 2;
        else {
            _type = 0;
            System.out.println("Not implemented Ridable type: " + type);
        }
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        switch(_type) {
            case 1: return getAiExecutor().getAiHandler().getAiScript().getAiOwner().getTemplate().isVehicleDriverRidable();
            case 2:
                Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget();
                return target != null && target.getTemplate().isVehicleDriverRidable();
            default: return false;
        }
    }
}
