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
@IAIName("party_leader_ocean_boss3")
public class Ai_party_leader_ocean_boss3 extends CreatureAI {
	public Ai_party_leader_ocean_boss3(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x607B32B9L /*_BombCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Teleport_Logic(blendTime), 1000));
	}

	protected void Move_Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x24623D0L /*Move_Teleport_Logic*/);
		doTeleport(EAIMoveDestType.Random, 3000, 3000, 5000, 8000);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 1000));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		if(getCallCount() == 7) {
			if (changeState(state -> Attack_Bomb(blendTime)))
				return;
		}
		if (getVariable(0x607B32B9L /*_BombCount*/) >= 4) {
			if (changeState(state -> Wait3(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 1000));
	}

	protected void Attack_Bomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8CDC264BL /*Attack_Bomb*/);
		setVariable(0x607B32B9L /*_BombCount*/, getVariable(0x607B32B9L /*_BombCount*/) + 1);
		useSkill(40513, 1, true, EAIFindTargetType.Enemy, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 1800 && getTargetHp(object) > 0 && isTargetVehicle(object));
		useSkill(40436, 1, true, EAIFindTargetType.Player, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 1800 && getTargetHp(object) > 0);
		doAction(2362681632L /*ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Move_Teleport_Logic(blendTime)));
	}

	protected void Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75E9DA83L /*Wait3*/);
		if(getCallCount() == 7) {
			if (changeState(state -> Attack_Bomb2(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3(blendTime), 1000));
	}

	protected void Attack_Bomb2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2ECFF6EDL /*Attack_Bomb2*/);
		useSkill(40513, 1, true, EAIFindTargetType.Enemy, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 1800 && getTargetHp(object) > 0 && isTargetVehicle(object));
		useSkill(40436, 1, true, EAIFindTargetType.Player, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 1800 && getTargetHp(object) > 0);
		doAction(2362681632L /*ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
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
	public EAiHandlerResult Gotohell(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
