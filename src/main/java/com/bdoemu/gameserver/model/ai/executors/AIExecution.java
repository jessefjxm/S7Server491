package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.bdoemu.gameserver.model.ai.conditions.*;
import com.bdoemu.gameserver.model.ai.model.AIState;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * An AI Executor that controls the states and allows
 * to be switched between them. Each executor has a
 * different set of conditions that are being used in
 * a script that controls AI.
 *
 * @author H1X4
 * @see AIState
 * @see AIHandler
 */
public abstract class AIExecution {

    /**
     * An AI Handler, that is a reference and
     * an owner of this AI Executor. It allows
     * for people to access parents without
     * allocating additional space per parent.
     */
    private AIHandler _handler;

    /**
     * Executor conditions are used to test if
     * an AI Handler can execute this
     * AI Executor and only then per tick it is
     * being executed each update.
     *
     * 1 Update = 1 Tick = 1 Executor checked
     *                          and executed.
     */
    private List<AICondition> _executorConditions;

    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution(AIHandler handler, JsonObject element) {
        _handler = handler;
        _executorConditions = new ArrayList<>();

        for (JsonObject.Member member : element) {
            String condition = member.getName();
            JsonValue value = member.getValue();

            if (condition.contains("@"))
                continue;

            if (value.isObject() && !value.isArray()) {
                if (value.isObject() || !value.isArray())
                    parseCondition(condition, value.asObject());
                else {
                    for (JsonValue condObj : value.asArray())
                        parseCondition(condition, condObj.asObject());
                }
            }
        }
    }

