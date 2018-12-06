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
@IAIName("npc_delivery_carriage_kamasilvia")
public class Ai_npc_delivery_carriage_kamasilvia extends CreatureAI {
	public Ai_npc_delivery_carriage_kamasilvia(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x7B513297L /*_count*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FirstGo(blendTime), 1000));
	}

	protected void FirstGo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBE33C101L /*FirstGo*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Kamasilvia_0002 Wagon_Kamasilvia_0319 Wagon_Kamasilvia_0929 Wagon_Kamasilvia_1126", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare(blendTime), 1000)));
	}

	protected void Prepare(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8CE046E5L /*Prepare*/);
		if (getLastDestination() == getVariable(0xF8E97067L /*Wagon_Kamasilvia_0002*/)) {
			if (changeState(state -> Ready_To_Go_Trent(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x6FB9DAFL /*Wagon_Kamasilvia_0319*/)) {
			if (changeState(state -> Ready_To_Go_Grana(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA3DEF6F4L /*Wagon_Kamasilvia_0929*/)) {
			if (changeState(state -> Ready_To_Go_WisdomTree(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x6659E0BAL /*Wagon_Kamasilvia_1126*/)) {
			if (changeState(state -> Ready_To_Go_Calpheon_SGate(blendTime)))
				return;
		}
		changeState(state -> Prepare(blendTime));
	}

	protected void Go_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x79CC42BBL /*Go_Start*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go(blendTime), 1000));
	}

	protected void Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCDE20F0FL /*Go*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Kamasilvia_0002 Wagon_Kamasilvia_0319 Wagon_Kamasilvia_0929 Wagon_Kamasilvia_1126", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End(blendTime), 1000)));
	}

	protected void End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1A1AF43L /*End*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare(blendTime), 1000));
	}

	protected void Ready_To_Go_Calpheon_SGate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC6F1CD7L /*Ready_To_Go_Calpheon_SGate*/);
		doAction(1966042104L /*READY_TO_GO_CALPHEON_SGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Trent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A05492DL /*Ready_To_Go_Trent*/);
		doAction(4068459788L /*READY_TO_GO_TRENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Grana(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E768394L /*Ready_To_Go_Grana*/);
		doAction(220349940L /*READY_TO_GO_GRANA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_WisdomTree(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBBADEA6EL /*Ready_To_Go_WisdomTree*/);
		doAction(4175891675L /*READY_TO_GO_WISDOMTREE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void FindWay_First(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA57D2B4BL /*FindWay_First*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FirstGo(blendTime), 600000));
	}

	protected void FindWay_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7A5D39BL /*FindWay_Go*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go(blendTime), 600000));
	}

	protected void FindWay_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB6E9C8B3L /*FindWay_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Destination(blendTime), 600000));
	}

	protected void Move_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA5879D4L /*Move_Destination*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "sender_destination", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End(blendTime), 1000)));
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
}
