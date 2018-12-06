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
@IAIName("w119_stonezaco")
public class Ai_w119_stonezaco extends CreatureAI {
	public Ai_w119_stonezaco(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		doAction(927442757L /*WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait1(blendTime), 1000));
	}

	protected void Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17E06D10L /*Wait1*/);
		doAction(927442757L /*WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait1(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal_3(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Chaser(blendTime)))
					return;
			}
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
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
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal_3(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Chaser(blendTime)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Attack_Normal_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB0F6C1F5L /*Attack_Normal_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4BCE7F4DL /*Attack_Normal_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7A81B572L /*Attack_Normal_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(1120120107L /*ATTACK_NORMAL_3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
