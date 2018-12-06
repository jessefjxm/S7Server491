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
@IAIName("timebomb")
public class Ai_timebomb extends CreatureAI {
	public Ai_timebomb(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_4(blendTime), 500));
	}

	protected void Counting_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF2730CA0L /*Counting_6*/);
		doAction(3929505664L /*COUNTING_6*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_5(blendTime), 2000));
	}

	protected void Counting_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC9F56F29L /*Counting_5*/);
		doAction(2169760715L /*COUNTING_5*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_4(blendTime), 2000));
	}

	protected void Counting_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x316BBEBFL /*Counting_4*/);
		doAction(2829908259L /*COUNTING_4*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_3(blendTime), 2000));
	}

	protected void Counting_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x304D40FL /*Counting_3*/);
		doAction(1363672748L /*COUNTING_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_2(blendTime), 2000));
	}

	protected void Counting_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DCF92FEL /*Counting_2*/);
		doAction(3633523161L /*COUNTING_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Counting_1(blendTime), 2000));
	}

	protected void Counting_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x505AC1D3L /*Counting_1*/);
		doAction(1721793704L /*COUNTING_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Explosion(blendTime), 2000));
	}

	protected void Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCDBD5BA4L /*Explosion*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(3912108767L /*EXPLOSION*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
