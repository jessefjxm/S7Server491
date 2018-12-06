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
@IAIName("party_leader_magoria")
public class Ai_party_leader_magoria extends CreatureAI {
	public Ai_party_leader_magoria(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		setVariable(0xAA320A99L /*_DiceCount*/, getRandom(100));
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 0 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> Summon_Tornado(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> Move_Random_Treasure(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> Summon_Tornado_Moving(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 90 && getVariable(0xAA320A99L /*_DiceCount*/) <= 100) {
			if (changeState(state -> Summon_Typhoon(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Summon_Tornado(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB79713C1L /*Summon_Tornado*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(1977618006L /*SUMMON_TORNADO*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Move_Random_Treasure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x58F2EE56L /*Move_Random_Treasure*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 100, 0, 1000, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Logic_Treasure(blendTime), 1000)));
	}

	protected void Summon_Logic_Treasure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5182994CL /*Summon_Logic_Treasure*/);
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> Summon_Treasure_1(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> Summon_Treasure_2(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> Summon_Treasure_3(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> Summon_Treasure_4(blendTime)))
				return;
		}
		changeState(state -> Summon_Logic_Treasure(blendTime));
	}

	protected void Summon_Treasure_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3EA16677L /*Summon_Treasure_1*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(933640681L /*SUMMON_TREASURE_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Treasure_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x91C52506L /*Summon_Treasure_2*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(1634643282L /*SUMMON_TREASURE_2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Treasure_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC5620EBAL /*Summon_Treasure_3*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(1245057038L /*SUMMON_TREASURE_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Treasure_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAA110F69L /*Summon_Treasure_4*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(2401405689L /*SUMMON_TREASURE_4*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Tornado_Moving(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xADBD7E0FL /*Summon_Tornado_Moving*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(3661792943L /*SUMMON_TORNADO_MOVING*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Typhoon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE3562826L /*Summon_Typhoon*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(3347368186L /*SUMMON_TYPHOON*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
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
