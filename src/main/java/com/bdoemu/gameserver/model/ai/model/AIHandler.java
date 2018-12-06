package com.bdoemu.gameserver.model.ai.model;

import com.bdoemu.gameserver.model.ai.AIScript;
import com.bdoemu.gameserver.model.ai.executors.*;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AI Handler that is used to be called from actual game server code.
 * This AI might be also shared in the client.
 *
 * @author H1X4
 */
public class AIHandler {
    /**
     * Reference to AI Script itself, you never know, maybe you will need to use it.
     * If it is unused, then remove to save 4 bytes of memory of each instance.
     */
    private AIScript _aiScript;

    /**
     * Shared AI Executor between states. AIState inherits and combines it's own logic,
     * while AIHandler uses default executor logic, without any timers.
     */
    private List<AIExecution> _aiExecutors;

    /**
     * Shared AI Iterator between states. AIState inherits and combines it's own logic,
     * while AIHandler uses defaul executor iterator logic, without any timers.
     */
    protected Iterator<AIExecution> _aiExecutorIterator;

    /**
     * AI Handler constructor, must be inherited from all AIState classes.
     *
     * @param aiScript AIScript that currently owns this handler.
     * @param element Element from AI XML used to read executors.
     */
    public AIHandler(AIScript aiScript, JsonObject element) {
        _aiScript = aiScript;
        _aiExecutors = new ArrayList<>();

        for (JsonObject.Member member : element) {
            String name = member.getName();
            JsonValue object = member.getValue();

            if (name.startsWith("@"))
                continue;

            if (object.isObject() && !object.isArray()) {
                if (object.isObject() || !object.isArray())
                    parseExecutor(name, object.asObject());
                else {
                    for (JsonValue condObj : object.asArray())
                        parseExecutor(name, condObj.asObject());
                }
            }
        }
    }

    /**
     * Parses executor from JSON values.
     *
     * @param name Executor name
     * @param object Object of executor
     */
    private void parseExecutor(String name, JsonObject object) {
        AIExecution executor = null;
        switch(name) {
            case "start": break;
            case "end": break;
            case "changestate": executor = new AIExecution_ChangeState(this, object); break;
            case "change_state": executor = new AIExecution_ChangeState(this, object); break;
            case "findcharacter": executor = new AIExecution_FindCharacter(this, object); break;
            case "findcharactercount": executor = new AIExecution_FindCharacterCount(this, object); break;
            case "findtarget": executor = new AIExecution_FindTarget(this, object); break;
            case "findtargetinaggro": executor = new AIExecution_FindTargetInAggro(this, object); break;
            case "itemdelivery": executor = new AIExecution_ItemDeliver(this, object); break;
            case "makeparty": executor = new AIExecution_MakeParty(this, object); break;
            case "origin": break;
            case "variable": break;
            case "compute": executor = new AIExecution_ExpressionCompute(this, object); break;
            case "script": executor = new AIExecution_Script(this, object); break;
            case "send_command": break;
            case "send_handler": executor = new AIExecution_SendHandler(this, object); break;
            case "gettargetfromlastrider": executor = new AIExecution_GetTargetFromLastRider(this, object); break;
            case "gettargetfromsenderinfo": executor = new AIExecution_GetTargetFromSenderInfo(this, object); break;
            case "sender_info": break;
            case "geteventvalue": executor = new AIExecution_GetEventValue(this, object); break;
            case "set_aggro": executor = new AIExecution_SetAggroRate(this, object); break;
            case "aggro": executor = new AIExecution_Aggro(this, object); break;
            case "use_skill": executor = new AIExecution_UseSkill(this, object); break;
            case "use_petskill": executor = new AIExecution_UsePetSkill(this, object); break;
            case "drop_item": executor = new AIExecution_DropItem(this, object); break;
            case "teleport": executor = new AIExecution_Teleport(this, object); break;
            case "teleport_passenger": executor = new AIExecution_PassengerTeleport(this, object); break;
            case "changeaction": executor = new AIExecution_ChangeAction(this, object); break;
            case "changedropitem": executor = new AIExecution_ChangeDropItem(this, object); break;
            case "resettaminginfo": executor = new AIExecution_ResetTamingInfo(this, object); break;
            case "set_tradeprice": executor = new AIExecution_SetTradePrice(this, object); break;
            case "set_monstercollect": executor = new AIExecution_SetMonsterCollect(this, object); break;
            case "settimer": executor = new AIExecution_SetTimer(this, object); break;
            case "sethandletimer": executor = new AIExecution_SetHandleTimer(this, object); break;
            case "clearhandletimer": executor = new AIExecution_ClearHandleTimer(this, object); break;
            case "worldnotifier": executor = new AIExecution_WorldNotifier(this, object); break;
            case "instancenotifier": executor = new AIExecution_InstanceNotifier(this, object); break;
            case "logout": executor = new AIExecution_Logout(this, object); break;
            case "setpin": executor = new AIExecution_SetTimer(this, object); break;
            case "setformation": executor = new AIExecution_SetFormation(this, object); break;
            case "summoncharacter": executor = new AIExecution_SummonCharacter(this, object); break;
            case "summoncharacters": executor = new AIExecution_SummonCharacters(this, object); break;
            case "teleportwithowner": executor = new AIExecution_TeleportWithOwner(this, object); break;
            case "cleartamingvehicle": executor = new AIExecution_ClearTryTamingActor(this, object); break;
            default: System.out.println("Unsupported AI Executor: " + name); break;
        }
        if (executor != null)
            addAiExecutor(executor);
    }

