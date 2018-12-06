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
@IAIName("npc_ship_transport_tarif_altinova")
public class Ai_npc_ship_transport_tarif_altinova extends CreatureAI {
	public Ai_npc_ship_transport_tarif_altinova(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FirstGo(blendTime), 1000));
	}

	protected void FirstGo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBE33C101L /*FirstGo*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "transport_ship_loop_0310 transport_ship_loop_0215", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Go_Start(blendTime), 1000)));
	}

	protected void Go_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x79CC42BBL /*Go_Start*/);
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
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "transport_ship_loop_0310 transport_ship_loop_0215", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End(blendTime), 1000)));
	}

	protected void End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1A1AF43L /*End*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start(blendTime), 1000));
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
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "sender_destination", ENaviType.ground, () -> {
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
