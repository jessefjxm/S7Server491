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
@IAIName("trollsiege")
public class Ai_trollsiege extends CreatureAI {
	public Ai_trollsiege(Creature actor, Map<Long, Integer> aiVariables) {
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
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack1(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack2(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x566D5E96L /*Attack1*/);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CF46EC2L /*Attack2*/);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0x433E1948L /*Fall*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> revive(10, () -> {
			return false;
		}, onExit -> changeState(state -> Fall_Start(blendTime))));
	}

	protected void Fall_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC60041EDL /*Fall_Start*/);
		doAction(4053489827L /*DIE_STR*/, blendTime, onDoActionEnd -> changeState(state -> Fall_Ing(blendTime)));
	}

	protected void Falling_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x258C1DB6L /*Falling_Wait*/);
		doAction(3193302180L /*DIE_FALLING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Fall_Ing(blendTime)));
	}

	protected void Fall_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5CC3C50L /*Fall_Ing*/);
		doAction(1326503045L /*DIE_FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 1, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Die(blendTime), 1000)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2337863545L /*DIE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport(blendTime), 5000));
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88C3A72EL /*Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1000, 2000);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Fall(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
