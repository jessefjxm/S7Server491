package com.bdoemu.gameserver.model.ai.model;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.AIScript;
import com.eclipsesource.json.JsonObject;

/**
 * An AI state that represents AIScript.
 * This is where all the magic happens
 * in Black Desert Online.
 *
 * @author H1X4
 */
public class AIState extends AIHandler {
    private int         _callCycleTime;
    private int         _randomCycleTime;
    private int         _actionHash;
    private int         _exitStateHash;
    private int         _completeStateHash;

    /**
     * Constructor for an AI State.
     * Reads all AI XML nodes and parses them into Executors and Conditions.
     *
     * @param script AI Script that will own this state.
     * @param node AI XML node from XML DOM Reader.
     */
    public AIState(AIScript script, JsonObject node) {
        super(script, node);

        //_callCycleTime = node.getInt("@callcycletime", 0); // AI_Wait_CallCycle_Time
        //_randomCycleTime = node.getInt("@randomcycletime", 0);
        _actionHash = (int) HashUtil.generateHashA(node.getString("@action", ""));
        _exitStateHash = (int) HashUtil.generateHashA(node.getString("@exit", ""));
        _completeStateHash = (int) HashUtil.generateHashA(node.getString("@complete", ""));
    }

    /**
     * Updates the current AIState, allows to execute an AIExecutor
     * on each tick, rather than all at once, allowing it to think
     * before it is acting what it needs to do. If by any means
     * it is required to complete immediately, "CallCycleTime" will be
     * overwritten to complete immediately, thus all ticks will be
     * executed immediately within the updates, without the total call
     * time which specifies how long this action can run, before proceeding
     * to next one.
     *
     * @param deltaTime How long it took to execute, usually used to compensate tick loop.
     */
    @Override
    public void update(int deltaTime) {
        if (_completeStateHash != 0) {
            tick(deltaTime);

            if (!_aiExecutorIterator.hasNext())
                getAiScript().setState(_completeStateHash, 0.1f);
        } else {
            if (System.currentTimeMillis() >= getAiScript().getStateTime())
                getAiScript().setState(_exitStateHash, 0.1f);
            else
                tick(deltaTime);
        }
    }

    /**
     * This function is called whenever a state is being entered.
     * Sometimes states do not require time to execute all executors,
     * such as death state. It also may contain some functions that
     * are obliged to be started whenever the state has been entered.
     *
     * @apiNote This method may be used as an Override, but please note that
     *          this base method MUST be called.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        if (_callCycleTime != 0) {
            getAiScript().setStateTime(System.currentTimeMillis()
                    + _callCycleTime
                    + (_randomCycleTime != 0
                        ? Rnd.get(-_randomCycleTime, _randomCycleTime)
                        : 0
                )
            );
        } else
            getAiScript().setStateTime(System.currentTimeMillis());

        // TODO: <start></start>
    }

    /**
     * This function is called whenever a state is being exited.
     * It also may contain some functions that are obliged to be
     * started whenever the state has been exited.
     *
     * @apiNote This method may be used as an Override, but please note that
     *          this base method MUST be called.
     */
    @Override
    public void onExit() {
        super.onExit();

        // TODO: <end></end>
    }
}