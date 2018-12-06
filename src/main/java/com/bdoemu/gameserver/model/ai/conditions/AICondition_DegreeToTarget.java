package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_DegreeToTarget extends AICondition {
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_DegreeToTarget(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        _value = element.getInt("@value", 0);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature actor = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget();

        int angleDiff = 0;
        if (target != null) {
            final double angleOfTarget = Math.toDegrees(Math.atan2(target.getLocation().getY() - actor.getLocation().getY(), target.getLocation().getX() - actor.getLocation().getX()));
            final double facingAngle = Math.toDegrees(Math.atan2(actor.getLocation().getSin(), actor.getLocation().getCos()));
            angleDiff = (int)((facingAngle - angleOfTarget + 180.0) % 360.0 - 180.0);
        }

        switch(_cmp) {
            case ">": return angleDiff > _value;
            case "<": return angleDiff < _value;
            case "=": return angleDiff == _value;
            case "==": return angleDiff == _value;
            case "!=": return angleDiff != _value;
            case ">=": return angleDiff >= _value;
            case "<=": return angleDiff <= _value;
            case "=>": return angleDiff >= _value;
            case "=<": return angleDiff <= _value;
            default: return true;
        }
    }
}
