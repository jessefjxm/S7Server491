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
@IAIName("ogre_boss_big_new")
public class Ai_ogre_boss_big_new extends CreatureAI {
	public Ai_ogre_boss_big_new(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x5749908DL /*_JumpCount*/, 3);
		setVariable(0x357581C9L /*_RangeCount*/, 0);
		setVariable(0x8620C5A4L /*_GeneralLimitCount*/, 2);
		setVariable(0x5CE1B16EL /*_GeneralCount*/, 0);
		setVariable(0x256EC6C5L /*_isInitPhase1*/, 0);
		setVariable(0x3ACED901L /*_isInitPhase2*/, 0);
		setVariable(0x48843447L /*_JumpNumber*/, 0);
		setVariable(0x58BE11BBL /*_PowerAttackCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 5000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (getSelfCombinePoint() == 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 100));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x256EC6C5L /*_isInitPhase1*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 99) {
			if (changeState(state -> Phase_1(blendTime)))
				return;
		}
		changeState(state -> Battle_State(blendTime));
	}

	protected void Phase_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x964234B2L /*Phase_1*/);
		setVariable(0x256EC6C5L /*_isInitPhase1*/, 1);
		if (changeState(state -> RoarAttack(blendTime)))
			return;
		changeState(state -> Battle_State(blendTime));
	}

	protected void PowerAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCFFF3EFCL /*PowerAttack*/);
		doAction(914900684L /*POWER_ATTACK_A*/, blendTime, onDoActionEnd -> changeState(state -> PowerAttack(blendTime)));
	}

	protected void CountPowerAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x922F0D41L /*CountPowerAttack*/);
		setVariable(0x58BE11BBL /*_PowerAttackCount*/, getVariable(0x58BE11BBL /*_PowerAttackCount*/) + 1);
		if (getVariable(0x58BE11BBL /*_PowerAttackCount*/) < 3) {
			if (changeState(state -> PowerAttack(blendTime)))
				return;
		}
		changeState(state -> Battle_State(blendTime));
	}

	protected void RoarAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB7A9F18CL /*RoarAttack*/);
		doAction(1650662116L /*ROAR_ATTACK_START*/, blendTime, onDoActionEnd -> changeState(state -> RoarAttack(blendTime)));
	}

	protected void Battle_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD320D983L /*Battle_State*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() == 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 75 && getVariable(0x5CE1B16EL /*_GeneralCount*/) >= 15 && getVariable(0x8620C5A4L /*_GeneralLimitCount*/) >= 2) {
			if (changeState(state -> Attack_General(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0x5CE1B16EL /*_GeneralCount*/) >= 15 && getVariable(0x8620C5A4L /*_GeneralLimitCount*/) >= 1) {
			if (changeState(state -> Attack_General(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 600) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Chaser(0.3)))
					return;
			}
		}
		if (getSelfCombinePoint() == 13 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Move_Chaser_FL(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(55)) {
				if (changeState(state -> Attack_Kick2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1500 && target != null && getDistanceToTarget(target) >= 600 && getVariable(0x5749908DL /*_JumpCount*/) >= 25) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1500 && target != null && getDistanceToTarget(target) >= 600 && getVariable(0x357581C9L /*_RangeCount*/) >= 15) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Range(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 700 && getVariable(0x3F487035L /*_HP*/) < 30 && getVariable(0x5CE1B16EL /*_GeneralCount*/) >= 15) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -35 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Turn_L(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 35 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Turn_R(0.3)))
					return;
			}
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Combo(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Stamp_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Stamp_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Attack_Swing_R(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() == 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() == 13) {
			if (changeState(state -> Move_Chaser_FL(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> HighSpeed_Chaser(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Move_Chaser_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2E35C062L /*Move_Chaser_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() == 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2731973574L /*MOVE_WALK_FL*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Move_Chaser_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x427B4970L /*Move_Chaser_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> HighSpeed_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2144563380L /*MOVE_WALK_RL*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() == 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() == 13) {
			if (changeState(state -> Move_Chaser_FL(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> HighSpeed_Chaser_Stop(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1500 && getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2(0.3)))
					return;
			}
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD4BB14E7L /*HighSpeed_Chaser_Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3949145239L /*BATTLE_RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_BackStep(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x257A4497L /*Move_BackStep*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(1984320902L /*MOVE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_BackStep(blendTime), 100)));
	}

	protected void Attack_Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5404099FL /*Attack_Turn_L*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(748625070L /*ATTACK_TURN_L*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E7DE5BDL /*Attack_Turn_R*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(3533086974L /*ATTACK_TURN_R*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Swing_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1E76385FL /*Attack_Swing_L*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(3548371962L /*ATTACK_SWING_L*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Swing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x24DBCBF3L /*Attack_Swing_R*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(2462351423L /*ATTACK_SWING_R*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Stamp_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD757DFDCL /*Attack_Stamp_L*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(630668866L /*ATTACK_STAMP_LA*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Stamp_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6AB4A774L /*Attack_Stamp_R*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(2378483406L /*ATTACK_STAMP_RA*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Stamp_LK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB311A72BL /*Attack_Stamp_LK*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(2700809327L /*ATTACK_STAMP_LK*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Stamp_RK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE25D5BE2L /*Attack_Stamp_RK*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(50284844L /*ATTACK_STAMP_RK*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC369511CL /*Attack_Kick*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(2028143634L /*ATTACK_KICK*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		doAction(2176337367L /*ATTACK_JUMP_SMASH*/, blendTime, onDoActionEnd -> {
			setVariable(0x5749908DL /*_JumpCount*/, 0);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Jump2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x11F71E61L /*Attack_Jump2*/);
		doAction(3266150866L /*ATTACK_RUNNING_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Kick2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBF4941A0L /*Attack_Kick2*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(410306098L /*ATTACK_KICK_COMBO*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Combo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCC2E4599L /*Attack_Combo*/);
		if (getVariable(0x5CE1B16EL /*_GeneralCount*/) < 16) {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) + 1);
		}
		doAction(2742053350L /*ATTACK_SWING_COMBO*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		doAction(2804644807L /*ATTACK_STONEWAVE*/, blendTime, onDoActionEnd -> {
			setVariable(0x357581C9L /*_RangeCount*/, 0);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_General(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88DD2B64L /*Attack_General*/);
		if (getSelfCombinePoint() == 14) {
			if (changeState(state -> Attack_General_Cancel(0.3)))
				return;
		}
		doAction(901362947L /*ATTACK_GENERAL_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General1(blendTime)));
	}

	protected void Attack_General1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBDE891C8L /*Attack_General1*/);
		if (getSelfCombinePoint() == 14) {
			if (changeState(state -> Attack_General_Cancel(0.3)))
				return;
		}
		doAction(1802055385L /*ATTACK_GENERAL_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General2(blendTime)));
	}

	protected void Attack_General2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD70732B3L /*Attack_General2*/);
		doAction(2409930781L /*ATTACK_GENERAL_C*/, blendTime, onDoActionEnd -> {
			setVariable(0x5CE1B16EL /*_GeneralCount*/, 0);
			setVariable(0x8620C5A4L /*_GeneralLimitCount*/, getVariable(0x8620C5A4L /*_GeneralLimitCount*/) - 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_General_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5C0CF23EL /*Attack_General_Cancel*/);
		doAction(3152228051L /*DAMAGE_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
	}

	protected void Trap_On(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x90264615L /*Trap_On*/);
		doAction(1861084442L /*DAMAGE_TRAP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Trap_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2D319B2BL /*Trap_Off*/);
		doAction(1331236243L /*DAMAGE_TRAP_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x5749908DL /*_JumpCount*/) < 26) {
			setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		}
		if (getVariable(0x357581C9L /*_RangeCount*/) < 16) {
			setVariable(0x357581C9L /*_RangeCount*/, getVariable(0x357581C9L /*_RangeCount*/) + 1);
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xD5712181L /*WalkRandom*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 34) && getState() != 0xBF725BC4L /*Damage_KnockBack*/ && getState() != 0x3FB3341CL /*Damage_Stun*/ && getState() != 0x2E79F126L /*Damage_Stun_Ing*/ && getState() != 0xA432B7EDL /*Damage_Stun_End*/) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if ((getState() == 0x88DD2B64L /*Attack_General*/ || getState() == 0xBDE891C8L /*Attack_General1*/ || getState() == 0xD70732B3L /*Attack_General2*/) && getSelfCombinePoint() == 14) {
			if (changeState(state -> Attack_General_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 59 && (getState() != 0x88DD2B64L /*Attack_General*/ || getState() != 0xBDE891C8L /*Attack_General1*/ || getState() != 0xD70732B3L /*Attack_General2*/ || getState() != 0xBF725BC4L /*Damage_KnockBack*/ || getState() != 0x6A4B0B1DL /*Damage_Rigid*/ || getState() != 0x3FB3341CL /*Damage_Stun*/ || getState() != 0xA432B7EDL /*Damage_Stun_End*/ || getState() != 0xBF725BC4L /*Damage_KnockBack*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCallDrop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Trap_On(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
