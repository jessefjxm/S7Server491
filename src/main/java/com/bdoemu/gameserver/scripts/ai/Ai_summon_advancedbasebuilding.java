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
@IAIName("summon_advancedbasebuilding")
public class Ai_summon_advancedbasebuilding extends CreatureAI {
	public Ai_summon_advancedbasebuilding(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 0);
		setVariable(0x3F487035L /*_Hp*/, 0);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		useSkill(40403, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CheckBuildingRate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8E7B6353L /*CheckBuildingRate*/);
		useSkill(40377, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, getVariable(0xF3B4EFA1L /*_buildingRate*/) + 1);
		useSkill(40404, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) == 100) {
			if (changeState(state -> CompleteBuild(blendTime)))
				return;
		}
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 0) {
			if (changeState(state -> BuildingState_1(blendTime)))
				return;
		}
		scheduleState(state -> CheckBuildingRate(blendTime), 1000);
	}

	protected void BuildingState_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F7FD0A3L /*BuildingState_1*/);
		doAction(3999688434L /*BUILDING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
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

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> true);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Working_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B0CB90L /*Working_Str*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		useSkill(40377, 1, false, EAIFindTargetType.Self, object -> true);
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 1800000) {
			if (changeState(state -> Working_End(blendTime)))
				return;
		}
		doAction(3058152320L /*WORKING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Working_Str(blendTime), 18000));
	}

	protected void Working_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDDBE5589L /*Working_End*/);
		useSkill(40382, 1, false, EAIFindTargetType.Self, object -> true);
		doAction(3781959056L /*WORKING_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Working_End(blendTime), 500));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		changeState(state -> TerminateState(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleWorkingStart(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (changeState(state -> Working_Str(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleWorkingEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleActiveSiegeObject(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Working_End(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCompleteSiegeObject(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> CompleteBuild(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
