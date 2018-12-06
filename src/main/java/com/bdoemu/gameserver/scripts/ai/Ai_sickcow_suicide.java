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
@IAIName("sickcow_suicide")
public class Ai_sickcow_suicide extends CreatureAI {
	public Ai_sickcow_suicide(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(927442757L /*WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 300 && getTargetHp(object) > 0)) {
			if (changeState(state -> Pre_Die(blendTime)))
				return;
		}
		doAction(927442757L /*WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Pre_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFBEE5987L /*Pre_Die*/);
		doAction(2433446149L /*DIE_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Still(blendTime), 3000));
	}

	protected void Die_Still(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xADC9B2E6L /*Die_Still*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Die(0.3)))
				return;
		}
		doAction(3152598923L /*DIED_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Still(blendTime), 3000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(425277756L /*DIE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 3000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2BD8C797L /*Suicide_Die*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Suicide_Die1(0.3)))
				return;
		}
		doAction(3344111505L /*DIE1*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die1(blendTime), 1000));
	}

	protected void Suicide_Die1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x27877871L /*Suicide_Die1*/);
		if(getCallCount() == 15) {
			if (changeState(state -> Suicide_Die2(0.3)))
				return;
		}
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die2(blendTime), 1000));
	}

	protected void Suicide_Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x8520D7B7L /*Suicide_Die2*/);
		doAction(4139484108L /*DIE3*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die2(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 5000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/ && target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Pre_Die(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Die_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAtSpawnEndTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
