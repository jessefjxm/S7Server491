package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.eclipsesource.json.JsonObject;

public class AIExecution_ChangeState extends AIExecution {
    private int _stateHash;
    private int _dice;
    private int _callCount;
    private float _blendTime;

    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_ChangeState(AIHandler handler, JsonObject element) {
        super(handler, element);

        _stateHash = (int) HashUtil.generateHashA(element.getString("@state", ""));
        //_dice = element.getInt("@dice", -1);
        _callCount = element.getInt("@callcount", -1);
        _blendTime = element.getFloat("@blendtime", 0.1f);

        //_dice = Rnd.get(_dice);
        _dice = -1;
    }

    @Override
    public void execute(int deltaTime) {
        if (_callCount != -1 && _dice != -1) {
            for (int i = 0; i < _callCount; ++i) {
                if (Rnd.getChance(_dice))
                    runChangeState();
            }
        }
        else
            runChangeState();
    }

    private void runChangeState() {
        getAiHandler().getAiScript().setState(_stateHash, _blendTime);
    }
}