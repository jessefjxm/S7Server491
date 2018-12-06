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
@IAIName("npc_boarding_carriage")
public class Ai_npc_boarding_carriage extends CreatureAI {
	public Ai_npc_boarding_carriage(Creature actor, Map<Long, Integer> aiVariables) {
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
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 1) {
			if (changeState(state -> Loop_Bal_to_Ser(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 2) {
			if (changeState(state -> Loop_CalpheonNorth(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 3) {
			if (changeState(state -> Loop_Calpheon_Whole(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 10) {
			if (changeState(state -> Loop_CalpheonSouth(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 97) {
			if (changeState(state -> Loop_Whole(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Loop_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x90DB62A4L /*Loop_Bal_to_Ser*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Balenos_Serendia_Route_0157", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_Bal_to_Ser(blendTime), 1000)));
	}

	protected void Prepare_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x41C85267L /*Prepare_Bal_to_Ser*/);
		if (getLastDestination() == getVariable(0xF98B4738L /*Balenos_Serendia_Route_0157*/)) {
			if (changeState(state -> ReadyToGo_to_Glyshi_Bal_to_Ser(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x2C56A677L /*Balenos_Serendia_Route_0271*/)) {
			if (changeState(state -> ReadyToGo_to_WGC_Bal_to_Ser(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xAB04E476L /*Balenos_Serendia_Route_0415*/)) {
			if (changeState(state -> ReadyToGo_to_Velia_Bal_to_Ser(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xE109B499L /*Balenos_Serendia_Route_0003*/)) {
			if (changeState(state -> ReadyToGo_to_Hidel_Bal_to_Ser(blendTime)))
				return;
		}
		changeState(state -> Prepare_Bal_to_Ser(blendTime));
	}

	protected void Go_Start_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFBC7FCF0L /*Go_Start_Bal_to_Ser*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Bal_to_Ser(blendTime), 1000));
	}

	protected void Go_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x38E980C3L /*Go_Bal_to_Ser*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Balenos_Serendia_Route_0157 Balenos_Serendia_Route_0271 Balenos_Serendia_Route_0415 Balenos_Serendia_Route_0003", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End_Bal_to_Ser(blendTime), 1000)));
	}

	protected void End_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23FE322L /*End_Bal_to_Ser*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare_Bal_to_Ser(blendTime), 1000));
	}

	protected void ReadyToGo_to_Glyshi_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEEC8B032L /*ReadyToGo_to_Glyshi_Bal_to_Ser*/);
		doAction(2776242966L /*READY_TO_GO_TWINVIL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Bal_to_Ser(blendTime), 100000));
	}

	protected void ReadyToGo_to_WGC_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8D92F926L /*ReadyToGo_to_WGC_Bal_to_Ser*/);
		doAction(3767835282L /*READY_TO_GO_WESTCAMP*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Bal_to_Ser(blendTime), 100000));
	}

	protected void ReadyToGo_to_Velia_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x81593914L /*ReadyToGo_to_Velia_Bal_to_Ser*/);
		doAction(379504146L /*READY_TO_GO_VELIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Bal_to_Ser(blendTime), 100000));
	}

	protected void ReadyToGo_to_Hidel_Bal_to_Ser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4C50D5B9L /*ReadyToGo_to_Hidel_Bal_to_Ser*/);
		doAction(3188387193L /*READY_TO_GO_HIDEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Bal_to_Ser(blendTime), 100000));
	}

	protected void Loop_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x90D50872L /*Loop_CalpheonNorth*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_North_0296", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_CalpheonNorth(blendTime), 1000)));
	}

	protected void Prepare_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x10FBE263L /*Prepare_CalpheonNorth*/);
		if (getLastDestination() == getVariable(0x389D656BL /*Wagon_Calpheon_North_0296*/)) {
			if (changeState(state -> ReadyToGo_to_Eferia_CalpheonNorth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x3358FF64L /*Wagon_Calpheon_North_0080*/)) {
			if (changeState(state -> ReadyToGo_to_Flolin_CalpheonNorth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x3E68923BL /*Wagon_Calpheon_North_0316*/)) {
			if (changeState(state -> ReadyToGo_to_GreatFarm_CalpheonNorth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x1A8533C3L /*Wagon_Calpheon_North_0262*/)) {
			if (changeState(state -> ReadyToGo_to_Calpheon_NWGate_CalpheonNorth(blendTime)))
				return;
		}
		changeState(state -> Prepare_CalpheonNorth(blendTime));
	}

	protected void Go_Start_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF76C6F99L /*Go_Start_CalpheonNorth*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_CalpheonNorth(blendTime), 1000));
	}

	protected void Go_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFBD36708L /*Go_CalpheonNorth*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_North_0296 Wagon_Calpheon_North_0080 Wagon_Calpheon_North_0316 Wagon_Calpheon_North_0262", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End_CalpheonNorth(blendTime), 1000)));
	}

	protected void End_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC45B824L /*End_CalpheonNorth*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare_CalpheonNorth(blendTime), 1000));
	}

	protected void ReadyToGo_to_Eferia_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2348DAB6L /*ReadyToGo_to_Eferia_CalpheonNorth*/);
		doAction(4142470382L /*READY_TO_GO_EFERIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonNorth(blendTime), 100000));
	}

	protected void ReadyToGo_to_Flolin_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD8625C89L /*ReadyToGo_to_Flolin_CalpheonNorth*/);
		doAction(1668850880L /*READY_TO_GO_FLOLIN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonNorth(blendTime), 100000));
	}

	protected void ReadyToGo_to_GreatFarm_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x73B6CC1AL /*ReadyToGo_to_GreatFarm_CalpheonNorth*/);
		doAction(4249144604L /*READY_TO_GO_GREATFARM*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonNorth(blendTime), 100000));
	}

	protected void ReadyToGo_to_Calpheon_NWGate_CalpheonNorth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4CC1C8E2L /*ReadyToGo_to_Calpheon_NWGate_CalpheonNorth*/);
		doAction(364488404L /*READY_TO_GO_CALPHEON_NWGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonNorth(blendTime), 100000));
	}

	protected void Loop_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD18324CAL /*Loop_CalpheonSouth*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_South_0004", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_CalpheonSouth(blendTime), 1000)));
	}

	protected void Prepare_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x987A0E8FL /*Prepare_CalpheonSouth*/);
		if (getLastDestination() == getVariable(0xD1F2D01EL /*Wagon_Calpheon_South_0004*/)) {
			if (changeState(state -> ReadyToGo_To_Trent_CalpheonSouth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x8D44D4D5L /*Wagon_Calpheon_South_0353*/)) {
			if (changeState(state -> ReadyToGo_To_Behr_CalpheonSouth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xC159ADCAL /*Wagon_Calpheon_South_0504*/)) {
			if (changeState(state -> ReadyToGo_To_Keplan_CalpheonSouth(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x6AE6727DL /*Wagon_Calpheon_South_0739*/)) {
			if (changeState(state -> ReadyToGo_To_Calpheon_SEGate_CalpheonSouth(blendTime)))
				return;
		}
		changeState(state -> Prepare_CalpheonSouth(blendTime));
	}

	protected void Go_Start_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x36616B0L /*Go_Start_CalpheonSouth*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_CalpheonSouth(blendTime), 1000));
	}

	protected void Go_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5DB50F5L /*Go_CalpheonSouth*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_South_0004 Wagon_Calpheon_South_0353 Wagon_Calpheon_South_0504 Wagon_Calpheon_South_0739", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End_CalpheonSouth(blendTime), 1000)));
	}

	protected void End_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB9BDEEA3L /*End_CalpheonSouth*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare_CalpheonSouth(blendTime), 1000));
	}

	protected void ReadyToGo_To_Trent_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5ABD080L /*ReadyToGo_To_Trent_CalpheonSouth*/);
		doAction(4068459788L /*READY_TO_GO_TRENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonSouth(blendTime), 100000));
	}

	protected void ReadyToGo_To_Behr_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC5235B3L /*ReadyToGo_To_Behr_CalpheonSouth*/);
		doAction(4292429126L /*READY_TO_GO_BEHR*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonSouth(blendTime), 100000));
	}

	protected void ReadyToGo_To_Keplan_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFBB473C2L /*ReadyToGo_To_Keplan_CalpheonSouth*/);
		doAction(3790307284L /*READY_TO_GO_KEPLAN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonSouth(blendTime), 100000));
	}

	protected void ReadyToGo_To_Calpheon_SEGate_CalpheonSouth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8D6A2FAAL /*ReadyToGo_To_Calpheon_SEGate_CalpheonSouth*/);
		doAction(541859425L /*READY_TO_GO_CALPHEON_SEGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_CalpheonSouth(blendTime), 100000));
	}

	protected void Loop_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF56B8513L /*Loop_Calpheon_Whole*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_Whole_0924 Wagon_Calpheon_Whole_1094 Wagon_Calpheon_Whole_1329 Wagon_Calpheon_Whole_0041 Wagon_Calpheon_Whole_0149 Wagon_Calpheon_Whole_0572 Wagon_Calpheon_Whole_0433", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_Calpheon_Whole(blendTime), 1000)));
	}

	protected void Prepare_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x734C569BL /*Prepare_Calpheon_Whole*/);
		if (getLastDestination() == getVariable(0x61EC5A1CL /*Wagon_Calpheon_Whole_0924*/)) {
			if (changeState(state -> ReadyToGo_To_Calpheon_NWGate_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA864FFF6L /*Wagon_Calpheon_Whole_1094*/)) {
			if (changeState(state -> ReadyToGo_To_Eferia_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xAD3C6F46L /*Wagon_Calpheon_Whole_1329*/)) {
			if (changeState(state -> ReadyToGo_To_Flolin_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x1EFB2D0CL /*Wagon_Calpheon_Whole_0041*/)) {
			if (changeState(state -> ReadyToGo_To_Calpheon_SEGate_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x61D03EL /*Wagon_Calpheon_Whole_0149*/)) {
			if (changeState(state -> ReadyToGo_To_Keplan_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xF890FEAAL /*Wagon_Calpheon_Whole_0572*/)) {
			if (changeState(state -> ReadyToGo_To_Behr_Calpheon_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x67A8A7A2L /*Wagon_Calpheon_Whole_0433*/)) {
			if (changeState(state -> ReadyToGo_To_Trent_Calpheon_Whole(blendTime)))
				return;
		}
		changeState(state -> Prepare_Calpheon_Whole(blendTime));
	}

	protected void ReadyToGo_To_Calpheon_NWGate_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB06B4906L /*ReadyToGo_To_Calpheon_NWGate_Calpheon_Whole*/);
		doAction(364488404L /*READY_TO_GO_CALPHEON_NWGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Eferia_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8D2D2C93L /*ReadyToGo_To_Eferia_Calpheon_Whole*/);
		doAction(4142470382L /*READY_TO_GO_EFERIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Flolin_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDC7CA4B5L /*ReadyToGo_To_Flolin_Calpheon_Whole*/);
		doAction(1668850880L /*READY_TO_GO_FLOLIN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Calpheon_SEGate_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF622D185L /*ReadyToGo_To_Calpheon_SEGate_Calpheon_Whole*/);
		doAction(541859425L /*READY_TO_GO_CALPHEON_SEGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Keplan_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x32C65FF0L /*ReadyToGo_To_Keplan_Calpheon_Whole*/);
		doAction(3790307284L /*READY_TO_GO_KEPLAN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Behr_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x20996ECDL /*ReadyToGo_To_Behr_Calpheon_Whole*/);
		doAction(4292429126L /*READY_TO_GO_BEHR*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void ReadyToGo_To_Trent_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD0663107L /*ReadyToGo_To_Trent_Calpheon_Whole*/);
		doAction(4068459788L /*READY_TO_GO_TRENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Calpheon_Whole(blendTime), 20000));
	}

	protected void Go_Start_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x19B2356CL /*Go_Start_Calpheon_Whole*/);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.prepare);
		deliveryItem(EAIDeliveryType.start);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Calpheon_Whole(blendTime), 1000));
	}

	protected void Go_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8AD82E1L /*Go_Calpheon_Whole*/);
		deliveryItem(EAIDeliveryType.move);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Calpheon_Whole_0924 Wagon_Calpheon_Whole_1094 Wagon_Calpheon_Whole_1329 Wagon_Calpheon_Whole_0041 Wagon_Calpheon_Whole_0149 Wagon_Calpheon_Whole_0572 Wagon_Calpheon_Whole_0433", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> End_Calpheon_Whole(blendTime), 1000)));
	}

	protected void End_Calpheon_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8CDFCB7CL /*End_Calpheon_Whole*/);
		deliveryItem(EAIDeliveryType.end);
		doAction(2087858766L /*DELIVERY_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Prepare_Calpheon_Whole(blendTime), 1000));
	}

	protected void Loop_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9E6D7906L /*Loop_Whole*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Whole_0001 Wagon_Whole_0076 Wagon_Whole_0163 Wagon_Whole_0279 Wagon_Whole_0425 Wagon_Whole_0554 Wagon_Whole_0831 Wagon_Whole_0922 Wagon_Whole_1088 Wagon_Whole_1213 Wagon_Whole_1494 Wagon_Whole_1657 Wagon_Whole_1760 Wagon_Whole_1961 Wagon_Whole_2170", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Prepare_Whole(blendTime), 1000)));
	}

	protected void Prepare_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD8BB2C75L /*Prepare_Whole*/);
		if (getLastDestination() == getVariable(0x95A87C4DL /*Wagon_Whole_0001*/)) {
			if (changeState(state -> ReadyToGo_To_Westcamp_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x9AB65054L /*Wagon_Whole_0076*/)) {
			if (changeState(state -> ReadyToGo_To_Olvia_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x68B066C1L /*Wagon_Whole_0163*/)) {
			if (changeState(state -> ReadyToGo_To_Flolin_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x87C6A17DL /*Wagon_Whole_0279*/)) {
			if (changeState(state -> ReadyToGo_To_Eferia_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x745C0F2BL /*Wagon_Whole_0425*/)) {
			if (changeState(state -> ReadyToGo_To_Calpheon_NWgate_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x470EBE48L /*Wagon_Whole_0554*/)) {
			if (changeState(state -> ReadyToGo_To_Trent_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x2CAF7E22L /*Wagon_Whole_0831*/)) {
			if (changeState(state -> ReadyToGo_To_Behr_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xECFB9FBBL /*Wagon_Whole_0922*/)) {
			if (changeState(state -> ReadyToGo_To_Keplan_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x2AFF1E68L /*Wagon_Whole_1088*/)) {
			if (changeState(state -> ReadyToGo_To_Twinvil_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xF4F1BBC2L /*Wagon_Whole_1213*/)) {
			if (changeState(state -> ReadyToGo_To_Tarif_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x949C4C35L /*Wagon_Whole_1494*/)) {
			if (changeState(state -> ReadyToGo_To_Abuntown_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x3A40992AL /*Wagon_Whole_1657*/)) {
			if (changeState(state -> ReadyToGo_To_Altinova_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xB320BFCDL /*Wagon_Whole_1760*/)) {
			if (changeState(state -> ReadyToGo_To_Kusha_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0xA37A3C99L /*Wagon_Whole_1961*/)) {
			if (changeState(state -> ReadyToGo_To_Hidel_Whole(blendTime)))
				return;
		}
		if (getLastDestination() == getVariable(0x711758FEL /*Wagon_Whole_2170*/)) {
			if (changeState(state -> ReadyToGo_To_Velia_Whole(blendTime)))
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
		deliveryItem(EAIDeliveryType.prepare);
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_Whole_0001 Wagon_Whole_0076 Wagon_Whole_0163 Wagon_Whole_0279 Wagon_Whole_0425 Wagon_Whole_0554 Wagon_Whole_0831 Wagon_Whole_0922 Wagon_Whole_1088 Wagon_Whole_1213 Wagon_Whole_1494 Wagon_Whole_1657 Wagon_Whole_1760 Wagon_Whole_1961 Wagon_Whole_2170", ENaviType.ground, () -> {
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

	protected void ReadyToGo_To_Westcamp_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x66044E6FL /*ReadyToGo_To_Westcamp_Whole*/);
		doAction(3767835282L /*READY_TO_GO_WESTCAMP*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Olvia_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9AE5A07BL /*ReadyToGo_To_Olvia_Whole*/);
		doAction(476436045L /*READY_TO_GO_OLVIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Flolin_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE2FC7FFAL /*ReadyToGo_To_Flolin_Whole*/);
		doAction(1668850880L /*READY_TO_GO_FLOLIN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Eferia_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5F809A80L /*ReadyToGo_To_Eferia_Whole*/);
		doAction(4142470382L /*READY_TO_GO_EFERIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Calpheon_NWgate_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF8C88BEL /*ReadyToGo_To_Calpheon_NWgate_Whole*/);
		doAction(364488404L /*READY_TO_GO_CALPHEON_NWGATE*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Trent_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x45EC7654L /*ReadyToGo_To_Trent_Whole*/);
		doAction(4068459788L /*READY_TO_GO_TRENT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Behr_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1FF3992L /*ReadyToGo_To_Behr_Whole*/);
		doAction(4292429126L /*READY_TO_GO_BEHR*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Keplan_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8505488DL /*ReadyToGo_To_Keplan_Whole*/);
		doAction(3790307284L /*READY_TO_GO_KEPLAN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Twinvil_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA49B9972L /*ReadyToGo_To_Twinvil_Whole*/);
		doAction(2776242966L /*READY_TO_GO_TWINVIL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Tarif_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE65A0BFAL /*ReadyToGo_To_Tarif_Whole*/);
		doAction(1168630048L /*READY_TO_GO_TARIF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Abuntown_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6FA6DFE3L /*ReadyToGo_To_Abuntown_Whole*/);
		doAction(1865559268L /*READY_TO_GO_ABUNTOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Altinova_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC1797F2L /*ReadyToGo_To_Altinova_Whole*/);
		doAction(423789644L /*READY_TO_GO_ALTINOVA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Kusha_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x11E82FECL /*ReadyToGo_To_Kusha_Whole*/);
		doAction(1217443436L /*READY_TO_GO_KUSHA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Hidel_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xABAAC1FBL /*ReadyToGo_To_Hidel_Whole*/);
		doAction(3188387193L /*READY_TO_GO_HIDEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	protected void ReadyToGo_To_Velia_Whole(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA7B98DEAL /*ReadyToGo_To_Velia_Whole*/);
		doAction(379504146L /*READY_TO_GO_VELIA*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Start_Whole(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
