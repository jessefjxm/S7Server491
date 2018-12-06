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
@IAIName("ori_mutanttrolla_boss")
public class Ai_ori_mutanttrolla_boss extends CreatureAI {
	public Ai_ori_mutanttrolla_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 100);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 0);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
		setVariable(0x5F56C63AL /*_isRageLevel*/, 0);
		setVariable(0x6AE7D30AL /*_isRageCount*/, 3);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait_Logic(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 0);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
		setVariable(0x5F56C63AL /*_isRageLevel*/, 0);
		setVariable(0x6AE7D30AL /*_isRageCount*/, 3);
		clearAggro(true);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
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
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 75 && getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && getVariable(0x6AE7D30AL /*_isRageCount*/) == 3) {
			if (changeState(state -> Battle_Roar_75(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 790) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Wait_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8D19C8B2L /*Battle_Wait_75*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && getVariable(0x6AE7D30AL /*_isRageCount*/) == 2) {
			if (changeState(state -> Battle_Roar_50(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_75(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_75(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Left_75(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Right_75(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2_75(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_75(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_75(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 790) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_75(blendTime), 100));
	}

	protected void Battle_Wait_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3CB871CL /*Battle_Wait_50*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 25 && getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && getVariable(0x6AE7D30AL /*_isRageCount*/) == 1) {
			if (changeState(state -> Battle_Roar_25(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_50(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_50(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Left_50(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Right_50(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_50(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 790) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_50(blendTime), 100));
	}

	protected void Battle_Wait_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDF7A13BAL /*Battle_Wait_25*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_25(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_25(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Left_25(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Battle_Turn_Right_25(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2_25(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_25(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_25(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 790) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_25(blendTime), 100));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal1_75(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_75(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal1_50(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_50(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal1_25(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_25(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1600) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_75(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_50(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_25(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_75(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_75(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_50(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_50(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_25(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_25(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_75(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_50(0.4)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_25(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3000360872L /*TURN_180_R*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FD5675DL /*Battle_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2650559575L /*TURN_180_L*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x792B58CFL /*Battle_Turn_Left_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1213496878L /*TURN_LEFT_75*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x199383B4L /*Battle_Turn_Right_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3150250778L /*TURN_RIGHT_75*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x39719A74L /*Battle_Turn_Right_180_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4238446802L /*TURN_180_R_75*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA826E5EFL /*Battle_Turn_Left_180_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2206635168L /*TURN_180_L_75*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8346801EL /*Battle_Turn_Left_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(331652462L /*TURN_LEFT_50*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE215B492L /*Battle_Turn_Right_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3946687466L /*TURN_RIGHT_50*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2354E475L /*Battle_Turn_Right_180_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4148907182L /*TURN_180_R_50*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x37F2EC08L /*Battle_Turn_Left_180_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(895079918L /*TURN_180_L_50*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x556E7E30L /*Battle_Turn_Left_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3060621629L /*TURN_LEFT_25*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B6B316CL /*Battle_Turn_Right_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1960204476L /*TURN_RIGHT_25*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9E867DFL /*Battle_Turn_Right_180_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(554234127L /*TURN_180_R_25*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2D0CC481L /*Battle_Turn_Left_180_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3163475942L /*TURN_180_L_25*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_75(0.2)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_75(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_50(0.2)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_50(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Left_180_25(0.2)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Turn_Right_180_25(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal2_75(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_75(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_75(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal2_50(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_50(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_50(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal2_25(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal1_25(0.3)))
					return;
			}
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_25(0.3)))
				return;
		}
		changeState(state -> Battle_Wait_Logic(blendTime));
	}

	protected void Battle_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8A9BEB3L /*Battle_Wait_Logic*/);
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 1) {
			if (changeState(state -> Battle_Wait_75(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 2) {
			if (changeState(state -> Battle_Wait_50(0.3)))
				return;
		}
		if (getVariable(0x5F56C63AL /*_isRageLevel*/) == 3) {
			if (changeState(state -> Battle_Wait_50(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF1582F25L /*Attack_Normal_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1573768277L /*BATTLE_ATTACK1_75*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_75(blendTime)));
	}

	protected void Attack_Normal1_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE4C03CBCL /*Attack_Normal1_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2257295051L /*BATTLE_ATTACK2_75*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_75(blendTime)));
	}

	protected void Attack_Normal2_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9D6E2FBL /*Attack_Normal2_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1761101079L /*BATTLE_ATTACK3_75*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_75(blendTime)));
	}

	protected void Attack_Normal_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB13ED9ACL /*Attack_Normal_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2648055982L /*BATTLE_ATTACK1_50*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_50(blendTime)));
	}

	protected void Attack_Normal1_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x56EF7B49L /*Attack_Normal1_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2838580566L /*BATTLE_ATTACK2_50*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_50(blendTime)));
	}

	protected void Attack_Normal2_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x995571EFL /*Attack_Normal2_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1355241425L /*BATTLE_ATTACK3_50*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_50(blendTime)));
	}

	protected void Attack_Normal_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x604C1CDBL /*Attack_Normal_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(872893193L /*BATTLE_ATTACK1_25*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_25(blendTime)));
	}

	protected void Attack_Normal1_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x738E357EL /*Attack_Normal1_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(306144296L /*BATTLE_ATTACK2_25*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_25(blendTime)));
	}

	protected void Attack_Normal2_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE355E9BBL /*Attack_Normal2_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4007529611L /*BATTLE_ATTACK3_25*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_25(blendTime)));
	}

	protected void Battle_Roar_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8399A432L /*Battle_Roar_75*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x5F56C63AL /*_isRageLevel*/, 1);
		if (getVariable(0x6AE7D30AL /*_isRageCount*/) == 3) {
			setVariable(0x6AE7D30AL /*_isRageCount*/, getVariable(0x6AE7D30AL /*_isRageCount*/) - 1);
		}
		doAction(1210701427L /*BATTLE_ATTACK_ROAR_75*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_75(blendTime)));
	}

	protected void Battle_Roar_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84766F25L /*Battle_Roar_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x5F56C63AL /*_isRageLevel*/, 2);
		if (getVariable(0x6AE7D30AL /*_isRageCount*/) == 2) {
			setVariable(0x6AE7D30AL /*_isRageCount*/, getVariable(0x6AE7D30AL /*_isRageCount*/) - 1);
		}
		doAction(2619115158L /*BATTLE_ATTACK_ROAR_50*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_50(blendTime)));
	}

	protected void Battle_Roar_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5E9825B9L /*Battle_Roar_25*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x5F56C63AL /*_isRageLevel*/, 3);
		if (getVariable(0x6AE7D30AL /*_isRageCount*/) == 1) {
			setVariable(0x6AE7D30AL /*_isRageCount*/, getVariable(0x6AE7D30AL /*_isRageCount*/) - 1);
		}
		doAction(3309335353L /*BATTLE_ATTACK_ROAR_25*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_25(blendTime)));
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
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xD61E465EL /*Move_Return*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
