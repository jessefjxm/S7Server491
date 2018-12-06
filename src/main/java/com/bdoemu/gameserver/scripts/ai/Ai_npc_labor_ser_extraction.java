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
@IAIName("npc_labor_ser_extraction")
public class Ai_npc_labor_ser_extraction extends CreatureAI {
	public Ai_npc_labor_ser_extraction(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 1) {
			if (changeState(state -> Type1_Go(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 2) {
			if (changeState(state -> Type2_Go(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Type1_FirstGo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x84CBB0A3L /*Type1_FirstGo*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Load_Stuff(blendTime), 1000)));
	}

	protected void Type1_Load_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57F18479L /*Type1_Load_Stuff*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_Go(blendTime), 1000));
	}

	protected void Type1_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x32EFC16BL /*Type1_Go*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_009", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Rest_In_Go(blendTime), 1000)));
	}

	protected void Type1_Rest_In_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5A3CF558L /*Type1_Rest_In_Go*/);
		doAction(720668828L /*REST_WITH_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_Go1(blendTime), 1000));
	}

	protected void Type1_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA574EF37L /*Type1_Go1*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_015", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Clear_Stuff(blendTime), 1000)));
	}

	protected void Type1_Clear_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6296418AL /*Type1_Clear_Stuff*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_GoBack(blendTime), 1000));
	}

	protected void Type1_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE25865ECL /*Type1_GoBack*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_023", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Rest_In_GoBack(blendTime), 1000)));
	}

	protected void Type1_Rest_In_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA312F831L /*Type1_Rest_In_GoBack*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_GoBack1(blendTime), 1000));
	}

	protected void Type1_GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2D2C6FECL /*Type1_GoBack1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_029", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Rest_In_GoBack1(blendTime), 1000)));
	}

	protected void Type1_Rest_In_GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEDBBF136L /*Type1_Rest_In_GoBack1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_FirstGo(blendTime), 1000));
	}

	protected void Type2_FirstGo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE02BD708L /*Type2_FirstGo*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_031", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Load_Stuff(blendTime), 1000)));
	}

	protected void Type2_Load_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF1C53216L /*Type2_Load_Stuff*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go(blendTime), 1000));
	}

	protected void Type2_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E38CD77L /*Type2_Go*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_040", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_In_Go(blendTime), 1000)));
	}

	protected void Type2_Rest_In_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF2FF785L /*Type2_Rest_In_Go*/);
		doAction(720668828L /*REST_WITH_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go1(blendTime), 1000));
	}

	protected void Type2_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6EC8E369L /*Type2_Go1*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_045", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Clear_Stuff(blendTime), 1000)));
	}

	protected void Type2_Clear_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6399288EL /*Type2_Clear_Stuff*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_GoBack(blendTime), 1000));
	}

	protected void Type2_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE038351L /*Type2_GoBack*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_053", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_In_GoBack(blendTime), 1000)));
	}

	protected void Type2_Rest_In_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57D37FB2L /*Type2_Rest_In_GoBack*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_GoBack1(blendTime), 1000));
	}

	protected void Type2_GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8EC900C4L /*Type2_GoBack1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ser_Extraction_Worker_Route_059", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_In_GoBack1(blendTime), 1000)));
	}

	protected void Type2_Rest_In_GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A3AD692L /*Type2_Rest_In_GoBack1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_FirstGo(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
