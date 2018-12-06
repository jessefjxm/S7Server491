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
@IAIName("npc_delivery_carriage_calpheon")
public class Ai_npc_delivery_carriage_calpheon extends CreatureAI {
	public Ai_npc_delivery_carriage_calpheon(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_Whole_0041 Wagon_Calpheon_Whole_0149 Wagon_Calpheon_Whole_0572 Wagon_Calpheon_Whole_0924 Wagon_Calpheon_Whole_1094", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare(blendTime), 1000)));
	}

	protected void Prepare(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8CE046E5L /*Prepare*/);
		if (getLastDestination() == getVariable(0x1EFB2D0CL /*Wagon_Calpheon_Whole_0041*/)) {
			if (changeState(state -> Ready_To_Go_Keplan(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x61D03EL /*Wagon_Calpheon_Whole_0149*/)) {
			if (changeState(state -> Ready_To_Go_Trent(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xF890FEAAL /*Wagon_Calpheon_Whole_0572*/)) {
			if (changeState(state -> Ready_To_Go_Calpheon_NWGate(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x61EC5A1CL /*Wagon_Calpheon_Whole_0924*/)) {
			if (changeState(state -> Ready_To_Go_Eferia(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA864FFF6L /*Wagon_Calpheon_Whole_1094*/)) {
			if (changeState(state -> Ready_To_Go_Calpheon_SEGate(blendTime)))
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
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go(blendTime), 1000));
	}

	protected void Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCDE20F0FL /*Go*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_Whole_0041 Wagon_Calpheon_Whole_0149 Wagon_Calpheon_Whole_0572 Wagon_Calpheon_Whole_0924 Wagon_Calpheon_Whole_1094", ENaviType.ground, () -> {
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

	protected void Ready_To_Go_Calpheon_SEGate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5AFE1D58L /*Ready_To_Go_Calpheon_SEGate*/);
		doAction(541859425L /*READY_TO_GO_CALPHEON_SEGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Keplan(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23CA5E08L /*Ready_To_Go_Keplan*/);
		doAction(3790307284L /*READY_TO_GO_KEPLAN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Trent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A05492DL /*Ready_To_Go_Trent*/);
		doAction(4068459788L /*READY_TO_GO_TRENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Calpheon_NWGate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x34723B92L /*Ready_To_Go_Calpheon_NWGate*/);
		doAction(364488404L /*READY_TO_GO_CALPHEON_NWGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Eferia(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9F9CCE35L /*Ready_To_Go_Eferia*/);
		doAction(4142470382L /*READY_TO_GO_EFERIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void Ready_To_Go_Flolin(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5B7813A7L /*Ready_To_Go_Flolin*/);
		doAction(1668850880L /*READY_TO_GO_FLOLIN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
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
