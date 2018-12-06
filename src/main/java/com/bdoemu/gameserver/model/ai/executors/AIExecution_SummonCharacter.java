package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.eclipsesource.json.JsonObject;

public class AIExecution_SummonCharacter extends AIExecution {
    // CharacterKey			- int32
    // ActionIndex			- int32
    // OffSetX				- int32
    // OffSetY				- int32
    // OffSetZ				- int32
    // RandomIntervalX		- int16
    // RandomIntervalZ		- int16
    // RandomNumberRangeX	- int16
    // RandomNumberRangeZ	- int16
    // MinSummonCount		- int16
    // MaxSummonCount		- int16
    // isChild				- boolean
    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_SummonCharacter(AIHandler handler, JsonObject element) {
        super(handler, element);
    }

    @Override
    public void execute(int deltaTime) {

    }
}