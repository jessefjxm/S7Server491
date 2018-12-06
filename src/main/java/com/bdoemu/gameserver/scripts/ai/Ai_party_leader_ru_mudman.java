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
@IAIName("party_leader_ru_mudman")
public class Ai_party_leader_ru_mudman extends CreatureAI {
	public Ai_party_leader_ru_mudman(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_SummonPoint1(blendTime), 1000));
	}

	protected void Move_Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24623D0L /*Move_Teleport_Logic*/);
		setVariable(0xAA320A99L /*_DiceCount*/, getRandom(100));
		if (getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> Move_SummonPoint1(blendTime)))
				return;
		}
		if (getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 100) {
			if (changeState(state -> Move_SummonPoint2(blendTime)))
				return;
		}
		changeState(state -> Move_SummonPoint1(blendTime));
	}

	protected void Move_SummonPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x24ECC0D2L /*Move_SummonPoint1*/);
		doTeleportToWaypoint("ru_mudman_boss_001", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Move_SummonPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEE91C08CL /*Move_SummonPoint2*/);
		doTeleportToWaypoint("ru_mudman_boss_002", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _PathEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _TimeOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _Deadstate(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
