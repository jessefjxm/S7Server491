package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.eclipsesource.json.JsonObject;

public class AIExecution_Teleport extends AIExecution {
    // dest		- string, compute, default: random
    // waypoint	- string, compute [INSTANCE_CODE]
    // OffsetX	- int16
    // OffsetY	- int16
    // OffsetZ	- int16
    // min		- int16
    // max		- int16
    // minY		- int16
    // maxY		- int16
    // randomtype - enum[spawn, current_position, owner, target]
    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_Teleport(AIHandler handler, JsonObject element) {
        super(handler, element);
    }

    @Override
    public void execute(int deltaTime) {

    }
}