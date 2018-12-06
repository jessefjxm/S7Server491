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
@IAIName("instance_guard_spear")
public class Ai_instance_guard_spear extends CreatureAI {
	public Ai_instance_guard_spear(Creature actor, Map<Long, Integer> aiVariables) {
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
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getTargetCharacterKey(object) != 60134 && getTargetCharacterKey(object) != 60135 && getTargetCharacterKey(object) != 60136 && getTargetCharacterKey(object) != 60137 && getTargetCharacterKey(object) != 60140 && getTargetCharacterKey(object) != 60141 && getTargetCharacterKey(object) != 60142 && getTargetCharacterKey(object) != 60143 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 800 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && getDistanceToTarget(target) > 250 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Attack_2(0.3)))
					return;
			}
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Attack_3(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_Wait(blendTime), 1500));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getTargetCharacterKey(object) != 60134 && getTargetCharacterKey(object) != 60135 && getTargetCharacterKey(object) != 60136 && getTargetCharacterKey(object) != 60137 && getTargetCharacterKey(object) != 60140 && getTargetCharacterKey(object) != 60141 && getTargetCharacterKey(object) != 60142 && getTargetCharacterKey(object) != 60143 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 800 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Return_Wait(blendTime), 3000)));
	}

	protected void Move_Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x47391F8FL /*Move_Return_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 1200) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Attack_2(0.3)))
					return;
			}
		}
		if (target != null && getTargetCharacterKey(target) != 60134 && target != null && getTargetCharacterKey(target) != 60135 && target != null && getTargetCharacterKey(target) != 60136 && target != null && getTargetCharacterKey(target) != 60137 && target != null && getTargetCharacterKey(target) != 60140 && target != null && getTargetCharacterKey(target) != 60141 && target != null && getTargetCharacterKey(target) != 60142 && target != null && getTargetCharacterKey(target) != 60143 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 250) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Attack_3(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Battle_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEECD0FB6L /*Battle_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB4271F4L /*Battle_Attack_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92CAF548L /*Battle_Attack_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_StandUp(blendTime), 3000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_StandUp(blendTime), 3000));
	}

	protected void Damage_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3891BF54L /*Damage_StandUp*/);
		doAction(927041621L /*DAMAGE_STANDUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Release(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82D0AC8EL /*Damage_Release*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 20000));
	}

	protected void Home_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCDB8C53EL /*Home_Die*/);
		doAction(2083706740L /*HOME_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Home_Die(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Release(0.3)))
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
	public EAiHandlerResult HandleAtMorning(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerAtSpawnEndTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
