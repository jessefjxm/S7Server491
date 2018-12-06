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
@IAIName("npc_tribute_carriage_hidel")
public class Ai_npc_tribute_carriage_hidel extends CreatureAI {
	public Ai_npc_tribute_carriage_hidel(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x7B513297L /*_count*/, 0);
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, new Integer[] { 0, 0 });
		setVariable(0x3F487035L /*_Hp*/, 0);
		doAction(3985023190L /*HIDE_WAGON*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(3985023190L /*HIDE_WAGON*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 10000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Reday_To_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6E176C32L /*Reday_To_Go*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_11");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Start(blendTime), 1000));
	}

	protected void Move_Calpheon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x759C3A07L /*Move_Calpheon_Start*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0052", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_01(blendTime), 1000)));
	}

	protected void Tribute_Message_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x992D268L /*Tribute_Message_01*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_12");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Calpheon_Ing01(blendTime)));
	}

	protected void Move_Calpheon_Ing01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAB7AFCC0L /*Move_Calpheon_Ing01*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0080", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_02(blendTime), 1000)));
	}

	protected void Tribute_Message_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE334E0BCL /*Tribute_Message_02*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_13");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Calpheon_Ing02(blendTime)));
	}

	protected void Move_Calpheon_Ing02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2F837E09L /*Move_Calpheon_Ing02*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0122", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_03(blendTime), 1000)));
	}

	protected void Tribute_Message_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA9B9D547L /*Tribute_Message_03*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_14");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Calpheon_Ing03(blendTime)));
	}

	protected void Move_Calpheon_Ing03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x75E82D73L /*Move_Calpheon_Ing03*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0200", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_04(blendTime), 1000)));
	}

	protected void Tribute_Message_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x30F17EF7L /*Tribute_Message_04*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_15");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Calpheon_Ing04(blendTime)));
	}

	protected void Move_Calpheon_Ing04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE0E43CFBL /*Move_Calpheon_Ing04*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0242", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_05(blendTime), 1000)));
	}

	protected void Tribute_Message_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1DD2FBB2L /*Tribute_Message_05*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_16");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Calpheon_Ing05(blendTime)));
	}

	protected void Move_Calpheon_Ing05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x47990B0L /*Move_Calpheon_Ing05*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0273", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Tribute_Message_06(blendTime), 1000)));
	}

	protected void Tribute_Message_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB106A7C9L /*Tribute_Message_06*/);
		worldNotify(EChatNoticeType.OfferingCarrier, "GAME", "LUA_TRIBUTE_CARRIER_MESSAGE_17");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> End(blendTime)));
	}

	protected void FindWay_Move_Calpheon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA69AA238L /*FindWay_Move_Calpheon_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Start(blendTime), 1000));
	}

	protected void FindWay_Move_Calpheon_Ing01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x62B6C920L /*FindWay_Move_Calpheon_Ing01*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Ing01(blendTime), 1000));
	}

	protected void FindWay_Move_Calpheon_Ing02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE0B1A8ACL /*FindWay_Move_Calpheon_Ing02*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Ing02(blendTime), 1000));
	}

	protected void FindWay_Move_Calpheon_Ing03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x176D3870L /*FindWay_Move_Calpheon_Ing03*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Ing03(blendTime), 1000));
	}

	protected void FindWay_Move_Calpheon_Ing04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4749D84L /*FindWay_Move_Calpheon_Ing04*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Ing04(blendTime), 1000));
	}

	protected void FindWay_Move_Calpheon_Ing05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x241B02BDL /*FindWay_Move_Calpheon_Ing05*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Calpheon_Ing05(blendTime), 1000));
	}

	protected void End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0xC1A1AF43L /*End*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare(blendTime), 10000));
	}

	protected void Prepare(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8CE046E5L /*Prepare*/);
		if (getLastDestination() == getVariable(0xCB25F3B1L /*TributeCarrierHidel_0273*/)) {
			if (changeState(state -> ReadyToGo_to_Hidel(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xF0568084L /*TributeCarrierHidel_0001*/)) {
			if (changeState(state -> HideWagon(blendTime)))
				return;
		}
		changeState(state -> Prepare(blendTime));
	}

	protected void HideWagon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1F7ABF51L /*HideWagon*/);
		doAction(3985023190L /*HIDE_WAGON*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 5000));
	}

	protected void ReadyToGo_to_Hidel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x469ED3B2L /*ReadyToGo_to_Hidel*/);
		doAction(3188387193L /*READY_TO_GO_HIDEL*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack(blendTime), 1000));
	}

	protected void GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB128473L /*GoBack*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("offering_carrier", "TributeCarrierHidel_0001", ENaviType.ground, () -> {
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
		if (getLastDestination() == getVariable(0xCB25F3B1L /*TributeCarrierHidel_0273*/)) {
			if (changeState(state -> GoBack(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0xF4CC69BCL /*TributeCarrierVelia_0001*/)) {
			if (changeState(state -> Move_Calpheon_Start(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0xAAEF957BL /*TributeCarrierHidel_0052*/)) {
			if (changeState(state -> Move_Calpheon_Ing01(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0x6607D56DL /*TributeCarrierHidel_0080*/)) {
			if (changeState(state -> Move_Calpheon_Ing02(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0x7AA7D0ACL /*TributeCarrierHidel_0122*/)) {
			if (changeState(state -> Move_Calpheon_Ing03(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0xDA84CEA7L /*TributeCarrierHidel_0200*/)) {
			if (changeState(state -> Move_Calpheon_Ing04(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getLastDestination() == getVariable(0x86D7F1A9L /*TributeCarrierHidel_0242*/)) {
			if (changeState(state -> Move_Calpheon_Ing05(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xF74207F6L /*TerminateState*/) {
			if (changeState(state -> Reday_To_Go(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
