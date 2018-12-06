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
@IAIName("ent_test")
public class Ai_ent_test extends CreatureAI {
	public Ai_ent_test(Creature actor, Map<Long, Integer> aiVariables) {
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
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 8) {
			if (changeState(state -> Ent_01_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 9) {
			if (changeState(state -> Ent_02_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 10) {
			if (changeState(state -> Ent_03_Go(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Ent_01_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCBE56B1BL /*Ent_01_Go*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_01_004", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_01_Go1(blendTime), 1000)));
	}

	protected void Ent_01_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC3B5ADDL /*Ent_01_Go1*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_01_008", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_01_Go2(blendTime), 1000)));
	}

	protected void Ent_01_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3F93EE3L /*Ent_01_Go2*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_01_011", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_01_Go3(blendTime), 1000)));
	}

	protected void Ent_01_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x60B4DF7L /*Ent_01_Go3*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_01_003", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_01_Go(blendTime), 1000)));
	}

	protected void Ent_02_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5C8DB13FL /*Ent_02_Go*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_02_001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_02_Go1(blendTime), 1000)));
	}

	protected void Ent_02_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF25B6597L /*Ent_02_Go1*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_02_008", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_02_Go2(blendTime), 1000)));
	}

	protected void Ent_02_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x401CD37DL /*Ent_02_Go2*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_02_017", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_02_Go3(blendTime), 1000)));
	}

	protected void Ent_02_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9DCE4F0EL /*Ent_02_Go3*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_02_025", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_02_Go(blendTime), 1000)));
	}

	protected void Ent_03_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x90A3086DL /*Ent_03_Go*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_03_007", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_03_Go1(blendTime), 1000)));
	}

	protected void Ent_03_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFD6B7DFBL /*Ent_03_Go1*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_03_016", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_03_Go2(blendTime), 1000)));
	}

	protected void Ent_03_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x48A718CEL /*Ent_03_Go2*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_03_024", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_03_Go3(blendTime), 1000)));
	}

	protected void Ent_03_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6B086859L /*Ent_03_Go3*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Ent_Walk_03_032", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ent_03_Go(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
