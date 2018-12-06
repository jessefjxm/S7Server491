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
@IAIName("flowertrap")
public class Ai_flowertrap extends CreatureAI {
	public Ai_flowertrap(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x762AB710L /*_UnSummonTime*/, getVariable(0xCC757928L /*AI_UnSummonTime*/));
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		scheduleState(state -> Wait(blendTime), 500);
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 400 && isCreatureVisible(object, false))) {
			if (changeState(state -> AttackBomb(blendTime)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0x762AB710L /*_UnSummonTime*/)) {
			if (changeState(state -> AttackBomb(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void AttackBomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2E6393F1L /*AttackBomb*/);
		doAction(2362681632L /*ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> UnSummon(blendTime)));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> UnSummon(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
