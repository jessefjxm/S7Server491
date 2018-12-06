package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_TargetActionType extends AICondition {
    private String _cmp;
    private String _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_TargetActionType(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        _value = element.getString("@value", "");
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature owner = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        Creature target = owner.getAggroList().getTarget();

        if (target != null) {
            String actionType = target.getActionStorage().getActionType().name();
            switch(_cmp) {
                case "==": return actionType.equals(_value);
                case "!=": return !actionType.equals(_value);
                default: return true;
            }
        }
        return false;
    }
}
