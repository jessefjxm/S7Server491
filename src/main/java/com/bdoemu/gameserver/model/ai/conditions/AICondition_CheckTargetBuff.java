package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_CheckTargetBuff extends AICondition {
    private int _buffIndex;
    //private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_CheckTargetBuff(AIExecution executor, JsonObject element) {
        super(executor, element);

        _buffIndex = element.getInt("@buffindex", 0);
        //_value = element.getInt("@value", 0);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature target = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        return target != null && target.getBuffList().hasBuff(ModuleBuffType.valueOf(_buffIndex));
    }
}
