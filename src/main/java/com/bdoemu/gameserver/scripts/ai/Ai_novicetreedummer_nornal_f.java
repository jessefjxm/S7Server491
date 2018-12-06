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
@IAIName("novicetreedummer_nornal_f")
public class Ai_novicetreedummer_nornal_f extends CreatureAI {
	public Ai_novicetreedummer_nornal_f(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xC6A18D4DL /*_IsSummon*/, getVariable(0x94DB406BL /*AI_IsSummon*/));
		setVariable(0x99703876L /*_SummonCount*/, 0);
		setVariable(0x1A92F8C5L /*_IsCure*/, getVariable(0x25647528L /*AI_IsCure*/));
		setVariable(0x13CF182BL /*_CureCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Start(blendTime), 500));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1800 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Summon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB8F870L /*Summon_Start*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1000));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		scheduleState(state -> Wait(blendTime), 100);
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 1800 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 500, false, ENaviType.ground, () -> {
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
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
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
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
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
		if (target != null && getDistanceToTarget(target) > 400 && getSelfCombinePoint() != 13) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Walk_Back(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 450 && target != null && getAngleToTarget(target) <= 60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 450 && target != null && getAngleToTarget(target) >= -60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 70 && target != null && getAngleToTarget(target) >= -60 && target != null && getAngleToTarget(target) <= 60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Skill1(0.3)))
					return;
			}
		}
		if (getVariable(0x1A92F8C5L /*_IsCure*/) == 1 && getVariable(0x13CF182BL /*_CureCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 30) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Cure(0.3)))
					return;
			}
		}
		if (getVariable(0xC6A18D4DL /*_IsSummon*/) == 1 && getVariable(0x99703876L /*_SummonCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 35) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Summon_Creature(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -40 && target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Turn_Left(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 40 && target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Turn_Right(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 70 && target != null && getAngleToTarget(target) >= -60 && target != null && getAngleToTarget(target) <= 60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Skill1(0.3)))
					return;
			}
		}
		if (getVariable(0x1A92F8C5L /*_IsCure*/) == 1 && getVariable(0x13CF182BL /*_CureCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 30) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Cure(0.3)))
					return;
			}
		}
		if (getVariable(0xC6A18D4DL /*_IsSummon*/) == 1 && getVariable(0x99703876L /*_SummonCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 35) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Summon_Creature(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x3126FF0FL /*isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getDistanceToTarget(target) >= 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 70 && target != null && getAngleToTarget(target) >= -60 && target != null && getAngleToTarget(target) <= 60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 450 && getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && getVariable(0x3F487035L /*_HP*/) <= 100 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Skill1(0.3)))
					return;
			}
		}
		if (getVariable(0x1A92F8C5L /*_IsCure*/) == 1 && getVariable(0x13CF182BL /*_CureCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 30) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Cure(0.3)))
					return;
			}
		}
		if (getVariable(0xC6A18D4DL /*_IsSummon*/) == 1 && getVariable(0x99703876L /*_SummonCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 35) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Summon_Creature(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(1509390199L /*BATTLE_MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
		doAction(1156785166L /*ATTACK_NORMAL_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
		doAction(677153502L /*ATTACK_NORMAL_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
		doAction(3190773862L /*ATTACK_COMBO_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5CA8868L /*Attack_Normal4*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
		doAction(745936669L /*ATTACK_NORMAL_R2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Skill1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE0ED2EE9L /*Attack_Skill1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3072834598L /*ATTACK_SKILL_READY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Cure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x14F0A9C9L /*Cure*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, 0);
		doAction(3434304799L /*CURE_READY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Summon_Creature(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x51ADC0A6L /*Summon_Creature*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x99703876L /*_SummonCount*/, 0);
		doAction(512753472L /*SUMMON_CREATURE*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Creature_Logic(blendTime)));
	}

	protected void Summon_Creature_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x53456008L /*Summon_Creature_Logic*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		changeState(state -> Battle_Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0xF1ED1C65L /*Start_Action_Logic*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
