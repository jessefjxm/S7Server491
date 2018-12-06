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
@IAIName("cyclops_boss")
public class Ai_cyclops_boss extends CreatureAI {
	public Ai_cyclops_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x71C34AF8L /*_GeneralAttCountDown*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 2);
		setVariable(0x7D29A7BDL /*_KnockBackCount*/, 0);
		setVariable(0x357581C9L /*_RangeCount*/, 0);
		setVariable(0x7847D43EL /*_DetectCount*/, 3);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x4DD43C1FL /*_isHealCount*/, 0);
		setVariable(0xA9698AA4L /*_EyeStunCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getSelfCombinePoint() >= 59 && getVariable(0x873BC567L /*AI_isRandomMove*/) == 1) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
				if (changeState(state -> Detect_Target(0.3)))
					return;
			}
		}
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 0 && getVariable(0x7847D43EL /*_DetectCount*/) > 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 500 && isCreatureVisible(object, false))) {
				if (changeState(state -> Detect_Target_1(0.3)))
					return;
			}
		}
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 0 && getVariable(0x7847D43EL /*_DetectCount*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 500 && isCreatureVisible(object, false))) {
				if (changeState(state -> Detect_Target_2(0.3)))
					return;
			}
		}
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 1) {
			if(getCallCount() == 5) {
				if (changeState(state -> Move_Random(0.4)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF315D8BBL /*Move_Random_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1500, 2000, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
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

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Detect_Target_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x727AB326L /*Detect_Target_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2035445255L /*DETECT_ENEMY_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x7847D43EL /*_DetectCount*/) > 0) {
				setVariable(0x7847D43EL /*_DetectCount*/, getVariable(0x7847D43EL /*_DetectCount*/) - 1);
			}
			scheduleState(state -> Move_Random_1(blendTime), 1000);
		});
	}

	protected void Detect_Target_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B549169L /*Detect_Target_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3835279419L /*DETECT_ENEMY_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Detect_Target(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x71C34AF8L /*_GeneralAttCountDown*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 2);
		setVariable(0x7D29A7BDL /*_KnockBackCount*/, 0);
		setVariable(0x7847D43EL /*_DetectCount*/, 3);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x4DD43C1FL /*_IsHealCount*/, 0);
		setVariable(0xA9698AA4L /*_EyeStunCount*/, 0);
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getSelfPartHp(1) <= 0 && getVariable(0xA9698AA4L /*_EyeStunCount*/) == 0) {
			if (changeState(state -> Damage_Eye_Stun(0.1)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0xEF7AE808L /*_RoarCount*/) == 2 && target != null && getDistanceToTarget(target) <= 700) {
			if (changeState(state -> Attack_Roar(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0xEF7AE808L /*_RoarCount*/) == 1 && target != null && getDistanceToTarget(target) <= 700) {
			if (changeState(state -> Attack_Roar(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Left_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 135 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Right_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -65) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 65) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 400) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (getVariable(0x357581C9L /*_RangeCount*/) >= 3 && target != null && getDistanceToTarget(target) <= 1500) {
			if (changeState(state -> Attack_Range(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 25 && getVariable(0xEF7AE808L /*_RoarCount*/) == 0 && target != null && getDistanceToTarget(target) <= 700) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Roar(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1000 && getDistanceToTarget(target, false) <= 1500)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 13) {
			if (changeState(state -> Battle_Run_Slow(0.3)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_UpperSwing(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 650) {
			if (changeState(state -> Battle_Run_Stop(0.4)))
				return;
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
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD490300FL /*Battle_Run_Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1000 && getDistanceToTarget(target, false) <= 1500)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 400) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		doAction(3949145239L /*BATTLE_RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Run_Slow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x83C645F7L /*Battle_Run_Slow*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 13) {
			if (changeState(state -> Battle_Run_Slow(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 650) {
			if (changeState(state -> Battle_Run_Slow_Stop(0.4)))
				return;
		}
		doAction(3020739504L /*BATTLE_RUN_SLOW*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Run_Slow(blendTime), 100)));
	}

	protected void Battle_Run_Slow_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61ED4107L /*Battle_Run_Slow_Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 400) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		doAction(4134803639L /*BATTLE_RUN_SLOW_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getSelfCombinePoint() >= 13) {
			if (changeState(state -> Battle_Walk_Slow(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200 && getSelfCombinePoint() >= 13) {
			if (changeState(state -> Battle_Run_Slow(0.4)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1000 && getDistanceToTarget(target, false) <= 1500)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Slow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xB7B660E1L /*Battle_Walk_Slow*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200 && getSelfCombinePoint() >= 13) {
			if (changeState(state -> Battle_Run_Slow(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 400 && getSelfCombinePoint() < 13) {
			if (changeState(state -> Battle_Walk_Slow(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(1057692549L /*BATTLE_WALK_SLOW*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Walk_Slow(blendTime), 100)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getSelfCombinePoint() >= 59) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) >= 20) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_General_Str(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x4DD43C1FL /*_isHealCount*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Heal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Smash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0x7D29A7BDL /*_KnockBackCount*/) >= 3) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(350 + Rnd.get(450, 500), ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3567810684L /*ATTACK_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1597155294L /*ATTACK_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FD5675DL /*Battle_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4247977435L /*ATTACK_TURN180_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2395845637L /*ATTACK_TURN180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Ing(blendTime), 2500));
	}

	protected void Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E79F126L /*Damage_Stun_Ing*/);
		doAction(363950375L /*DAMAGE_STUN_A*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(2397205042L /*DAMAGE_STUN_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2500));
	}

	protected void Damage_Eye_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA7BAD77EL /*Damage_Eye_Stun*/);
		setVariable(0xA9698AA4L /*_EyeStunCount*/, 1);
		doAction(1910377999L /*DAMAGE_EYE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Eye_Stun_1(blendTime), 5000));
	}

	protected void Damage_Eye_Stun_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61DFCD82L /*Damage_Eye_Stun_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2762047794L /*DAMAGE_EYE_STUN_B*/, blendTime, onDoActionEnd -> {
			setVariable(0xA9698AA4L /*_EyeStunCount*/, 0);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Heal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE3496A0CL /*Attack_Heal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1757343001L /*ATTACK_HEAL*/, blendTime, onDoActionEnd -> {
			setVariable(0x4DD43C1FL /*_isHealCount*/, 1);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x7D29A7BDL /*_KnockBackCount*/) < 4) {
			setVariable(0x7D29A7BDL /*_KnockBackCount*/, getVariable(0x7D29A7BDL /*_KnockBackCount*/) + 1);
		}
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x7D29A7BDL /*_KnockBackCount*/, 0);
		if (getVariable(0x357581C9L /*_RangeCount*/) < 4) {
			setVariable(0x357581C9L /*_RangeCount*/, getVariable(0x357581C9L /*_RangeCount*/) + 1);
		}
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0xEF7AE808L /*_RoarCount*/) > 0) {
			setVariable(0xEF7AE808L /*_RoarCount*/, getVariable(0xEF7AE808L /*_RoarCount*/) - 1);
		}
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x357581C9L /*_RangeCount*/, 0);
		doAction(3674084903L /*ATTACK_RANGE*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Smash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x220CEDAFL /*Attack_Smash*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1150742983L /*ATTACK_SMASH_COMBO*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_UpperSwing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD74041F2L /*Attack_UpperSwing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1261557706L /*ATTACK_UPPERSWING*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		doAction(896467984L /*ATTACK_JUMP_STR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_General_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9544FBACL /*Attack_General_Str*/);
		setVariable(0x71C34AF8L /*_GeneralAttCountDown*/, 0);
		doAction(37328067L /*ATTACK_GENERAL_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General(blendTime)));
	}

	protected void Attack_General(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88DD2B64L /*Attack_General*/);
		doAction(901362947L /*ATTACK_GENERAL_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_General_Cancle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC48A8DCL /*Attack_General_Cancle*/);
		doAction(2746448427L /*ATTACK_GENERAL_CANCLE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 2000) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x873BC567L /*AI_isRandomMove*/) == 0 && target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 500) && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target_1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) < 25) {
			setVariable(0x71C34AF8L /*_GeneralAttCountDown*/, getVariable(0x71C34AF8L /*_GeneralAttCountDown*/) + 1);
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(1) <= 0 && getVariable(0xA9698AA4L /*_EyeStunCount*/) == 0) {
			if (changeState(state -> Damage_Eye_Stun(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x9544FBACL /*Attack_General_Str*/ && getSelfCombinePoint() == 14) {
			if (changeState(state -> Attack_General_Cancle(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
