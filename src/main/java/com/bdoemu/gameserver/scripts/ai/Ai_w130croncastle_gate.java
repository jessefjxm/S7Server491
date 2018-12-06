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
@IAIName("w130croncastle_gate")
public class Ai_w130croncastle_gate extends CreatureAI {
	public Ai_w130croncastle_gate(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A34695AL /*Stance*/);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Wait_Close(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) >= 3) {
			if (changeState(state -> Wait_Open(blendTime)))
				return;
		}
		changeState(state -> Stance(blendTime));
	}

	protected void Hundle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9B792C8CL /*Hundle*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(639015291L /*OPEN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Hundle2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB651BC7AL /*Hundle2*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3228089413L /*OPEN_ING2*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4C4B2355L /*Open*/);
		setVariable(0xF06310B7L /*_stance*/, 3);
		doAction(275159270L /*OPEN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x92D1FA8FL /*Close*/);
		setVariable(0xF06310B7L /*_stance*/, 0);
		doAction(1232650280L /*CLOSE*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Wait_Close(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBA6E4261L /*Wait_Close*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Close(blendTime), 10000));
	}

	protected void Wait_Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B22E56L /*Wait_Open*/);
		doAction(3081895922L /*WAIT_OPEN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Open(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOpen(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> Open(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Hundle(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> Hundle2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleClose(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> Close(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
