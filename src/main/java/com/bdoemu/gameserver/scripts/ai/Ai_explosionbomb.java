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
@IAIName("explosionbomb")
public class Ai_explosionbomb extends CreatureAI {
	public Ai_explosionbomb(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF630F33AL /*_Distance*/, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 1000));
	}

	protected void Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x29A56175L /*Stop*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 1000));
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
		getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().HandleEvent1(getActor(), null));
		doAction(3912108767L /*EXPLOSION*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x277B023DL /*Die_Ready*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1500));
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

	@Override
	public EAiHandlerResult Call_stop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stop(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Call_from_Dialog(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Counting_6(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Call_remove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Ready(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Explosion(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Counting_6(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
