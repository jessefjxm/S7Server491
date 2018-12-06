package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;

public class AIExecution_Aggro extends AIExecution {
    private String _execute;
    private boolean _targetClear;
    // execute="clear" | targetclear="false"

    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_Aggro(AIHandler handler, JsonObject element) {
        super(handler, element);

        _execute = element.getString("@execute", "");
        _targetClear = element.getBoolean("@targetclear", false);
    }

    @Override
    public void execute(int deltaTime) {
        if (_execute.equals("clear"))
            getAiHandler().getAiScript().getAiOwner().getAggroList().clear(true);

        if (_targetClear) {
            Creature target = getAiHandler().getAiScript().getAiOwner().getAggroList().getTarget();
            target.getAggroList().clear(false); // TODO: might lead to undefined stuff
        }
    }
}