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
@IAIName("w232_renegadotemplar")
public class Ai_w232_renegadotemplar extends CreatureAI {
	public Ai_w232_renegadotemplar(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Open_Door(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA50D97B6L /*Open_Door*/);
		doAction(2666225881L /*OPEN_DOOR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Open_Door1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5D959E4L /*Open_Door1*/);
		doAction(2078377146L /*OPEN_DOOR1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Purify_Fire(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE43A4120L /*Purify_Fire*/);
		doAction(2481896809L /*PURIFY_FIRE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA5879D4L /*Move_Destination*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderDestination, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Open_Door(blendTime), 1000)));
	}

	protected void MoveFast_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x29B7F003L /*MoveFast_Destination*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderDestination, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Open_Door1(blendTime), 1000)));
	}

	protected void Event1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2F2F26A7L /*Event1*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderDestination, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Purify_Fire(blendTime), 1000)));
	}

	protected void Event2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE16F7D05L /*Event2*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderDestination, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MoveFast_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEA00F6BFL /*MoveFast_Target*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderTarget, 0, 0, 0, 50, 100, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 2000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Move_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MoveFast_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MoveFast_Target(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Event1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Event2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Open_Door1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
