package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_OwnerInTheHouseAny extends AICondition {
    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_OwnerInTheHouseAny(AIExecution executor, JsonObject element) {
        super(executor, element);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature owner = getAiExecutor().getAiHandler().getAiScript().getAiOwner().getOwner();
        return owner != null && owner.isPlayer() && ((Player) owner).getHouseVisit().isInHouseAny();
    }
}