    private void parseCondition(String name, JsonObject object) {
        AICondition condition = null;
        switch(name) {
            case "distance"                 : condition = new AICondition_DistanceToTarget(this, object); break;
            case "distancetotarget"         : condition = new AICondition_DistanceToTarget(this, object); break;
            case "distancetargettoorigin"   : break;
            case "distancetochild"          : break;
            case "findraycastpathtoowner"   : break;
            case "findraycastpathtotarget"  : break;
            case "vanish"                   : condition = new AICondition_Vanish(this, object); break;
            case "existowner"               : break;
            case "obstacle"                 : condition = new AICondition_Obstacle(this, object); break;
            case "distancetotent"           : break;
            case "self_hp"                  : condition = new AICondition_SelfHP(this, object); break;
            case "self_has_part"            : break;
            case "self_part_hp"             : break;
            case "self_combinepoint"        : break;
            case "self_part_combinepoint"   : break;
            case "target_hp"                : condition = new AICondition_TargetHP(this, object); break;
            case "existtarget"              : condition = new AICondition_ExistTarget(this, object); break;
            case "existtent"                : break;
            case "existvehicle"             : break;
            case "existrider"               : condition = new AICondition_ExistRider(this, object); break;
            case "heightdiff"               : condition = new AICondition_HeightDiffCheckWithTarget(this, object); break;
            case "level"                    : break;
            case "variable"                 : condition = new AICondition_Variable(this, object); break;
            case "compute"                  : condition = new AICondition_Compute(this, object); break;
            case "dice"                     : condition = new AICondition_Dice(this, object); break;
            case "state"                    : condition = new AICondition_State(this, object); break;
            case "origin"                   : condition = new AICondition_OriginPos(this, object); break;
            case "target_name"              : break;
            case "target_actiontype"        : condition = new AICondition_TargetActionType(this, object); break;
            case "target_characterkey"      : condition = new AICondition_TargetCharacterKey(this, object); break;
            case "target_actionindex"       : break;
            case "target_dialogindex"       : break;
            case "is_night"                 : condition = new AICondition_IsNight(this, object); break;
            case "gametime"                 : condition = new AICondition_GameTime(this, object); break;
            case "action"                   : condition = new AICondition_Action(this, object); break;
            case "summoner_action"          : break;
            case "target_action"            : break;
            case "summoner_class"           : condition = new AICondition_SummonOwnerClassType(this, object); break;
            case "tendency"                 : break;
            case "guildtendency"            : break;
            case "angle"                    : condition = new AICondition_DegreeToTarget(this, object); break;
            case "issameguild"              : break;
            case "partycheck"               : condition = new AICondition_PartyCheck(this, object); break;
            case "lastdestination"          : break;
            case "ridable"                  : condition = new AICondition_Ridable(this, object); break;
            case "checktrigger"             : condition = new AICondition_Check_Trigger(this, object); break;
            case "checkbuff"                : condition = new AICondition_CheckTargetBuff(this, object); break;
            case "checkweather"             : break;
            case "self_inventoryweight"     : condition = new AICondition_SelfInventoryWeight(this, object); break;
            case "self_isdeliver"           : break;
            case "target_isdeliver"         : condition = new AICondition_TargetIsDeliver(this, object); break;
            case "target_isprotectedregion"         : condition = new AICondition_OBSOLOTED_IsProtectedRegion(this, object); break;
            case "target_iscrouchaction"            : break;
            case "target_collectorequipmentgrade"   : break;
            case "target_battleaimedactiontype"     : break;
            case "self_weapontype"          : break;
            case "target_weapontype"        : break;
            case "checkclienttype"          : break;
            case "ispet"                    : break;
            case "ownerinthehouse"          : break;
            case "self_inhouseany"          : break;
            case "owner_inhouseany"         : condition = new AICondition_OwnerInTheHouseAny(this, object); break;
            case "target_inhouseany"        : condition = new AICondition_TargetInTheHouseAny(this, object); break;
            case "target_isregionlordorking"        : break;
            case "target_isregionlordorkingguild"   : break;
            case "target_isregionsiegebeing"        : condition = new AICondition_TargetIsRegionSiegeBeing(this, object); break;
            case "self_isregionsiegebeing"          : break;
            case "self_isregionminorsiegebeing"     : break;
            case "target_isprocessquest"            : break;
            case "target_iscompletequest"           : break;
            case "petskillusable"                   : break;
            case "petactionusable"                  : break;
            case "isberserk"                        : break;
            case "isdarkspiritmonster"              : break;
            case "petindex"                         : break;
            case "monstergrade"                     : break;
            case "distancetoobjecteventkey" : break;
            case "buildingrate"             : break;
            case "harvestgrowingrate"       : condition = new AICondition_HarvestGrowingRate(this, object); break;
            case "existownercarrier"        : break;
            case "ispremiumpcroomuser"      : break;
            case "istargetvehicle"          : condition = new AICondition_IsTargetVehicle(this, object); break;
            case "rideonship"               : break;
            case "checkownerevadeemergency" : break;
            case "checkinstanceteamno"      : break;
            case "checkparentinstanceteamno": break;
            case "checkcurrentregion"       : break;
            case "checkowneroncarrier"      : break;
            default : System.out.println("Not implemented executor: " + name); break;
        }

        if (condition != null)
            _executorConditions.add(condition);
    }

    /**
     * Checks if an AI Executor can be executed.
     * It is mainly used to test against a set of conditions
     * and is being run once per tick. If this condition
     * succeeds, it is being executed immediately, otherwise
     * it will be executed on next frame tick.
     *
     * @return true if can be executed, otherwise false.
     */
    public boolean canExecute() {
        for (AICondition condition : _executorConditions) {
            if (!condition.test())
                return false;
        }
        return true;
    }

    /**
     * Executes this executor.
     * If previous condition was true, this executor
     * will be executed immediately.
     *
     * @param deltaTime The time elapsed since game loop tick start.
     */
    public abstract void execute(int deltaTime);

    /**
     * An AI Handler that can only be used within the AI Executor.
     * This is usually to prevent spawning multiple parent instances
     * in a single class file, which could improve memory footprint.
     *
     * @return Owner of AI Executor, the AI Handler.
     */
    public AIHandler getAiHandler() {
        return _handler;
    }
}