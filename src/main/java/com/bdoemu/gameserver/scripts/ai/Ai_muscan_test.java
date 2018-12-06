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
@IAIName("muscan_test")
public class Ai_muscan_test extends CreatureAI {
	public Ai_muscan_test(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x169EAF09L /*_Anger*/, 0);
		setVariable(0x8AA7EEEBL /*_StunCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> StartAction(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void StartAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD7C9080FL /*StartAction*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> StartAction(blendTime), 200));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Return_End(blendTime), 1000)));
	}

	protected void Move_Return_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x245E0189L /*Move_Return_End*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> StartAction(blendTime), 2000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
		if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Anger(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1F6FAE6L /*Anger*/);
		setVariable(0x169EAF09L /*_Anger*/, 1);
		doAction(489219374L /*ANGER_MODE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 0 && getVariable(0x3F487035L /*_HP*/) < 70) {
			if (changeState(state -> Anger(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
					return;
			}
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shout(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Smash(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shout(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Swing_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Upper_RL(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -120 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if (changeState(state -> Battle_Turn_Left_180(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 120 && getVariable(0xE5BD13F2L /*_Degree*/) <= 179) {
			if (changeState(state -> Battle_Turn_Right_180(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Battle_Walk_Around(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 15) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
					return;
			}
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Smash(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Swing_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Upper_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 250 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 750) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
					return;
			}
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Smash(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Swing_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Upper_RL(blendTime)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(200, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
					return;
			}
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Smash(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Swing_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Upper_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 230 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Run_Stop(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD490300FL /*Battle_Run_Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3949145239L /*BATTLE_RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
					return;
			}
		}
		if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 30 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shout(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shout(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Smash(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shout(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Swing_RL(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Upper_RL(blendTime)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> moveAround(250 + Rnd.get(200, 400), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
						return;
				}
			}
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 30 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Smash(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Swing_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Upper_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 500) {
				if (changeState(state -> Battle_Run(blendTime)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 250) {
				if (changeState(state -> Battle_Walk(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
						return;
				}
			}
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 30 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Smash(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Swing_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Upper_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 500) {
				if (changeState(state -> Battle_Run(blendTime)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 250) {
				if (changeState(state -> Battle_Walk(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FD5675DL /*Battle_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3146596688L /*TURN_LEFT_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
						return;
				}
			}
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 30 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Smash(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Swing_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Upper_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 500) {
				if (changeState(state -> Battle_Run(blendTime)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 250) {
				if (changeState(state -> Battle_Walk(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3358478875L /*TURN_RIGHT_180*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Jump_Ground_Smash(0.3)))
						return;
				}
			}
			if (getVariable(0x169EAF09L /*_Anger*/) == 1 && target != null && getDistanceToTarget(target) <= 300 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Whirlwind_Ready(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 30 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 60 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(20)) {
					if (changeState(state -> Attack_Smash(blendTime)))
						return;
				}
			}
			if (getVariable(0x3F487035L /*_HP*/) < 90 && target != null && getDistanceToTarget(target) <= 600 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Shout(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Swing_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 250 && target != null && isCreatureVisible(target, false)) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Upper_RL(blendTime)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 500) {
				if (changeState(state -> Battle_Run(blendTime)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 250) {
				if (changeState(state -> Battle_Walk(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E79F126L /*Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 200));
	}

	protected void Damage_Attack_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6C5483BEL /*Damage_Attack_Cancel*/);
		doAction(3152228051L /*DAMAGE_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 5000));
	}

	protected void Attack_Smash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x220CEDAFL /*Attack_Smash*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1224631599L /*ATTACK_SMASH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_LR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF225444CL /*Attack_Swing_LR*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3510121346L /*ATTACK_SWING_LR_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_LR_END(blendTime)));
	}

	protected void Attack_Swing_LR_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2A620073L /*Attack_Swing_LR_END*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2023083601L /*ATTACK_SWING_LR_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_RL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xADFB1D8FL /*Attack_Swing_RL*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1694651615L /*ATTACK_SWING_RL_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_RL_END(blendTime)));
	}

	protected void Attack_Swing_RL_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x45153C12L /*Attack_Swing_RL_END*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3852232532L /*ATTACK_SWING_RL_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Upper_LR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD85637C6L /*Attack_Upper_LR*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4260922229L /*ATTACK_UPPER_LR_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Upper_LR_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x89917E02L /*Attack_Upper_LR_END*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3986189856L /*ATTACK_UPPER_LR_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Upper_RL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1EE57D1DL /*Attack_Upper_RL*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2830023489L /*ATTACK_UPPER_RL_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Upper_RL_END(blendTime)));
	}

	protected void Attack_Upper_RL_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x473E65A7L /*Attack_Upper_RL_END*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(903518129L /*ATTACK_UPPER_RL_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Jump_Ground_Smash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6C4CAEACL /*Attack_Jump_Ground_Smash*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2051228251L /*ATTACK_JUMP_GROUND_SMASH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Shout(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7982D823L /*Attack_Shout*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2075155719L /*SKILL_SHOUT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Whirlwind_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8614566FL /*Attack_Whirlwind_Ready*/);
		doAction(4057147095L /*SKILL_WHIRLWIND_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Whirlwind_1(blendTime), 200));
	}

	protected void Attack_Whirlwind_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9944F8EL /*Attack_Whirlwind_1*/);
		doAction(4255639086L /*SKILL_WHIRLWIND*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Whirlwind_2(blendTime)));
	}

	protected void Attack_Whirlwind_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8C822354L /*Attack_Whirlwind_2*/);
		doAction(2094287996L /*SKILL_WHIRLWIND_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Whirlwind_End(blendTime)));
	}

	protected void Attack_Whirlwind_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF39C44A3L /*Attack_Whirlwind_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1546434933L /*SKILL_WHIRLWIND_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0xD7C9080FL /*StartAction*/ && target != null && isCreatureVisible(target, false)) {
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
		if (getState() == 0xD7C9080FL /*StartAction*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Detect_Target(0.3)))
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
		if ((getState() == 0x8614566FL /*Attack_Whirlwind_Ready*/ || getState() == 0xA9944F8EL /*Attack_Whirlwind_1*/ || getState() == 0x8C822354L /*Attack_Whirlwind_2*/ || getState() == 0xF39C44A3L /*Attack_Whirlwind_End*/) && getSelfCombinePoint() == 14) {
			if (changeState(state -> Damage_Attack_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 59 && (getState() == 0x6C5483BEL /*Damage_Attack_Cancel*/ || getState() == 0x7982D823L /*Attack_Shout*/ || getState() == 0xA1F6FAE6L /*Anger*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
