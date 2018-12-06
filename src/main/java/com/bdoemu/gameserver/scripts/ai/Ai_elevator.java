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
@IAIName("elevator")
public class Ai_elevator extends CreatureAI {
	public Ai_elevator(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Up(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Wait_Up_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3C18EDADL /*Wait_Up_Ready*/);
		doAction(1610588688L /*WAIT_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Up(blendTime), 1000));
	}

	protected void Wait_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB736BF68L /*Wait_Up*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Move_Down(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Up(blendTime), 1000 + Rnd.get(-2000,2000)));
	}

	protected void Move_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB12FA748L /*Move_Down*/);
		doAction(1147992529L /*MOVE_DOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Down_Ready(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Wait_Down_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBC2F5432L /*Wait_Down_Ready*/);
		doAction(454717839L /*WAIT_DOWN_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Down(blendTime), 1000));
	}

	protected void Wait_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1A9854FBL /*Wait_Down*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Move_Up(blendTime)))
				return;
		}
		doAction(938073226L /*WAIT_DOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Down(blendTime), 1000 + Rnd.get(-2000,2000)));
	}

	protected void Move_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7BA69A15L /*Move_Up*/);
		doAction(3682754745L /*MOVE_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Up_Ready(blendTime), 1000 + Rnd.get(-500,500)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFailSteal(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
