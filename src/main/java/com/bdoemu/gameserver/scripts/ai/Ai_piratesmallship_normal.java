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
@IAIName("piratesmallship_normal")
public class Ai_piratesmallship_normal extends CreatureAI {
	public Ai_piratesmallship_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xF62CFC4DL /*_M_AttackCount*/, 0);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) > 5 && target != null && getDistanceToTarget(target) < 2000) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) >= 5) {
			if (changeState(state -> Chaser_Run_At(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		scheduleState(state -> Move_Return(blendTime), 1000);
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Battle_Wait(1.8)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> escape(8000, () -> {
			setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPathToTarget(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPathToTarget_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Escape(blendTime), 3000)));
	}

	protected void Detect_Target_Err(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x4CDFA107L /*Detect_Target_Err*/);
		doAction(996790591L /*CHASER_RUN2*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Left, -90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 5000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
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
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 3000, 7000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 9000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 100);
		});
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return_NoLf(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4CAF3777L /*Move_Return_NoLf*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		changeState(state -> FailFindPathToTarget_Logic(blendTime));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FailFindPathToTarget_Logic(blendTime), 1000));
	}

	protected void FailFindPathToTarget_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3C75D394L /*FailFindPathToTarget_Logic*/);
		if (changeState(state -> Battle_Wait(blendTime)))
			return;
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xF62CFC4DL /*_M_AttackCount*/, 0);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_L(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_L(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_L(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= -47 && target != null && getAngleToTarget(target) <= 47 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Turn_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 112 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Turn_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= -180 && target != null && getAngleToTarget(target) <= -112 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Turn_Lv1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 4000) {
			if (changeState(state -> Chaser_Run(1.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 400, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Turn_Lv2(blendTime), 2000)));
	}

	protected void Chaser_Run2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0x396F005BL /*Chaser_Run2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 40 && target != null && getAngleToTarget(target) < 108 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -40 && target != null && getAngleToTarget(target) > -108 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Attack_L(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1800) {
			if (changeState(state -> Turn_Off(0.3)))
				return;
		}
		if(getCallCount() == 4) {
			if (changeState(state -> Chaser_Run3(0.4)))
				return;
		}
		doAction(996790591L /*CHASER_RUN2*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Chaser_Run2(blendTime), 700);
		});
	}

	protected void Chaser_Run3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0xC7CFDCEDL /*Chaser_Run3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 40 && target != null && getAngleToTarget(target) < 108 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -40 && target != null && getAngleToTarget(target) > -108 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Attack_L(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1800) {
			if (changeState(state -> Turn_Off(1.8)))
				return;
		}
		doAction(1288915276L /*CHASER_RUN3*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Chaser_Run3(blendTime), 700);
		});
	}

	protected void Move_Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAF2AADB9L /*Move_Attack_R*/);
		setVariable(0xF62CFC4DL /*_M_AttackCount*/, getVariable(0xF62CFC4DL /*_M_AttackCount*/) + 1);
		doAction(979521964L /*MOVE_ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Run_At(blendTime)));
	}

	protected void Move_Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4E8822B0L /*Move_Attack_L*/);
		setVariable(0xF62CFC4DL /*_M_AttackCount*/, getVariable(0xF62CFC4DL /*_M_AttackCount*/) + 1);
		doAction(3842209107L /*MOVE_ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Run_At(blendTime)));
	}

	protected void Chaser_Run_At(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0xF34932CBL /*Chaser_Run_At*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(getCallCount() == 12) {
				if (changeState(state -> Move_Attack_R(1)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(getCallCount() == 12) {
				if (changeState(state -> Move_Attack_L(1)))
					return;
			}
		}
		if (getVariable(0xF62CFC4DL /*_M_AttackCount*/) >= 6 && target != null && getAngleToTarget(target) > 50 && target != null && getAngleToTarget(target) < 111 && target != null && getDistanceToTarget(target) <= 1800) {
			if(getCallCount() == 10) {
				if (changeState(state -> Turn_Off(1.8)))
					return;
			}
		}
		if (getVariable(0xF62CFC4DL /*_M_AttackCount*/) >= 6 && target != null && getAngleToTarget(target) < -50 && target != null && getAngleToTarget(target) > -111 && target != null && getDistanceToTarget(target) <= 1800) {
			if(getCallCount() == 10) {
				if (changeState(state -> Turn_Off(1.8)))
					return;
			}
		}
		if (getVariable(0xF62CFC4DL /*_M_AttackCount*/) >= 12 && target != null && getDistanceToTarget(target) >= 2000) {
			if(getCallCount() == 10) {
				if (changeState(state -> Turn_Off(1.8)))
					return;
			}
		}
		doAction(1288915276L /*CHASER_RUN3*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Chaser_Run_At(blendTime), 700);
		});
	}

	protected void Turn_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0x3BA835A6L /*Turn_Lv1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) <= 113 && target != null && getDistanceToTarget(target) > 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) >= -113 && target != null && getDistanceToTarget(target) > 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) <= 113 && target != null && getDistanceToTarget(target) > 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) >= -113 && target != null && getDistanceToTarget(target) > 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) <= 113 && target != null && getDistanceToTarget(target) > 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) >= -113 && target != null && getDistanceToTarget(target) > 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 50 && target != null && getAngleToTarget(target) < 111) {
			if (changeState(state -> Turn_Off(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -50 && target != null && getAngleToTarget(target) > -111) {
			if (changeState(state -> Turn_Off(1.8)))
				return;
		}
		if(getCallCount() == 13) {
			if (changeState(state -> Chaser_Run2(0.4)))
				return;
		}
		doAction(1424721413L /*TURN_LV1*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Lv1(blendTime), 700));
	}

	protected void Turn_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0x9D131F05L /*Turn_Lv2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) > 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) > 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) > 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_R(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) > 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_L(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 50 && target != null && getAngleToTarget(target) < 111 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Turn_Off(1.8)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -50 && target != null && getAngleToTarget(target) > -111 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Turn_Off(1.8)))
				return;
		}
		if(getCallCount() == 4) {
			if (changeState(state -> Chaser_Run2(0.3)))
				return;
		}
		doAction(2942115834L /*TURN_LV2*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Lv2(blendTime), 700));
	}

	protected void Turn_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDA74F6ADL /*Turn_Off*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Battle_Wait(1.8)))
				return;
		}
		doAction(4050250095L /*TURN_OFF*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 400, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 8000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61CFF41EL /*Attack_R*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(4136558927L /*ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x40F853F4L /*Attack_L*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(216985241L /*ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCAECEDC8L /*Long_Attack_R*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1339189768L /*LONG_ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5DEF57FDL /*Long_Attack_L*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1228385763L /*LONG_ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void SLong_Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x151FEB4CL /*SLong_Attack_R*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2186094444L /*SLONG_ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void SLong_Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD4072CDL /*SLong_Attack_L*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1481860757L /*SLONG_ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.2)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}
}
