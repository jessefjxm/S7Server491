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
@IAIName("goblin_boss_servant")
public class Ai_goblin_boss_servant extends CreatureAI {
	public Ai_goblin_boss_servant(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		doAction(2858873921L /*SPAWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2858873921L /*SPAWN*/, blendTime, onDoActionEnd -> scheduleState(state -> ChaseOwner_Move(blendTime), 1000));
	}

	protected void ChaseOwner_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEEF90722L /*ChaseOwner_Move*/);
		if (isTargetLost()) {
			if (changeState(state -> ChaseOwner_Move(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 350) {
			if (changeState(state -> SearchBombPos(blendTime)))
				return;
		}
		doAction(2876463608L /*CHASE_PARENT*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_Move(blendTime), 250)));
	}

	protected void SearchBombPos(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEDB54517L /*SearchBombPos*/);
		doAction(798885138L /*WAIT_SEARCH_BOMPOS*/, blendTime, onDoActionEnd -> scheduleState(state -> SearchBombPos(blendTime), 10000));
	}

	protected void MoveBombPos(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFB30F3BL /*MoveBombPos*/);
		doAction(1049476387L /*MOVE_BOMBPOS*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> BombReady(blendTime), 2000)));
	}

	protected void BombReady(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD7D8C96BL /*BombReady*/);
		doAction(977209905L /*SELF_BOMB_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Bomb(blendTime), 2000));
	}

	protected void Bomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA67090F0L /*Bomb*/);
		doAction(1226525293L /*SELF_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
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
	public EAiHandlerResult HandleGoMoveBombPos(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MoveBombPos(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
