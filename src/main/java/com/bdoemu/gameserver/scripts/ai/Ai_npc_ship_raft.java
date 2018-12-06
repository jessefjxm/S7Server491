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
@IAIName("npc_ship_raft")
public class Ai_npc_ship_raft extends CreatureAI {
	public Ai_npc_ship_raft(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300 + Rnd.get(-300,300)));
	}

	protected void Type1_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x32EFC16BL /*Type1_Go*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_01_Stop01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Rest_01(blendTime), 1000)));
	}

	protected void Type1_Rest_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x370DC8DBL /*Type1_Rest_01*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_Go1(blendTime), 10000));
	}

	protected void Type1_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA574EF37L /*Type1_Go1*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_01_010", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Rest_02(blendTime), 1000)));
	}

	protected void Type1_Rest_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF8898898L /*Type1_Rest_02*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_Go(blendTime), 10000));
	}

	protected void Type2_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E38CD77L /*Type2_Go*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_Stop01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_01(blendTime), 1000)));
	}

	protected void Type2_Rest_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBF693158L /*Type2_Rest_01*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go1(blendTime), 10000));
	}

	protected void Type2_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6EC8E369L /*Type2_Go1*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_008", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_02(blendTime), 1000)));
	}

	protected void Type2_Rest_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23B7FFD0L /*Type2_Rest_02*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go2(blendTime), 10000));
	}

	protected void Type2_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5DDDA157L /*Type2_Go2*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_019", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_03(blendTime), 1000)));
	}

	protected void Type2_Rest_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB61A67EFL /*Type2_Rest_03*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go3(blendTime), 10000));
	}

	protected void Type2_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4974119L /*Type2_Go3*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_024", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_04(blendTime), 1000)));
	}

	protected void Type2_Rest_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFFD11CE2L /*Type2_Rest_04*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go4(blendTime), 10000));
	}

	protected void Type2_Go4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7D6364ABL /*Type2_Go4*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_027", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_05(blendTime), 1000)));
	}

	protected void Type2_Rest_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x949B5ECDL /*Type2_Rest_05*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go5(blendTime), 10000));
	}

	protected void Type2_Go5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA276A665L /*Type2_Go5*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Raft_Loopline_02_030", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Rest_06(blendTime), 1000)));
	}

	protected void Type2_Rest_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD5974017L /*Type2_Rest_06*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
