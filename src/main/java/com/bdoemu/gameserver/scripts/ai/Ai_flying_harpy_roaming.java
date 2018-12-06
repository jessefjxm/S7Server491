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
@IAIName("flying_harpy_roaming")
public class Ai_flying_harpy_roaming extends CreatureAI {
	public Ai_flying_harpy_roaming(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
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
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 3) {
			if (changeState(state -> Type3_Go(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 4) {
			if (changeState(state -> Type4_Go(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 5) {
			if (changeState(state -> Type5_Go(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 6) {
			if (changeState(state -> Type6_Go(blendTime)))
				return;
		}
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300 + Rnd.get(-300,300)));
	}

	protected void Type1_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x32EFC16BL /*Type1_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_002", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go1(blendTime), 1000)));
	}

	protected void Type1_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA574EF37L /*Type1_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_003", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go2(blendTime), 1000)));
	}

	protected void Type1_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4166300L /*Type1_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_006", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go(blendTime), 1000)));
	}

	protected void Type2_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E38CD77L /*Type2_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_014", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go1(blendTime), 1000)));
	}

	protected void Type2_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6EC8E369L /*Type2_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_019", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go2(blendTime), 1000)));
	}

	protected void Type2_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5DDDA157L /*Type2_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_022", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go3(blendTime), 1000)));
	}

	protected void Type2_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4974119L /*Type2_Go3*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_016", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go(blendTime), 1000)));
	}

	protected void Type3_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB51A9DBL /*Type3_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_041", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go1(blendTime), 1000)));
	}

	protected void Type3_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x327FB836L /*Type3_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_038", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go2(blendTime), 1000)));
	}

	protected void Type3_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x14A88AFDL /*Type3_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_028", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go3(blendTime), 1000)));
	}

	protected void Type3_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x964940E4L /*Type3_Go3*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_023", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go(blendTime), 1000)));
	}

	protected void Type4_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB2580A99L /*Type4_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_102", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go1(blendTime), 1000)));
	}

	protected void Type4_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x749A6DF3L /*Type4_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_090", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go2(blendTime), 1000)));
	}

	protected void Type4_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3A4B3185L /*Type4_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_103", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go3(blendTime), 1000)));
	}

	protected void Type4_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C9472F0L /*Type4_Go3*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_085", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go(blendTime), 1000)));
	}

	protected void Type5_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7B07709CL /*Type5_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_075", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Go1(blendTime), 1000)));
	}

	protected void Type5_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD0249134L /*Type5_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_065", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Go2(blendTime), 1000)));
	}

	protected void Type5_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5F0A673FL /*Type5_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_063", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Go3(blendTime), 1000)));
	}

	protected void Type5_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x92C61169L /*Type5_Go3*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_079", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Go(blendTime), 1000)));
	}

	protected void Type6_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAC80A34BL /*Type6_Go*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_049", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type6_Go1(blendTime), 1000)));
	}

	protected void Type6_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF0FA9B05L /*Type6_Go1*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_051", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type6_Go2(blendTime), 1000)));
	}

	protected void Type6_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2D39EB3EL /*Type6_Go2*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_043", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type6_Go3(blendTime), 1000)));
	}

	protected void Type6_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9FCF0A82L /*Type6_Go3*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gateway_Harpy_03_058", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type6_Go(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
