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
@IAIName("npc_boarding_carriage1")
public class Ai_npc_boarding_carriage1 extends CreatureAI {
	public Ai_npc_boarding_carriage1(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Whole(blendTime), 1000));
	}

	protected void Loop_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9E6D7906L /*Loop_Whole*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Desert_0001 Wagon_Desert_0256 Wagon_Desert_0622 Wagon_Desert_0965 Wagon_Desert_1361 Wagon_Desert_1882 Wagon_Desert_2638", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_Whole(blendTime), 1000)));
	}

	protected void Prepare_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD8BB2C75L /*Prepare_Whole*/);
		if (getLastDestination() == getVariable(0xC1159AB2L /*Wagon_Desert_0001*/)) {
			if (changeState(state -> ReadyToGo_To_CRESCENT(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xD937040EL /*Wagon_Desert_0256*/)) {
			if (changeState(state -> ReadyToGo_To_TITIUM(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x21BC4606L /*Wagon_Desert_0622*/)) {
			if (changeState(state -> ReadyToGo_To_PILACU(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xAEA48FBFL /*Wagon_Desert_0965*/)) {
			if (changeState(state -> ReadyToGo_To_AREHAZA(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xE775FFF4L /*Wagon_Desert_1361*/)) {
			if (changeState(state -> ReadyToGo_To_VALENCIA(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA922F98AL /*Wagon_Desert_1882*/)) {
			if (changeState(state -> ReadyToGo_To_GAHAZ(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xDFF006C8L /*Wagon_Desert_2638*/)) {
			if (changeState(state -> ReadyToGo_To_BAZAR(blendTime)))
				return;
		}
		changeState(state -> Prepare_Whole(blendTime));
	}

	protected void Go_Start_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBA225E65L /*Go_Start_Whole*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Whole(blendTime), 1000));
	}

	protected void Go_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDEB25A98L /*Go_Whole*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Desert_0001 Wagon_Desert_0256 Wagon_Desert_0622 Wagon_Desert_0965 Wagon_Desert_1361 Wagon_Desert_1882 Wagon_Desert_2638", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End_Whole(blendTime), 1000)));
	}

	protected void End_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x541103BAL /*End_Whole*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare_Whole(blendTime), 1000));
	}

	protected void ReadyToGo_To_BAZAR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F290BAFL /*ReadyToGo_To_BAZAR*/);
		doAction(14477248L /*READY_TO_GO_BAZAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_CRESCENT(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x38C08073L /*ReadyToGo_To_CRESCENT*/);
		doAction(448430781L /*READY_TO_GO_CRESCENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_TITIUM(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBDADE577L /*ReadyToGo_To_TITIUM*/);
		doAction(4103442501L /*READY_TO_GO_TITIUM*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_PILACU(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAAE72FC8L /*ReadyToGo_To_PILACU*/);
		doAction(1772367923L /*READY_TO_GO_PILACU*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_AREHAZA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x79244A53L /*ReadyToGo_To_AREHAZA*/);
		doAction(4272438309L /*READY_TO_GO_AREHAZA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_VALENCIA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6DF7052CL /*ReadyToGo_To_VALENCIA*/);
		doAction(332713111L /*READY_TO_GO_VALENCIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_GAHAZ(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA4A4029L /*ReadyToGo_To_GAHAZ*/);
		doAction(1394751619L /*READY_TO_GO_GAHAZ*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
