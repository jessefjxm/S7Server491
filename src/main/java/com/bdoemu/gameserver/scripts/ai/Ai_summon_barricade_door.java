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
@IAIName("summon_barricade_door")
public class Ai_summon_barricade_door extends CreatureAI {
	public Ai_summon_barricade_door(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Working_Str(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Close_Door(blendTime), 1000));
	}

	protected void Wait_Second(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1A276660L /*Wait_Second*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Open_Door(blendTime), 1000));
	}

	protected void Working_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B0CB90L /*Working_Str*/);
		useSkill(41073, 1, false, EAIFindTargetType.Self, object -> true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CheckBuildingRate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8E7B6353L /*CheckBuildingRate*/);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, getVariable(0xF3B4EFA1L /*_buildingRate*/) + 1);
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 99) {
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CompleteBuild(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDB0BF386L /*CompleteBuild*/);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 100);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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

	protected void Close_Door(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5A90714L /*Close_Door*/);
		if (findTarget(EAIFindTargetType.AllyVehicle, EAIFindType.normal, true, object -> getTargetCharacterKey(object) == 9093 && getDistanceToTarget(object) < 100)) {
			if (changeState(state -> Close_Idle(0.3)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Open_Door(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA50D97B6L /*Open_Door*/);
		if (findTarget(EAIFindTargetType.AllyVehicle, EAIFindType.normal, true, object -> getTargetCharacterKey(object) == 9093 && getDistanceToTarget(object) < 100)) {
			if (changeState(state -> Open_Idle(0.3)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Closing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x22758241L /*Closing*/);
		doAction(1459005898L /*CLOSING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Close(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x92D1FA8FL /*Close*/);
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9093).forEach(consumer -> consumer.getAi().HandleEvent2(getActor(), null));
		doAction(3059318169L /*CLOSE_DOOR*/, blendTime, onDoActionEnd -> scheduleState(state -> Closing(blendTime), 1000));
	}

	protected void Close_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x197F215CL /*Close_Idle*/);
		doAction(3059318169L /*CLOSE_DOOR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Opening(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D82A115L /*Opening*/);
		doAction(3502208139L /*OPENING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Second(blendTime), 2000));
	}

	protected void Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4C4B2355L /*Open*/);
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9093).forEach(consumer -> consumer.getAi().HandleEvent1(getActor(), null));
		doAction(2666225881L /*OPEN_DOOR*/, blendTime, onDoActionEnd -> scheduleState(state -> Opening(blendTime), 1000));
	}

	protected void Open_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4DBD6539L /*Open_Idle*/);
		doAction(2666225881L /*OPEN_DOOR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Second(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9093).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
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
		if (getState() != 0x22758241L /*Closing*/) {
			if (changeState(state -> Open(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleWorkingEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x4D82A115L /*Opening*/) {
			if (changeState(state -> Close(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDestroyedBase(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9093).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}
}
