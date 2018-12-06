package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.eclipsesource.json.JsonObject;

public class AIExecution_FindCharacter extends AIExecution {
    // state  - string
    // target - int8
    //		combinewaveally			= 2
    //		combinewaveplayer		= 3
    //		monster					= 4
    //		player					= 7
    //		dword_141AD13AC			= 8
    //		enemy					= 9
    //		collect					= 11
    //		character				= 12
    // 		corpse					= 13
    //		vehiclehhorse			= 14
    //		tent					= 15
    //		installation			= 16
    //		inthehouse				= 17
    //		pkplayer				= 18
    //		ridingvehicle			= 19
    //		enemy_lordorkingtent	= 20
    //		enemy_barricade			= 21
    //		ally_lordorkingtent		= 23
    //		owner_player			= 24
    //		allyvehicle				= 28
    //		elitemonster			= 29
    //		lordorking_player		= 30
    // ally		- boolean
    // findType - int8 [enum=normal,nearest], default = normal
    // blendtime- float
    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_FindCharacter(AIHandler handler, JsonObject element) {
        super(handler, element);
    }

    @Override
    public void execute(int deltaTime) {

    }
}