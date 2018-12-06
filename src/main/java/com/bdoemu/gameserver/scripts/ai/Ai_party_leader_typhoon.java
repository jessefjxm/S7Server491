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
@IAIName("party_leader_typhoon")
public class Ai_party_leader_typhoon extends CreatureAI {
	public Ai_party_leader_typhoon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(2235988577L /*START_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 1000));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		setVariable(0xAA320A99L /*_DiceCount*/, getRandom(100));
		if (getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> Typhoon_SummonPoint1(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> Typhoon_SummonPoint2(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> Typhoon_SummonPoint3(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> Typhoon_SummonPoint4(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> Typhoon_SummonPoint5(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> Typhoon_SummonPoint6(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> Typhoon_SummonPoint7(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> Typhoon_SummonPoint8(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> Typhoon_SummonPoint9(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 90) {
			if (changeState(state -> Typhoon_SummonPoint10(blendTime)))
				return;
		}
		changeState(state -> Typhoon_SummonPoint1(blendTime));
	}

	protected void Typhoon_SummonPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D77CCF0L /*Typhoon_SummonPoint1*/);
		doTeleportToWaypoint("typhoon_001", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x40228945L /*Typhoon_SummonPoint2*/);
		doTeleportToWaypoint("typhoon_002", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9790B874L /*Typhoon_SummonPoint3*/);
		doTeleportToWaypoint("typhoon_003", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7E49C18CL /*Typhoon_SummonPoint4*/);
		doTeleportToWaypoint("typhoon_004", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBE1A7ABDL /*Typhoon_SummonPoint5*/);
		doTeleportToWaypoint("typhoon_005", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD572F956L /*Typhoon_SummonPoint6*/);
		doTeleportToWaypoint("typhoon_006", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x76DE78C5L /*Typhoon_SummonPoint7*/);
		doTeleportToWaypoint("typhoon_007", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1FF87A09L /*Typhoon_SummonPoint8*/);
		doTeleportToWaypoint("typhoon_008", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBD365AE3L /*Typhoon_SummonPoint9*/);
		doTeleportToWaypoint("typhoon_009", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Typhoon_SummonPoint10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5CB9CAA9L /*Typhoon_SummonPoint10*/);
		doTeleportToWaypoint("typhoon_010", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x866C7489L /*Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
