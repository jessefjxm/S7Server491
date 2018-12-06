package com.bdoemu.gameserver.model.ai;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.gameserver.model.ai.conditions.AICondition;
import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.bdoemu.gameserver.model.ai.services.AIScriptLoader;
import com.bdoemu.gameserver.model.ai.services.AIServiceProvider;
import com.bdoemu.gameserver.model.ai.model.AIState;
import com.bdoemu.gameserver.model.creature.Creature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a script handler that is compatible with
 * Black Desert Online *.ai files. Combo files are not supported and
 * I do not believe they need to be used by this specific AI Script instance.
 *
 * @author H1X4
 * @version Early Alpha
 */
public class AIScript implements Serializable {

    /**
     * Creature that owns current AI Script
     */
    private Creature _aiOwner;

    /**
     * Current AI State timer, last enter to a state.
     * Allows to detect bottleneck and some other
     * undefined issues with the state script parser.
     */
    private long _stateTime;

    /**
     * Currently executing state.
     */
    private int _currentStateHandler;

    /**
     * All current states, mapped into a BDO Hash.
     */
    private HashMap<Integer, AIHandler> _states;

    /**
     * Variables from AI action chart. They are the main ones
     * that control this script, instead of any data, etc.
     */
    private Map<Long, Integer> _aiVariables;

    /**
     * Script variables, only the script decides what they are.
     */
    private Map<String, Integer> _variables;

    /**
     * If script is not ready to be running,
     * there is no reason for it to be running.
     */
    private boolean _isRunning;

    /**
     * AI Script initializer, assigns an owner and
     * reads the specific script that the owner has.
     * Hopefully, it will not require 10 GB of RAM to use parse.
     *
     * @param owner AI Script assigned owner.
     */
    public AIScript(Creature owner){
        _aiOwner = owner;
        _states = new HashMap<>();
        _variables = new HashMap<>();
        _aiVariables = _aiOwner.getActionStorage().getAiParameters();
        _currentStateHandler = (int) HashUtil.generateHashA("InitialState");
        _isRunning = false;
        AIScriptLoader.getInstance().loadScript(this);
        _isRunning = true;
    }

    /**
     * Each time AIServiceProvider calls a tread loop, this function
     * is being executed to update the current state of
     * this AI script with a delta time as a compensation
     * for delaying the task.
     *
     * @see AIServiceProvider
     */
    public void update() {
        if (_isRunning) {
            //System.out.println("update()#" + _aiOwner.getName() + "#" + AIServiceProvider.getInstance().getDeltaTime());
            _states.get(_currentStateHandler).update(AIServiceProvider.getInstance().getDeltaTime());
        }
    }

    /**
     * Returns AI Script owner instance to be used within the
     * script itself and conditions.
     *
     * @return AI Script Owner
     */
    public Creature getAiOwner() {
        return _aiOwner;
    }

    /**
     * Returns state time, when was the state being executed the last time
     * in order to compensate for "CallCycleTime" and "RandomCallCycleTime".
     *
     * @return Start of State Time in milliseconds.
     * @see AIExecution
     * @see AICondition
     */
    public long getStateTime() {
        return _stateTime;
    }

    /**
     * Sets a new state time. This function is only used by the sate, in order
     * to delay or remove the delay for "CallCycleTime" using "RandomCallCycleTime".
     *
     * @param newStateTime The new state time produced by AIState.
     * @see AIState
     */
    public void setStateTime(long newStateTime) {
        _stateTime = newStateTime;
    }

    /**
     * Whenever a state is being switched, it will call this function to change the state
     * and every time the state is changed, the state timer resets.
     *
     * @param newState New state hash to set to.
     * @param blendTime Blend time between states
     */
    public void setState(int newState, float blendTime) {
        _states.get(_currentStateHandler).onExit();
        _currentStateHandler = newState;
        _states.get(_currentStateHandler).onEnter();
    }

    /**
     * Returns a current ai State that can be used
     * by any condition or executor in order to
     * compare states, only used by <State cmp="" value=""/>
     *
     * @return Current state handler hash
     */
    public int getCurrentStateHandler() {
        return _currentStateHandler;
    }

    /**
     * Executes AI handler using the hash.
     *
     * @param hash Hash of the handler.
     */
    public void dispatchHandler(int hash) {

        // TODO: This does NOT execute in a state, but executes immediately.
        // fact is that once u switch state, the condition AICondition_STate does not work then.
        // so maybe you need to pass "previous state" integer and use this afterwards.
        //setState(hash, 0.1f);
    }

    /**
     * Returns an AI variable that is requested
     * by an condition or executor. Each AI script
     * has custom AI variable that controls a
     * condition or an executor.
     *
     * @param aiVariable Variable to retrieve
     * @return Variable value
     */
    public long getAiVariable(String aiVariable) {
        return _aiVariables.get(aiVariable);
    }

    /**
     * Sets a SCRIPT variable that is used for a condition.
     *
     * @param variable Script Variable
     * @param value New script variable value
     */
    public void setVariable(String variable, Integer value) {
        _variables.put(variable, value);
    }

    /**
     * Returns a SCRIPT variable that is used for condition
     * engine or passing between states the values.
     *
     * @param variable Variable to retrieve
     * @return Result of variable
     */
    public int getVariable(String variable) {
        switch(variable) {
            case "_HP"          : return (getAiOwner().getGameStats().getHp().getIntValue() * 100) / getAiOwner().getGameStats().getHp().getIntMaxValue();
            case "_MyBodyHeight": return getAiOwner().getTemplate().getBodyHeight();
            case "CharIDValue"  : return getAiOwner().getTemplate().getCreatureId();
        }
        return _variables.get(variable);
    }

    /**
     * Appends a new handler into AI Script.
     *
     * @param stateHash Hash of the state name or action.
     * @param handler AI Handler that will be added.
     */
    public void addState(int stateHash, AIHandler handler) {
        _states.put(stateHash, handler);
    }
}