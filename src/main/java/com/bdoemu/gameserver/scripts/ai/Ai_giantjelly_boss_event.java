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
@IAIName("giantjelly_boss_event")
public class Ai_giantjelly_boss_event extends CreatureAI {
	public Ai_giantjelly_boss_event(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xAAB58E9BL /*_Roar*/, 0);
		setVariable(0x63E6470L /*_HitCount*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xC6C5BEFBL /*_QuakeAttackCount*/, 0);
		setVariable(0x418BA373L /*_BreathAttackCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Notifier_Logic(blendTime), 1000));
	}

	protected void CheckSpawn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2803EDBL /*CheckSpawn_Logic*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 23001)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		changeState(state -> Notifier_Logic(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> LostTarget(blendTime));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF06CDECAL /*LostTarget*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		changeState(state -> LostTarget_Wait(blendTime));
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Kzarka, "GAME", "LUA_EVENT_CZAKA_SQAWN");
		changeState(state -> Wait(blendTime));
	}

	protected void Start_Action_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5A1E79F6L /*Start_Action_Wait*/);
		if(getCallCount() == 1800) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void LostTarget_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBF84E10BL /*LostTarget_Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> LostTarget_Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Kzarka, "GAME", "LUA_CZAKA_TIMEOUT");
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Battle_Turn_45_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD2F3313L /*Battle_Turn_45_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4293593613L /*BATTLE_TURN_45_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_45_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC087FCE1L /*Battle_Turn_45_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3215447898L /*BATTLE_TURN_45_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_90_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD69EBA6EL /*Battle_Turn_90_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2213227866L /*BATTLE_TURN_90_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_90_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5DD6B55EL /*Battle_Turn_90_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(69025871L /*BATTLE_TURN_90_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_180_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD0A10B57L /*Battle_Turn_180_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(991936547L /*BATTLE_TURN_180_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_180_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC84FD670L /*Battle_Turn_180_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1915812192L /*BATTLE_TURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getDistanceToTarget(target) > 6000) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 40 && getVariable(0xAAB58E9BL /*_Roar*/) == 0) {
			if (changeState(state -> Roar(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 75 && getVariable(0x418BA373L /*_BreathAttackCount*/) == 0) {
			if (changeState(state -> Attack_Breath(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0x418BA373L /*_BreathAttackCount*/) == 1) {
			if (changeState(state -> Attack_Breath(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 30 && getVariable(0x418BA373L /*_BreathAttackCount*/) == 2) {
			if (changeState(state -> Attack_Breath(blendTime)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) > 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> AttackCount_Breath(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 3800 && getVariable(0x3F487035L /*_HP*/) < 68 && getVariable(0xC6C5BEFBL /*_QuakeAttackCount*/) == 0) {
			if (changeState(state -> Attack_Quake(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 3800 && getVariable(0x3F487035L /*_HP*/) < 45 && getVariable(0xC6C5BEFBL /*_QuakeAttackCount*/) == 1) {
			if (changeState(state -> Attack_Quake(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 3800 && getVariable(0x3F487035L /*_HP*/) < 11 && getVariable(0xC6C5BEFBL /*_QuakeAttackCount*/) == 2) {
			if (changeState(state -> Attack_Quake(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 3800 && getVariable(0x4C7FECF6L /*AttackCount_Quake*/) > 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Quake(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getAngleToTarget(target) <= -35) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_90_Left(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getAngleToTarget(target) >= 35) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_90_Right(blendTime)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -110 && target != null && getAngleToTarget(target) >= -179) {
			if (changeState(state -> Battle_Turn_90_Left(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 110 && target != null && getAngleToTarget(target) <= 179) {
			if (changeState(state -> Battle_Turn_90_Right(blendTime)))
				return;
		}
		if (getVariable(0xAAB58E9BL /*_Roar*/) == 0 && target != null && getAngleToTarget(target) <= -65) {
			if (changeState(state -> Battle_Turn_90_Left(blendTime)))
				return;
		}
		if (getVariable(0xAAB58E9BL /*_Roar*/) == 0 && target != null && getAngleToTarget(target) >= 65) {
			if (changeState(state -> Battle_Turn_90_Right(blendTime)))
				return;
		}
		if (getVariable(0xAAB58E9BL /*_Roar*/) != 0 && target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Battle_Turn_45_Left(blendTime)))
				return;
		}
		if (getVariable(0xAAB58E9BL /*_Roar*/) != 0 && target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Battle_Turn_45_Right(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getAngleToTarget(target) >= -30 && target != null && getDistanceToTarget(target) < 1300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_R(blendTime)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getAngleToTarget(target) >= -30 && target != null && getDistanceToTarget(target) < 1300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_L(blendTime)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 50 && target != null && getAngleToTarget(target) >= -50 && target != null && getDistanceToTarget(target) < 1300) {
			if (changeState(state -> Attack_Normal_L(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 50 && target != null && getAngleToTarget(target) >= -50 && target != null && getDistanceToTarget(target) < 1300 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Attack_Normal_R(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1300 && target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45) {
			if (changeState(state -> Attack_FireBall(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Quake(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x443209EAL /*Attack_Quake*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleOrderStone(getActor(), null));
		setVariable(0xC6C5BEFBL /*_QuakeAttackCount*/, getVariable(0xC6C5BEFBL /*_QuakeAttackCount*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(1879632987L /*ATTACK_QUAKE*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void AttackCount_Quake(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4C7FECF6L /*AttackCount_Quake*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleOrderStone(getActor(), null));
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(1879632987L /*ATTACK_QUAKE*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Turn_90_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF5FEFB53L /*Attack_Turn_90_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(372137508L /*ATTACK_TURN_90_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Turn_90_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x69D00588L /*Attack_Turn_90_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1664743606L /*ATTACK_TURN_90_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Normal_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x53A7FF8EL /*Attack_Normal_L*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1156785166L /*ATTACK_NORMAL_L*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Normal_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE1457EBL /*Attack_Normal_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(677153502L /*ATTACK_NORMAL_R*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Stamp_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD757DFDCL /*Attack_Stamp_L*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2022837284L /*ATTACK_STAMP_L*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Stamp_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6AB4A774L /*Attack_Stamp_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2806262256L /*ATTACK_STAMP_R*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D9470D7L /*Attack_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x418BA373L /*_BreathAttackCount*/, getVariable(0x418BA373L /*_BreathAttackCount*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(3779359920L /*ATTACK_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void AttackCount_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7AEF5BACL /*AttackCount_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(3779359920L /*ATTACK_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_FireBall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAE61A5F0L /*Attack_FireBall*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(553184894L /*ATTACK_FIREBALL*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Change_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDAFDA67CL /*Change_Target*/);
		if(Rnd.getChance(20)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEB4FEC02L /*Stun*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x63E6470L /*_HitCount*/, 0);
		doAction(3164014989L /*STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stun_Ing(blendTime), 10000));
	}

	protected void Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6EF239DBL /*Stun_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Stun_End(0.3)))
				return;
		}
		doAction(3164014989L /*STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stun_Ing(blendTime), 1000));
	}

	protected void Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xED1B5E21L /*Stun_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3164014989L /*STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 10000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleOrderDie(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60186BFFL /*Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xAAB58E9BL /*_Roar*/, 1);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Catch_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA0478891L /*Attack_Catch_R*/);
		doAction(2284421478L /*ATTACK_CATCH_R*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Catch_Ing_R(blendTime), 2100));
	}

	protected void Attack_Catch_Ing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A4314A7L /*Attack_Catch_Ing_R*/);
		doAction(3348477955L /*ATTACK_CATCH_ING_R*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Catch_Go_R(blendTime), 700));
	}

	protected void Attack_Catch_Go_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x600E75B5L /*Attack_Catch_Go_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2717450505L /*ATTACK_CATCH_GO_R*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Catch_Throw_R(blendTime)));
	}

	protected void Attack_Catch_Throw_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFAC91454L /*Attack_Catch_Throw_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3219138341L /*ATTACK_CATCH_THROW_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
		return EAiHandlerResult.BYPASS;
	}
}
