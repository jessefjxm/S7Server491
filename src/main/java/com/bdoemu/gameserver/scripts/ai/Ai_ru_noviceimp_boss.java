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
@IAIName("ru_noviceimp_boss")
public class Ai_ru_noviceimp_boss extends CreatureAI {
	public Ai_ru_noviceimp_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0x7398A4FL /*_IsRoar*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 0);
		setVariable(0x7AC48F6EL /*_JumpAttack2Count*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, 0);
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, 0);
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, 0);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, 0);
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckSpawn_Logic(blendTime), 100));
	}

	protected void CheckSpawn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2803EDBL /*CheckSpawn_Logic*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 23061)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		changeState(state -> Notifier_Logic(blendTime));
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_IMPBOSS_SPAWN");
		changeState(state -> Wait(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_Roar(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 500, false, ENaviType.ground, () -> {
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
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0x7AC48F6EL /*_JumpAttack2Count*/, 3);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x7398A4FL /*_IsRoar*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Return_Wait(blendTime), 1000)));
	}

	protected void Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x95AE08DEL /*Return_Wait*/);
		doAction(2447201636L /*RETURN_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
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
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
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
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(60)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getDistanceToTarget(target) > 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Dash2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 40 && getVariable(0x7398A4FL /*_IsRoar*/) == 0) {
			if (changeState(state -> Attack_Roar(0.4)))
				return;
		}
		if (getVariable(0x7AC48F6EL /*_JumpAttack2Count*/) >= 3 && getVariable(0x3F487035L /*_HP*/) <= 75 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Jump2(0.3)))
				return;
		}
		if (getVariable(0x7AC48F6EL /*_JumpAttack2Count*/) >= 2 && getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Jump2(0.3)))
				return;
		}
		if (getVariable(0x7AC48F6EL /*_JumpAttack2Count*/) >= 1 && getVariable(0x3F487035L /*_HP*/) <= 25 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Jump2(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0xE73206BFL /*_JumpAttackCount*/) >= 7 && target != null && getDistanceToTarget(target) < 850 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 0 && target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 1 && target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal3_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_1(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 350) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Wait_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7603F562L /*Battle_Wait_Roar*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(60)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x7398A4FL /*_IsRoar*/) == 0) {
			if (changeState(state -> Attack_Roar(0.4)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Combination_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0xE73206BFL /*_JumpAttackCount*/) >= 10 && getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 850 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 0 && target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2_Roar_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 1 && target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal3_Roar_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_Roar_1(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 350) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		doAction(3873587756L /*BATTLE_WAIT_ROAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Roar(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getDistanceToTarget(target) > 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Dash2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 850 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 0 && target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 1 && target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal3_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_1(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 0 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Move_Chaser_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x7D0CABC0L /*Move_Chaser_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getDistanceToTarget(target) > 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Dash2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Combination_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 850 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 0 && target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2F6E584FL /*_BattleCount*/) == 1 && target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal3_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_1(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 0 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		doAction(3372977862L /*BATTLE_WALK_ROAR*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser_Roar(blendTime), 100)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getDistanceToTarget(target) > 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Dash2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) >= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0xE73206BFL /*_JumpAttackCount*/) >= 5 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_1(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 0 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Chaser_Run_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x91F41DD4L /*Chaser_Run_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getDistanceToTarget(target) > 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Dash2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Combination_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) >= 50 && getVariable(0xBEE22793L /*_CombinationCount*/) >= 10 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Combination_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0xE73206BFL /*_JumpAttackCount*/) >= 10 && getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_1(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 0 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.4)))
				return;
		}
		doAction(1496808095L /*BATTLE_RUN_ROAR*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run_Roar(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(1915812192L /*BATTLE_TURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Battle_Turn_Right_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Battle_Turn_Right_180(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Left_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x50CAE33DL /*Battle_Turn_Left_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(927517472L /*BATTLE_TURN_LEFT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Roar_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE8144AA6L /*Battle_Turn_Right_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(115396126L /*BATTLE_TURN_RIGHT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Roar_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_Roar_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB0DD8915L /*Battle_Turn_Right_Roar_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(234156519L /*BATTLE_TURN_180_RIGHT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Roar_Logic(blendTime)));
	}

	protected void Turn_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x79BFF61EL /*Turn_Roar_Logic*/);
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Battle_Turn_Right_Roar_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Battle_Turn_Right_Roar_180(0.3)))
				return;
		}
		doAction(3873587756L /*BATTLE_WAIT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Roar(blendTime)));
	}

	protected void DeadHand_Summon_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0xF04930CDL /*DeadHand_Summon_Ready*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(1901832297L /*DEADHAND_SUMMON_READY*/, blendTime, onDoActionEnd -> revive(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> DeadHand_Summon(blendTime), 1000)));
	}

	protected void DeadHand_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8E76F4F0L /*DeadHand_Summon*/);
		doAction(1629496095L /*DEADHAND_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_IMPBOSS_TIMEOUT");
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_End_Logic(blendTime), 1000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Special_Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF3EFF55L /*Special_Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB97BF3E2L /*Special_Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_End(blendTime), 9000));
	}

	protected void Special_Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC21C1C3DL /*Special_Damage_Stun_End*/);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, getTime());
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_End_Logic(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x698D06BDL /*Special_Damage_Stun_Logic*/);
		if(Rnd.getChance(80)) {
			if (changeState(state -> Stun_Combination(0.3)))
				return;
		}
		changeState(state -> Special_Damage_Stun_End(blendTime));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		setVariable(0x7398A4FL /*_IsRoar*/, 1);
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF7F3CFB0L /*Attack_Normal_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal_2(blendTime)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Attack_Normal_1(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal(blendTime));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB0F6C1F5L /*Attack_Normal_1*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4BCE7F4DL /*Attack_Normal_2*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF5039702L /*Attack_Normal2_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal2_2(blendTime)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Attack_Normal2_1(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal2(blendTime));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5DBC5AFEL /*Attack_Normal2_1*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2002899555L /*ATTACK_NORMAL2_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDFEAC91BL /*Attack_Normal2_2*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2067287384L /*ATTACK_NORMAL2_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x70EC00E6L /*Attack_Normal3_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal3_2(blendTime)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Attack_Normal3_1(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal3(blendTime));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2957630933L /*ATTACK_NORMAL3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8416FF8L /*Attack_Normal3_1*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(3777145387L /*ATTACK_NORMAL3_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDAE74F2EL /*Attack_Normal3_2*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(4274451916L /*ATTACK_NORMAL3_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 0);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) - 2);
		doAction(461173768L /*ATTACK_JUMP_READY*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2905D73L /*Attack_Combination*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(3519135295L /*ATTACKSKILL_COMBINATION_READY*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Stun_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9A612AF4L /*Stun_Combination*/);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(3519135295L /*ATTACKSKILL_COMBINATION_READY*/, blendTime, onDoActionEnd -> changeState(state -> Special_Combination_Logic(blendTime)));
	}

	protected void Special_Combination_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6E5811E3L /*Special_Combination_Logic*/);
		changeState(state -> Special_Damage_Stun_End(blendTime));
	}

	protected void Attack_Normal_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3F57C30AL /*Attack_Normal_Roar_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal_Roar_1(blendTime)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal_Roar_2(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal_Roar(blendTime));
	}

	protected void Attack_Normal_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x788C919BL /*Attack_Normal_Roar*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(224380186L /*ATTACK_NORMAL_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_Roar_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5B0ED37L /*Attack_Normal_Roar_1*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(2540790267L /*ATTACK_NORMAL_ROAR_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_Roar_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x917284F6L /*Attack_Normal_Roar_2*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(3549063602L /*ATTACK_NORMAL_ROAR_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3C20A6L /*Attack_Normal2_Roar_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal2_Roar_1(blendTime)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal2_Roar_2(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal2_Roar(blendTime));
	}

	protected void Attack_Normal2_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE31A0DEEL /*Attack_Normal2_Roar*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(678333289L /*ATTACK_NORMAL2_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Roar_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x85C445E5L /*Attack_Normal2_Roar_1*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(3681108972L /*ATTACK_NORMAL2_ROAR_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Roar_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x15F6B4F4L /*Attack_Normal2_Roar_2*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 1);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(1467761491L /*ATTACK_NORMAL2_ROAR_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF22CD049L /*Attack_Normal3_Roar_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal3_Roar_1(blendTime)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Normal3_Roar_2(blendTime)))
				return;
		}
		changeState(state -> Attack_Normal3_Roar(blendTime));
	}

	protected void Attack_Normal3_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6655F2D8L /*Attack_Normal3_Roar*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(771048656L /*ATTACK_NORMAL3_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_Roar_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17CA4721L /*Attack_Normal3_Roar_1*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(3720165145L /*ATTACK_NORMAL3_ROAR_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_Roar_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x578C4CF9L /*Attack_Normal3_Roar_2*/);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		doAction(1286958793L /*ATTACK_NORMAL3_ROAR_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x11F71E61L /*Attack_Jump2*/);
		if (getVariable(0x7AC48F6EL /*_JumpAttack2Count*/) >= -1) {
			setVariable(0x7AC48F6EL /*_JumpAttack2Count*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) - 1);
		}
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, getVariable(0xE73206BFL /*_JumpAttackCount*/) + 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) + 1);
		doAction(2350417439L /*ATTACK_JUMP2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump2_1(blendTime)));
	}

	protected void Attack_Jump2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF5D9D848L /*Attack_Jump2_1*/);
		doAction(2751095920L /*ATTACK_JUMP2_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump2_3_Delay(blendTime)));
	}

	protected void Attack_Jump2_3_Delay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5D06E8E6L /*Attack_Jump2_3_Delay*/);
		doAction(2736825231L /*ATTACK_JUMP2_3_DELAY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Jump2_4(blendTime), 1000));
	}

	protected void Attack_Jump2_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3CD36607L /*Attack_Jump2_4*/);
		doAction(444465079L /*ATTACK_JUMP2_4*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump2_5_Delay(blendTime)));
	}

	protected void Attack_Jump2_5_Delay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x14075964L /*Attack_Jump2_5_Delay*/);
		doAction(2480562441L /*ATTACK_JUMP2_5_DELAY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Jump2_6(blendTime), 1000));
	}

	protected void Attack_Jump2_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x700C1BC6L /*Attack_Jump2_6*/);
		doAction(800090207L /*ATTACK_JUMP2_6*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1345770L /*Attack_Jump_Roar*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 0);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) - 2);
		doAction(3340181801L /*ATTACK_JUMP_READY_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Combination_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6EB2F170L /*Attack_Combination_Roar*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(2935470715L /*ATTACKSKILL_COMBINATION_READY_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Stun_Combination_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x59C62F47L /*Stun_Combination_Roar*/);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(2935470715L /*ATTACKSKILL_COMBINATION_READY_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Special_Combination_Roar_Logic(blendTime)));
	}

	protected void Special_Combination_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x54CD4786L /*Special_Combination_Roar_Logic*/);
		changeState(state -> Special_Damage_Stun_End(blendTime));
	}

	protected void Attack_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4AA1775EL /*Attack_Dash*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(415291795L /*ATTACK_DASH*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Dash_End2(blendTime)));
	}

	protected void Attack_Dash_End2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7397B703L /*Attack_Dash_End2*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(3364153005L /*ATTACK_DASH_END2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Dash2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD2093065L /*Attack_Dash2*/);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 3);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		doAction(1405452937L /*ATTACK_DASH2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x7398A4FL /*_IsRoar*/) == 1) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
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
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
