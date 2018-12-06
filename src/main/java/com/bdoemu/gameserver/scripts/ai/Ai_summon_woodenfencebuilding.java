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
@IAIName("summon_woodenfencebuilding")
public class Ai_summon_woodenfencebuilding extends CreatureAI {
	public Ai_summon_woodenfencebuilding(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Working_Str(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 1000));
	}

	protected void Working_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B0CB90L /*Working_Str*/);
		useSkill(41072, 1, false, EAIFindTargetType.Self, object -> true);
		doAction(3058152320L /*WORKING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		if (findTarget(EAIFindTargetType.AllyVehicle, EAIFindType.normal, true, object -> getTargetCharacterKey(object) == 9092 && getDistanceToTarget(object) < 100)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9092).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
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
	public EAiHandlerResult HandleDestroyedBase(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Character, object -> getDistanceToTarget(object) < 100 && getTargetCharacterKey(object) == 9092).forEach(consumer -> consumer.getAi()._DestroyedBase(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}
}