    /**
     * Every time an update has been called, it will call this function to execute
     * more logic. As per default, handlers will contain one or two executors, that
     * are basically checkers for the state. AI Handler is used instead, to process
     * the data that we are currently using and push it to the player for the ui
     * review. Also, this is the main player AI, so it must be prioritized over
     * every single AI that is not a player, vehicle or servant.
     *
     * @param deltaTime How long it took to execute, usually used to compensate tick loop.
     * @apiNote As per default, handlers only have </changeState>, therefore we do not need
     *          to implement additional loop or additional ticks.
     * @see com.bdoemu.gameserver.model.ai.model.AIState
     */
    public void update(int deltaTime) {
        tick(deltaTime);
    }

    /**
     * Every time an update has been called, it will call this function to execute
     * a tick. Each tick pushes iterator until nothing is left. If an executor cannot
     * be executed, it will be ignored and waited for the next tick, because it does
     * NOT have enough time to execute another canExecute() in real-time.
     *
     * @param deltaTime The time elapsed since game loop tick start.
     * @see com.bdoemu.gameserver.model.ai.model.AIState
     */
    protected void tick(int deltaTime) {
        if(_aiExecutorIterator.hasNext()) {
            AIExecution executor = _aiExecutorIterator.next();

            if (executor.canExecute())
                executor.execute(deltaTime);
        }
    }

    /**
     * Fetches the AIScript controller, this controlled may be used by executors
     * and if needed by the handler itself in the future.
     *
     * @return AIScript that owns current handler.
     * @see AIScript
     * @see AIExecution
     */
    public AIScript getAiScript() {
        return _aiScript;
    }

    /**
     * Appends an AI executor to the handler. These executors are later used by the ticks
     * once they are appended.
     *
     * @param aiExecution AIExecution that will be owned by this AIHandler
     * @see AIHandler
     */
    public void addAiExecutor(AIExecution aiExecution) {
        _aiExecutors.add(aiExecution);
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
    public void onEnter() {
        _aiExecutorIterator = _aiExecutors.iterator();
    }

    /**
     * This function is called whenever a state is being exited.
     * It also may contain some functions that are obliged to be
     * started whenever the state has been exited.
     *
     * @apiNote This method may be used as an Override, but please note that
     *          this base method MUST be called.
     */
    public void onExit() {
        _aiExecutorIterator = null;
    }
}