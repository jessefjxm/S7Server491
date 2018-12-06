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
@IAIName("5")
public class Ai_5 extends CreatureAI {
	public Ai_5(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Side_Random1(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Side_Random2(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Move_Side_Random1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x85D8E11DL /*Move_Side_Random1*/);
		doAction(1472991859L /*MOVE_SIDEWALK_TEST1*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 300, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Move_Side_Random2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFD8E7794L /*Move_Side_Random2*/);
		doAction(4118391456L /*MOVE_SIDEWALK_TEST2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 300, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 500).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x2EE4701L /*ChaseDistance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Attack(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 500).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Attack(blendTime)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Battle_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEECD0FB6L /*Battle_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 500).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 500).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _helpme(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvade(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleGuardCrash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
