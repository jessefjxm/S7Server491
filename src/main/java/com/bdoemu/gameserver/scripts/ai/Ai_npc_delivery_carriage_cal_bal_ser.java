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
@IAIName("npc_delivery_carriage_cal_bal_ser")
public class Ai_npc_delivery_carriage_cal_bal_ser extends CreatureAI {
	public Ai_npc_delivery_carriage_cal_bal_ser(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "loopline_1 loopline_70 loopline_140 loopline_296 loopline_471", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare(blendTime), 1000)));
	}

	protected void Prepare(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8CE046E5L /*Prepare*/);
		if (getLastDestination() == getVariable(0x97242045L /*loopline_1*/)) {
			if (changeState(state -> ReadyToGo_to_Hidel(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x9FACBCB1L /*loopline_70*/)) {
			if (changeState(state -> ReadyToGo_to_Twinvil(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x56A519DAL /*loopline_140*/)) {
			if (changeState(state -> ReadyToGo_to_Chalpheon(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x21F347AL /*loopline_296*/)) {
			if (changeState(state -> ReadyToGo_to_Velia(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA4763D8BL /*loopline_471*/)) {
			if (changeState(state -> ReadyToGo_to_Velia(blendTime)))
				return;
		}
		changeState(state -> Prepare(blendTime));
	}

	protected void ReadyToGo_to_Hidel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x469ED3B2L /*ReadyToGo_to_Hidel*/);
		doAction(3188387193L /*READY_TO_GO_HIDEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void ReadyToGo_to_Twinvil(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5CBF6503L /*ReadyToGo_to_Twinvil*/);
		doAction(2776242966L /*READY_TO_GO_TWINVIL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void ReadyToGo_to_Chalpheon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x36BE49E8L /*ReadyToGo_to_Chalpheon*/);
		doAction(1962145978L /*READY_TO_GO_CHALPHEON*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
	}

	protected void ReadyToGo_to_Velia(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA52FD55CL /*ReadyToGo_to_Velia*/);
		doAction(379504146L /*READY_TO_GO_VELIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 20000));
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "loopline_1 loopline_70 loopline_140 loopline_296 loopline_471", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End(blendTime), 1000)));
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

	protected void End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1A1AF43L /*End*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare(blendTime), 1000));
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
