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
@IAIName("redorcelder_boss")
public class Ai_redorcelder_boss extends CreatureAI {
	public Ai_redorcelder_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x353C0FBL /*_Mob_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5ECA70CFL /*_Attack_Normal*/, 0);
		setVariable(0xF44AC14DL /*_Attack_Close*/, 0);
		setVariable(0x6013E887L /*_Attack_Explosion*/, 0);
		setVariable(0x175D4C14L /*_Attack_FireWave*/, 0);
		setVariable(0x91664759L /*_Attack_Volcano*/, 0);
		setVariable(0x30852D04L /*_Reset_Count*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x6013E887L /*_Attack_Explosion*/) >= 2 && getVariable(0x175D4C14L /*_Attack_FireWave*/) >= 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 5) {
			if (changeState(state -> Reset_Count(blendTime)))
				return;
		}
		if (getVariable(0x6013E887L /*_Attack_Explosion*/) >= 2 && getVariable(0x175D4C14L /*_Attack_FireWave*/) >= 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3 && target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Reset_Count(blendTime)))
				return;
		}
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Reset_Count(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x92DFD868L /*Reset_Count*/);
		setVariable(0x5ECA70CFL /*_Attack_Normal*/, 0);
		setVariable(0x6013E887L /*_Attack_Explosion*/, 0);
		setVariable(0x175D4C14L /*_Attack_FireWave*/, 0);
		setVariable(0x91664759L /*_Attack_Volcano*/, 0);
		setVariable(0x30852D04L /*_Reset_Count*/, getVariable(0x30852D04L /*_Reset_Count*/) + 1);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 700, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Detect_Target(blendTime), 1000));
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
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6013E887L /*_Attack_Explosion*/, 0);
		setVariable(0x175D4C14L /*_Attack_FireWave*/, 0);
		setVariable(0x91664759L /*_Attack_Volcano*/, 0);
		setVariable(0xF44AC14DL /*_Attack_Close*/, 0);
		setVariable(0x5ECA70CFL /*_Attack_Normal*/, 0);
		setVariable(0x30852D04L /*_Reset_Count*/, 0);
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
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
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x353C0FBL /*_Mob_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && getVariable(0xF44AC14DL /*_Attack_Close*/) <= 3) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Close(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) > 135) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Back(0.4)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) < -25) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left(0.4)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) > 25) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && getVariable(0x30852D04L /*_Reset_Count*/) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Volcano(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 1) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 1) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 1100) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 700) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1500) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if(getCallCount() == 100) {
				if (changeState(state -> LostTarget(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(300 + Rnd.get(150, 350), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if(getCallCount() == 100) {
				if (changeState(state -> LostTarget(blendTime)))
					return;
			}
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 450) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && getVariable(0xF44AC14DL /*_Attack_Close*/) <= 3) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Close(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 450) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && getVariable(0xF44AC14DL /*_Attack_Close*/) <= 3) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Close(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6C6A2115L /*Battle_Turn_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 450) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && getVariable(0xF44AC14DL /*_Attack_Close*/) <= 3) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Close(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) <= 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 1 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) < 7) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && getVariable(0x6013E887L /*_Attack_Explosion*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Explosion(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1300 && getVariable(0x175D4C14L /*_Attack_FireWave*/) == 2 && getVariable(0x5ECA70CFL /*_Attack_Normal*/) > 3) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_FireWave(0.3)))
					return;
			}
		}
		doAction(1852897567L /*BATTLE_TURN_BACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x5ECA70CFL /*_Attack_Normal*/) >= 3) {
			setVariable(0xF44AC14DL /*_Attack_Close*/, 0);
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> {
			setVariable(0x5ECA70CFL /*_Attack_Normal*/, getVariable(0x5ECA70CFL /*_Attack_Normal*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Close(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA2FD6B1AL /*Attack_Close*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(292824189L /*ATTACK_CLOSE*/, blendTime, onDoActionEnd -> {
			setVariable(0xF44AC14DL /*_Attack_Close*/, getVariable(0xF44AC14DL /*_Attack_Close*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3EE4B73AL /*Attack_Explosion*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2587605505L /*ATTACK_EXPLOSION*/, blendTime, onDoActionEnd -> {
			setVariable(0x6013E887L /*_Attack_Explosion*/, getVariable(0x6013E887L /*_Attack_Explosion*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_FireWave(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC3314DF5L /*Attack_FireWave*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(191920785L /*ATTACK_FIREWAVE*/, blendTime, onDoActionEnd -> {
			setVariable(0x175D4C14L /*_Attack_FireWave*/, getVariable(0x175D4C14L /*_Attack_FireWave*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Volcano(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7B52D384L /*Attack_Volcano*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x30852D04L /*_Reset_Count*/, 0);
		doAction(2916642300L /*ATTACK_VOLCANO*/, blendTime, onDoActionEnd -> {
			setVariable(0x91664759L /*_Attack_Volcano*/, getVariable(0x91664759L /*_Attack_Volcano*/) + 1);
			changeState(state -> Logic(blendTime));
		});
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
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
