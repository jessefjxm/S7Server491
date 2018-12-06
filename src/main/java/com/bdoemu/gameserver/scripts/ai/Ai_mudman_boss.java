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
@IAIName("mudman_boss")
public class Ai_mudman_boss extends CreatureAI {
	public Ai_mudman_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x1EB244E5L /*_Hit*/, 0);
		setVariable(0x3F487035L /*_Hp*/, 100);
		setVariable(0x49BE15E5L /*_HpRate*/, 0);
		setVariable(0x9A4D48B0L /*_HpCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1200)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
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
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
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
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
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
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) > -180) {
			if (changeState(state -> Battle_Turn_Left_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180) {
			if (changeState(state -> Battle_Turn_Right_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Uppers(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 1000)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Charge1(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 1000)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Charge2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Norma2(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Norma3(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Uppers_Move1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE1FDE376L /*Move_Uppers_Move1*/);
		doAction(2356629443L /*MOVE_HIDE_DOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Uppers_Moveing(blendTime), 200));
	}

	protected void Move_Uppers_Moveing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xF8C8893AL /*Move_Uppers_Moveing*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Uppers_Move2(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Move_Uppers_Move2(0.1)))
				return;
		}
		doAction(3542498425L /*MOVE_HIDE_MOVE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Uppers_Moveing(blendTime), 200)));
	}

	protected void Move_Uppers_Move2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9882D187L /*Move_Uppers_Move2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(772763326L /*MOVE_HIDE_UP*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Norma4(0.2)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Move_Uppers_Move1(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Uppers(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 200)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Move_Uppers_Move1(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 40) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Uppers(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 50) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Uppers(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 550) {
				if (changeState(state -> Battle_Walk(0.4)))
					return;
			}
			if (changeState(state -> Attack_Norma2(0.3)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 50) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Uppers(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 550) {
				if (changeState(state -> Battle_Walk(0.4)))
					return;
			}
			if (changeState(state -> Attack_Norma3(0.3)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FD5675DL /*Battle_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(2650559575L /*TURN_180_L*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 50) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Uppers(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Around(0.3)))
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
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(3000360872L /*TURN_180_R*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 50) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Uppers(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Around(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Around(0.3)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 600) {
				if(Rnd.getChance(5)) {
					if (changeState(state -> Move_Uppers_Move1(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Around(0.3)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 600) {
				if(Rnd.getChance(5)) {
					if (changeState(state -> Move_Uppers_Move1(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
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
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Ing(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Around(0.3)))
					return;
			}
			if (target != null && getDistanceToTarget(target) > 600) {
				if(Rnd.getChance(5)) {
					if (changeState(state -> Move_Uppers_Move1(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 1000) {
				if (changeState(state -> Move_Uppers_Move1(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 2500);
		});
	}

	protected void Damage_Attack_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6C5483BEL /*Damage_Attack_Cancel*/);
		doAction(3152228051L /*DAMAGE_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Norma2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x30B1C591L /*Attack_Norma2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2944721907L /*ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Norma3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6E1ECD64L /*Attack_Norma3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1297441637L /*ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Norma4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE7D1EFD7L /*Attack_Norma4*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2079495046L /*ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1427142468L /*ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B699344L /*Attack_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3338747575L /*ATTACK_AROUND*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Charge1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29A3AF09L /*Attack_Charge1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(954110867L /*ATTACK_CHARGE1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Charge2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19FAB99CL /*Attack_Charge2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1569378594L /*ATTACK_CHARGE2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Slime1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9C4D92F6L /*Attack_Slime1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x9A4D48B0L /*_HpCount*/, getVariable(0x9A4D48B0L /*_HpCount*/) + 1);
		doAction(2759800916L /*ATTACK_SLIME1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Slime2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x28249698L /*Attack_Slime2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x9A4D48B0L /*_HpCount*/, getVariable(0x9A4D48B0L /*_HpCount*/) + 1);
		doAction(2673539401L /*ATTACK_SLIME2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Uppers(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x16C1194BL /*Attack_Uppers*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3604607574L /*ATTACK_UPPER1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x49BE15E5L /*_HpRate*/) < 70 && getVariable(0x9A4D48B0L /*_HpCount*/) == 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Slime1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getVariable(0x49BE15E5L /*_HpRate*/) < 20 && getVariable(0x9A4D48B0L /*_HpCount*/) == 2) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Slime1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getVariable(0x49BE15E5L /*_HpRate*/) < 45 && getVariable(0x9A4D48B0L /*_HpCount*/) == 1) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Slime2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getVariable(0x49BE15E5L /*_HpRate*/) < 20 && getVariable(0x9A4D48B0L /*_HpCount*/) == 2) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Slime2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
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
		if (getState() == 0x16C1194BL /*Attack_Uppers*/ && getSelfCombinePoint() == 14) {
			if (changeState(state -> Damage_Attack_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 59 && (getState() == 0x6C5483BEL /*Damage_Attack_Cancel*/ || getState() == 0xBF725BC4L /*Damage_KnockBack*/ || getState() == 0x69E1FC3AL /*Damage_KnockDown*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
