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
@IAIName("m0414_kamakenta")
public class Ai_m0414_kamakenta extends CreatureAI {
	public Ai_m0414_kamakenta(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, getVariable(0xC3B66543L /*AI_Stun_Time*/));
		setVariable(0x8AA7EEEBL /*_StunCount*/, 0);
		setVariable(0x87B27D4AL /*_Freezing_Time*/, getVariable(0x6F08D801L /*AI_Freezing_Time*/));
		if (getVariable(0x9AD1E354L /*AI_SummonStartAction*/) == 1) {
			if (changeState(state -> Summon_Start_Action(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Summon_Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE56F2FBBL /*Summon_Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500 + Rnd.get(-500,500)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, getVariable(0xF561B270L /*AI_RandomMove_Min*/), getVariable(0xEA5276A5L /*AI_RandomMove_Max*/), false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xA8B1D988L /*AI_RandomMove_CallCycleTime*/))));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x8AA7EEEBL /*_StunCount*/, 0);
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Return_B(blendTime), 1000)));
	}

	protected void Move_Return_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1CE83D51L /*Move_Return_B*/);
		if (getDistanceToSpawn() > 100) {
			doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		}
		if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
			if (changeState(state -> Weapon_In(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88C3A72EL /*Teleport*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3467964157L /*TELEPORT*/, blendTime, onDoActionEnd -> changeState(state -> Turn(blendTime)));
	}

	protected void Weapon_In(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626F781FL /*Weapon_In*/);
		doAction(1891792052L /*WEAPON_IN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6B3D2433L /*Turn*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4284768045L /*TURN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -90) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Turn(0.1)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 90) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Turn(0.1)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0xAE5899D3L /*AI_BT_Teleport*/) > 0 && target != null && getDistanceToTarget(target) > 500) {
			if(Rnd.getChance(getVariable(0xAE5899D3L /*AI_BT_Teleport*/))) {
				if (changeState(state -> Teleport(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.1)))
					return;
			}
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.1)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.1)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.1)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3(0.1)))
					return;
			}
		}
		if (getVariable(0xF847CAE4L /*AI_BT_Attack4*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x2C020EEAL /*AI_BT_Attack4_Distance*/)) {
			if(Rnd.getChance(getVariable(0xF847CAE4L /*AI_BT_Attack4*/))) {
				if (changeState(state -> Battle_Attack4(0.1)))
					return;
			}
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1(0.1)))
					return;
			}
		}
		if (getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2(0.1)))
					return;
			}
		}
		if (getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 200 && target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Move_Chaser(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.1)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Battle_Wait(0.1)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0xAE5899D3L /*AI_BT_Teleport*/) > 0) {
			if(Rnd.getChance(getVariable(0xAE5899D3L /*AI_BT_Teleport*/))) {
				if (changeState(state -> Teleport(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.1)))
					return;
			}
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_ChargeAttack2(0.1)))
					return;
			}
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_RangeAttack1(0.1)))
					return;
			}
		}
		if (getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_RangeAttack2(0.1)))
					return;
			}
		}
		if (getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/) > 0 && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/)) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_RangeAttack3(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 200 && target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Move_Chaser(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Battle_Wait(0.1)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		setVariable(0x8AA7EEEBL /*_StunCount*/, getVariable(0x8AA7EEEBL /*_StunCount*/) + 1);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		setVariable(0x8AA7EEEBL /*_StunCount*/, getVariable(0x8AA7EEEBL /*_StunCount*/) + 1);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), getVariable(0x2E9C3CCFL /*_Stun_Time*/)));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	protected void Damage_AirFloat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xFE54DA01L /*Damage_AirFloat*/);
		doAction(834965090L /*DAMAGE_AIRFLOAT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_AirSmash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xF3E1435DL /*Damage_AirSmash*/);
		doAction(3455519139L /*DAMAGE_AIRSMASH*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_DownSmash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xAD9D0DFL /*Damage_DownSmash*/);
		doAction(460682976L /*DAMAGE_DOWNSMASH*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Freeze(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D0DCCD1L /*Damage_Freeze*/);
		doAction(2514923161L /*DAMAGE_FREEZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Freeze_End(blendTime), 3000));
	}

	protected void Damage_Freeze_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDEC6952L /*Damage_Freeze_End*/);
		doAction(1366805764L /*DAMAGE_FREEZE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Damage_Release(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82D0AC8EL /*Damage_Release*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF05721A5L /*Battle_Attack4*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A7FFDBBL /*Battle_ChargeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3904990386L /*BATTLE_CHARGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6F4B95A3L /*Battle_ChargeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(435020623L /*BATTLE_CHARGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3788D09AL /*Battle_ChargeAttack3*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(770707470L /*BATTLE_CHARGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x395B66CCL /*Battle_RangeAttack3*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 1200) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, eventData[0]);
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAirFloat(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x6B3D2433L /*Turn*/ || getState() == 0x3A7FFDBBL /*Battle_ChargeAttack1*/ || getState() == 0x395B66CCL /*Battle_RangeAttack3*/) && getVariable(0x8AA7EEEBL /*_StunCount*/) < 3) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
