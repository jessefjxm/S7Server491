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
@IAIName("spirit_earth_dummy")
public class Ai_spirit_earth_dummy extends CreatureAI {
	public Ai_spirit_earth_dummy(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x374FD7BFL /*_isSpin*/, getVariable(0xE4CCB9F8L /*AI_Spin*/));
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Logic(blendTime), 100));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (getVariable(0x374FD7BFL /*_isSpin*/) == 1) {
			if (changeState(state -> Attack_Spin(0.1)))
				return;
		}
		if (getVariable(0x374FD7BFL /*_isSpin*/) == 0) {
			if (changeState(state -> Attack_Earthquake(0.1)))
				return;
		}
		changeState(state -> Attack_Logic(blendTime));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		changeState(state -> Die(blendTime));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC02DCCFL /*UnSummon*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
		doAction(1340753835L /*UNSUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Attack_Earthquake(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61BA060L /*Attack_Earthquake*/);
		doAction(1829204510L /*ATTACK_EARTHQUAKE*/, blendTime, onDoActionEnd -> changeState(state -> UnSummon(blendTime)));
	}

	protected void Attack_Spin(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAD8562E5L /*Attack_Spin*/);
		doAction(1853421619L /*ATTACK_SPIN_STR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
			changeState(state -> Die(blendTime));
		});
	}

	protected void Attack_Spin_End2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1827CA57L /*Attack_Spin_End2*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
		doAction(1624239483L /*ATTACK_SPIN_END2*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult End_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Spin_End2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
