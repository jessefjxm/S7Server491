package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.eclipsesource.json.JsonObject;

/**
 * @author H1X4
 */
public class AICondition_SummonOwnerClassType extends AICondition {
    private int _classType;
    private String _cmp;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_SummonOwnerClassType(AIExecution executor, JsonObject element) {
        super(executor, element);

        _classType = element.getInt("@classtype", 0);
        _cmp = element.getString("@cmp", "==");
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature owner = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getOwner();
        if (owner != null && owner.isPlayer()) {
            Player playerOwner = (Player) owner;
            int classType = playerOwner.getClassType().ordinal();
            switch (_cmp) {
                case "==":
                    return classType == _classType;
                case "!=":
                    return classType != _classType;
                case ">":
                    return classType > _classType;
                case "<":
                    return classType < _classType;
                case ">=":
                    return classType >= _classType;
                case "<=":
                    return classType <= _classType;
                case "=>":
                    return classType >= _classType;
                case "=<":
                    return classType <= _classType;
                default:
                    return true;
            }
        }
        return owner != null;
    }
}
