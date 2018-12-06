package com.bdoemu.gameserver.scripts.ai;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.deprecated.*;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.actions.enums.*;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.misc.enums.ETradeCommerceType;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author H1X4
 */

@SuppressWarnings("all")
@IAIName("summon_supplybasebuilding")
public class Ai_summon_supplybasebuilding extends CreatureAI {
	public Ai_summon_supplybasebuilding(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 0);
		setVariable(0x3F487035L /*_Hp*/, 0);
		useSkill(41063, 1, false, EAIFindTargetType.Self, object -> true);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		useSkill(40403, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CheckBuildingRate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8E7B6353L /*CheckBuildingRate*/);
		useSkill(40377, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, getVariable(0xF3B4EFA1L /*_buildingRate*/) + 1);
		useSkill(40404, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 99) {
			if (changeState(state -> CompleteBuild(blendTime)))
				return;
		}
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 0) {
			if (changeState(state -> BuildingState_1(blendTime)))
				return;
		}
		doAction(3999688434L /*BUILDING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void BuildingState_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F7FD0A3L /*BuildingState_1*/);
		doAction(3999688434L /*BUILDING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckDieCondition(blendTime), 1000));
	}

	protected void CompleteBuild(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDB0BF386L /*CompleteBuild*/);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		useSkill(40408, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 100);
		doAction(287969247L /*BUILDING_COMPLETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void CheckDieCondition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x70964AB8L /*CheckDieCondition*/);
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) < 100) {
			if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, true, object -> getTargetCharacterKey(object) == 37094 && getDistanceToTarget(object) < 100)) {
				if (changeState(state -> CheckBuildingRate(0.3)))
					return;
			}
		}
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) == 100) {
			if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, true, object -> getTargetCharacterKey(object) == 37094 && getDistanceToTarget(object) < 100)) {
				if (changeState(state -> Summon_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Die(blendTime));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckDieCondition(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 37094).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDestroyedBase(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 37094).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}
}
