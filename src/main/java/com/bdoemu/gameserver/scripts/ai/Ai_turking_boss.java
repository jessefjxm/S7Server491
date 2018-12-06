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
@IAIName("turking_boss")
public class Ai_turking_boss extends CreatureAI {
	public Ai_turking_boss(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x6E382F32L /*_SummonAttackCount*/, 0);
		setVariable(0x418BA373L /*_BreathAttackCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckSpawn_Logic(blendTime), 1000));
	}

	protected void CheckSpawn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2803EDBL /*CheckSpawn_Logic*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 27139)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		changeState(state -> Notifier_Logic(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> LostTarget_Wait(blendTime));
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_TRUKING_SPAWN");
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
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1000, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Return_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x84F18493L /*Return_Logic*/);
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 100) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 50) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBF84E10BL /*LostTarget_Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_TRUKING_TIMEOUT");
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 40 && getVariable(0xAAB58E9BL /*_Roar*/) == 0) {
			if (changeState(state -> Roar(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
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
		if (getVariable(0x3F487035L /*_HP*/) < 68 && getVariable(0x6E382F32L /*_SummonAttackCount*/) == 0) {
			if (changeState(state -> Attack_Summon(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 45 && getVariable(0x6E382F32L /*_SummonAttackCount*/) == 1) {
			if (changeState(state -> Attack_Summon(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 11 && getVariable(0x6E382F32L /*_SummonAttackCount*/) == 2) {
			if (changeState(state -> Attack_Summon(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= -40) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_90_Left(blendTime)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= 40) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_90_Right(blendTime)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) < 40 && target != null && getAngleToTarget(target) > -40 && target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal_1(blendTime)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) < 40 && target != null && getAngleToTarget(target) > -40 && target != null && getDistanceToTarget(target) < 400 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal_2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal_3(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= 400 && target != null && getDistanceToTarget(target) < 700 && target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45) {
			if (changeState(state -> Attack_Chase(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 700 && target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45) {
			if (changeState(state -> Attack_Flying(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Turn_90_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD69EBA6EL /*Battle_Turn_90_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Battle_Turn_90_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5DD6B55EL /*Battle_Turn_90_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Battle_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF94905AEL /*Battle_Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Normal_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB0F6C1F5L /*Attack_Normal_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Normal_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4BCE7F4DL /*Attack_Normal_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Normal_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7A81B572L /*Attack_Normal_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3000640225L /*AROUND_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9527EC79L /*Attack_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2395976516L /*CHASE_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Flying(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDAEAE32EL /*Attack_Flying*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(4081526825L /*FLYING_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
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
		doAction(4150556420L /*ATTACK_BREATH_START*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
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
		doAction(4150556420L /*ATTACK_BREATH_START*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x6E382F32L /*_SummonAttackCount*/, getVariable(0x6E382F32L /*_SummonAttackCount*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(4064546250L /*ATTACK_SUMMON_START*/, blendTime, onDoActionEnd -> changeState(state -> Change_Target(blendTime)));
	}

	protected void Change_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDAFDA67CL /*Change_Target*/);
		if(Rnd.getChance(20)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
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
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stun_Ing(blendTime), 10000));
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
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stun_Ing(blendTime), 1000));
	}

	protected void Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xED1B5E21L /*Stun_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 10000));
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
		setBehavior(EAIBehavior.idle);
		setState(0x60186BFFL /*Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xAAB58E9BL /*_Roar*/, 1);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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
