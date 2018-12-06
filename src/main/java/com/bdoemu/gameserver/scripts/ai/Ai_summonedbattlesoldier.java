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
@IAIName("summonedbattlesoldier")
public class Ai_summonedbattlesoldier extends CreatureAI {
	public Ai_summonedbattlesoldier(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 4);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 0);
		setVariable(0x72A1B315L /*_IsComplexRange*/, getVariable(0xB6870982L /*IsComplexRange*/));
		setVariable(0xADD3AE72L /*_characterType*/, getVariable(0x8819453EL /*IsRangerCharacter*/));
		setVariable(0xD13110BL /*_IsGuardCharacter*/, getVariable(0x1DD8339CL /*IsGuardCharacter*/));
		doAction(2621686016L /*INITIALIZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2621686016L /*INITIALIZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A34695AL /*Stance*/);
		if (getVariable(0xF06310B7L /*_stance*/) == 4) {
			if (changeState(state -> Stance_Protect_WeaponCheck(blendTime)))
				return;
		}
		changeState(state -> Stance(blendTime));
	}

	protected void Stance_Idle_WeaponCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x853C1D30L /*Stance_Idle_WeaponCheck*/);
		if (getVariable(0x79D09B4CL /*_IsWeaponOut*/) == 1) {
			if (changeState(state -> Stance_Idle_WeaponIn(blendTime)))
				return;
		}
		changeState(state -> Stance_Idle(blendTime));
	}

	protected void Stance_Idle_WeaponIn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D036626L /*Stance_Idle_WeaponIn*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 0);
		clearAggro(true);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 700));
	}

	protected void Stance_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9C20F410L /*Stance_Idle*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Idle_WeaponOut(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Idle_WeaponOut(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 1000));
	}

	protected void Stance_Idle_WeaponOut(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD94C829CL /*Stance_Idle_WeaponOut*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_BattleIdle(blendTime), 1000));
	}

	protected void Stance_BattleIdle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3439385EL /*Stance_BattleIdle*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stance_Idle_WeaponIn(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x4F3BBB4BL /*Idle_CheckOut_Distance*/)) {
			if (changeState(state -> Stance_Idle_WeaponIn(blendTime)))
				return;
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Idle_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Idle_Attack1(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Idle_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Idle_Attack1(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Stance_Idle_Attack(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_BattleIdle(blendTime), 1000));
	}

	protected void Stance_Idle_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAE949CCEL /*Stance_Idle_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Stance_BattleIdle(blendTime)));
	}

	protected void Stance_Idle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA6E08FF0L /*Stance_Idle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Stance_BattleIdle(blendTime)));
	}

	protected void Stance_Idle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3DCAB774L /*Stance_Idle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Stance_BattleIdle(blendTime)));
	}

	protected void Stance_Follow_WeaponCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF7AFACDBL /*Stance_Follow_WeaponCheck*/);
		if (getVariable(0x79D09B4CL /*_IsWeaponOut*/) == 1) {
			if (changeState(state -> Stance_Follow_WeaponIn(blendTime)))
				return;
		}
		changeState(state -> Stance_Follow(blendTime));
	}

	protected void Stance_Follow_WeaponIn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1CB5AF3L /*Stance_Follow_WeaponIn*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 0);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Follow(blendTime), 700));
	}

	protected void Stance_Follow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC2EA9387L /*Stance_Follow*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 150, 350, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Follow_Wait(blendTime), 500)));
	}

	protected void Stance_Follow_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xD8D35A2L /*Stance_Follow_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 200) {
			if (changeState(state -> Stance_Follow(blendTime)))
				return;
		}
		doAction(4284768045L /*TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Formation, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Follow_Wait(blendTime), 1000)));
	}

	protected void Stance_Patrol_WeaponCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3F56AA3AL /*Stance_Patrol_WeaponCheck*/);
		if (getVariable(0x79D09B4CL /*_IsWeaponOut*/) == 1) {
			if (changeState(state -> Stance_Patrol_WeaponIn(blendTime)))
				return;
		}
		changeState(state -> Stance_Patrol(blendTime));
	}

	protected void Stance_Patrol_WeaponIn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x25BEE55BL /*Stance_Patrol_WeaponIn*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 0);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Patrol(blendTime), 700));
	}

	protected void Stance_Patrol(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4BB607C1L /*Stance_Patrol*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Patrol_WeaponOut(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Patrol_WeaponOut(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Patrol(blendTime), 1000)));
	}

	protected void Stance_Patrol_WeaponOut(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB3132E69L /*Stance_Patrol_WeaponOut*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Patrol_Chase(blendTime), 1000));
	}

	protected void Stance_Patrol_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x7E9923C7L /*Stance_Patrol_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stance_Patrol_WeaponIn(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x4F3BBB4BL /*Idle_CheckOut_Distance*/)) {
			if (changeState(state -> Stance_Patrol_WeaponIn(blendTime)))
				return;
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Patrol_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Patrol_Attack1(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Patrol_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Patrol_Attack1(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Stance_Patrol_Attack(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Patrol_Chase(blendTime), 500)));
	}

	protected void Stance_Patrol_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF1D3CDDBL /*Stance_Patrol_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Patrol_Chase(blendTime)));
	}

	protected void Stance_Patrol_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x96F20560L /*Stance_Patrol_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Patrol_Chase(blendTime)));
	}

	protected void Stance_Patrol_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDCB1823DL /*Stance_Patrol_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Patrol_Chase(blendTime)));
	}

	protected void Stance_Protect_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8EFE3BAL /*Stance_Protect_Owner*/);
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 150, 350, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Protect_Wait(blendTime), 1000)));
	}

	protected void Stance_Protect_WeaponCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCBFF8C59L /*Stance_Protect_WeaponCheck*/);
		if (getVariable(0x79D09B4CL /*_IsWeaponOut*/) == 0) {
			if (changeState(state -> Stance_Protect_WeaponOut(blendTime)))
				return;
		}
		changeState(state -> Stance_Protect(blendTime));
	}

	protected void Stance_Protect_WeaponOut(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x852CCDF5L /*Stance_Protect_WeaponOut*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Protect(blendTime), 1000));
	}

	protected void Stance_Protect(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9C5AC17CL /*Stance_Protect*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Protect_Chase(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Protect_Chase(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 150, 350, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Protect_Wait(blendTime), 1000)));
	}

	protected void Stance_Protect_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x5397CE18L /*Stance_Protect_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 200) {
			if (changeState(state -> Stance_Protect(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Protect_Chase(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Stance_Protect_Chase(blendTime)))
				return;
		}
		doAction(3101627639L /*BATTLE_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Formation, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Protect_Wait(blendTime), 1000)));
	}

	protected void Stance_Protect_RetreatCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBB9EDA92L /*Stance_Protect_RetreatCheck*/);
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && getVariable(0xADD3AE72L /*_characterType*/) == 1) {
			if (changeState(state -> Stance_Protect_Retreat(blendTime)))
				return;
		}
		changeState(state -> Stance_Protect_Chase(blendTime));
	}

	protected void Stance_Protect_Retreat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x59B33110L /*Stance_Protect_Retreat*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x8F454457L /*Attack_Distance*/)) {
			if (changeState(state -> Stance_Protect_Attack(blendTime)))
				return;
		}
		if(getCallCount() == 3) {
			if (changeState(state -> Stance_Protect_RetreatCheck(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Protect_Retreat(blendTime), 1000)));
	}

	protected void Stance_Protect_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xA1AC454AL /*Stance_Protect_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Protect_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Protect_Attack1(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_Protect_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_Protect_Attack1(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Stance_Protect_Attack(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stance_Protect(blendTime)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Stance_Protect_Wait(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Protect_Chase(blendTime), 100)));
	}

	protected void Stance_Protect_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8A88F7B3L /*Stance_Protect_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Protect_RetreatCheck(blendTime)));
	}

	protected void Stance_Protect_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x271F61A4L /*Stance_Protect_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Protect_RetreatCheck(blendTime)));
	}

	protected void Stance_Protect_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFEBBB49FL /*Stance_Protect_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Stance_Protect_RetreatCheck(blendTime)));
	}

	protected void Stance_GainForce_WeaponCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5108546L /*Stance_GainForce_WeaponCheck*/);
		if (getVariable(0x79D09B4CL /*_IsWeaponOut*/) == 0) {
			if (changeState(state -> Stance_GainForce_WeaponOut(blendTime)))
				return;
		}
		changeState(state -> Stance_GainForce(blendTime));
	}

	protected void Stance_GainForce_RetreatCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x51B3E128L /*Stance_GainForce_RetreatCheck*/);
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && getVariable(0xADD3AE72L /*_characterType*/) == 1) {
			if (changeState(state -> Stance_GainForce_Retreat(blendTime)))
				return;
		}
		changeState(state -> Stance_Protect_Chase(blendTime));
	}

	protected void Stance_GainForce_Retreat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x63B1D617L /*Stance_GainForce_Retreat*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x8F454457L /*Attack_Distance*/)) {
			if (changeState(state -> Stance_GainForce_Attack(blendTime)))
				return;
		}
		if(getCallCount() == 3) {
			if (changeState(state -> Stance_GainForce_RetreatCheck(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_GainForce_Retreat(blendTime), 1000)));
	}

	protected void Stance_GainForce_WeaponOut(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x136EE6E5L /*Stance_GainForce_WeaponOut*/);
		setVariable(0x79D09B4CL /*_IsWeaponOut*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_GainForce(blendTime), 1000));
	}

	protected void Stance_GainForce(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x64826EC2L /*Stance_GainForce*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance_GainForce_NoTarget_Wait(blendTime)))
				return;
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_GainForce_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 1 && target != null && getDistanceToTarget(target) < getVariable(0x2CB61A4BL /*RangeAttack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_GainForce_Attack1(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Stance_GainForce_Attack2(blendTime)))
					return;
			}
		}
		if (getVariable(0x72A1B315L /*_IsComplexRange*/) == 0 && target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Stance_GainForce_Attack1(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x8F454457L /*Attack_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Stance_GainForce_Attack(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stance_Protect(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_GainForce(blendTime), 100)));
	}

	protected void Stance_GainForce_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B0F1C9CL /*Stance_GainForce_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance_GainForce_NoTarget_Wait(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Stance_GainForce_RetreatCheck(blendTime)));
	}

	protected void Stance_GainForce_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF0BDA58AL /*Stance_GainForce_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance_GainForce_NoTarget_Wait(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Stance_GainForce_RetreatCheck(blendTime)));
	}

	protected void Stance_GainForce_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD8D13EA1L /*Stance_GainForce_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance_GainForce_NoTarget_Wait(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Stance_GainForce_RetreatCheck(blendTime)));
	}

	protected void Stance_GainForce_NoTarget_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x16EE4040L /*Stance_GainForce_NoTarget_Wait*/);
		setVariable(0xF06310B7L /*_stance*/, 4);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3891BF54L /*Damage_StandUp*/);
		doAction(927041621L /*DAMAGE_STANDUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 2000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_StandUp(blendTime), 2000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 5000));
	}

	protected void Damage_GuardCrash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x560CBAFBL /*Damage_GuardCrash*/);
		doAction(1289617018L /*GUARD_CRASH*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 2000));
	}

	@Override
	public EAiHandlerResult HandleStance1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xF06310B7L /*_stance*/, 1);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xF06310B7L /*_stance*/, 2);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xF06310B7L /*_stance*/, 3);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xF06310B7L /*_stance*/, 4);
		if (changeState(state -> Stance_Protect_Owner(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xF06310B7L /*_stance*/, 5);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/)) && target != null && getTargetHp(target) > 0 && getState() == 0x4BB607C1L /*Stance_Patrol*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Stance_Patrol_WeaponOut(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0x249A134DL /*Idle_CheckIn_Distance*/)) && target != null && getTargetHp(target) > 0 && (getState() == 0x9C5AC17CL /*Stance_Protect*/ || getState() == 0x5397CE18L /*Stance_Protect_Wait*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Stance_Protect_Chase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x4BB607C1L /*Stance_Patrol*/) {
			if (changeState(state -> Stance_Patrol_WeaponOut(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x9C5AC17CL /*Stance_Protect*/) {
			if (changeState(state -> Stance_Protect_Chase(0.1)))
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
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.1)))
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
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleGuardCrash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD13110BL /*_IsGuardCharacter*/) == 1) {
			if (changeState(state -> Damage_GuardCrash(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		setVariable(0xF06310B7L /*_stance*/, 2);
		return EAiHandlerResult.BYPASS;
	}
}
