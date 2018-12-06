package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.eclipsesource.json.JsonObject;

public class AIExecution_GetTargetFromSenderInfo extends AIExecution {
    // state 	 - string
    // blendTime - float
    // info		 - enum[sender, "target force"]
    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_GetTargetFromSenderInfo(AIHandler handler, JsonObject element) {
        super(handler, element);
    }

    @Override
    public void execute(int deltaTime) {

    }
}