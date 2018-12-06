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
@IAIName("novicelavapatriarchupgrade_boss")
public class Ai_novicelavapatriarchupgrade_boss extends CreatureAI {
	public Ai_novicelavapatriarchupgrade_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xEBAA9AEAL /*_isRage*/, 0);
		setVariable(0x365FC370L /*_SummonAttack_Count*/, 0);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, 0);
		setVariable(0x2B114CF7L /*_StampCombination_Count*/, 3);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x9A1C912EL /*_isInvisibleCount*/, 0);
		setVariable(0x3FBDA541L /*_isInvisibleMode*/, 0);
		setVariable(0xB371B2BBL /*_isInvisible_StartTime*/, 0);
		setVariable(0x6AE75601L /*_isInvisible_IngTime*/, 0);
		setVariable(0xD43F2B2L /*_isInvisible_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x256EC6C5L /*_isInitPhase1*/, 0);
		setVariable(0x3ACED901L /*_isInitPhase2*/, 0);
		setVariable(0x7B202151L /*_isInitPhase3*/, 0);
		setVariable(0x54437BFFL /*_isInitPhase4*/, 0);
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> TeleportToTarget(blendTime), 100));
	}

	protected void TeleportToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7408BA32L /*TeleportToTarget*/);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 100));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> CreateServant(blendTime), 1500));
	}

	protected void CreateServant(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9F461A9L /*CreateServant*/);
		doAction(3086887503L /*CREATE_SERVANT*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action_Logic(blendTime)));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 5000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		scheduleState(state -> Wait(blendTime), 100);
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-1000,1000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 500, false, ENaviType.ground, () -> {
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
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
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
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
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
		setBehavior(EAIBehavior.none);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x54437BFFL /*_isInitPhase4*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 20) {
			if (changeState(state -> Phase_4(blendTime)))
				return;
		}
		if (getVariable(0x7B202151L /*_isInitPhase3*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 40) {
			if (changeState(state -> Phase_3(blendTime)))
				return;
		}
		if (getVariable(0x3ACED901L /*_isInitPhase2*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 60) {
			if (changeState(state -> Phase_2(blendTime)))
				return;
		}
		if (getVariable(0x256EC6C5L /*_isInitPhase1*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 80) {
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
		if (Rnd.get(100) <= 33) {
			if (changeState(state -> NotifyPowerTower(blendTime)))
				return;
		}
		if (Rnd.get(100) <= 50) {
			if (changeState(state -> NotifyAvoidTower(blendTime)))
				return;
		}
		if (changeState(state -> NotifyCreateTower(blendTime)))
			return;
		changeState(state -> Battle_State(blendTime));
	}

	protected void Phase_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x188A6003L /*Phase_2*/);
		setVariable(0x3ACED901L /*_isInitPhase2*/, 1);
		if (Rnd.get(100) <= 33) {
			if (changeState(state -> NotifyPowerTower(blendTime)))
				return;
		}
		if (Rnd.get(100) <= 50) {
			if (changeState(state -> NotifyAvoidTower(blendTime)))
				return;
		}
		if (changeState(state -> NotifyCreateTower(blendTime)))
			return;
		changeState(state -> Battle_State(blendTime));
	}

	protected void Phase_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x646EFA54L /*Phase_3*/);
		setVariable(0x7B202151L /*_isInitPhase3*/, 1);
		if (Rnd.get(100) <= 33) {
			if (changeState(state -> NotifyPowerTower(blendTime)))
				return;
		}
		if (Rnd.get(100) <= 50) {
			if (changeState(state -> NotifyAvoidTower(blendTime)))
				return;
		}
		if (changeState(state -> NotifyCreateTower(blendTime)))
			return;
		changeState(state -> Battle_State(blendTime));
	}

	protected void Phase_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x119B0FL /*Phase_4*/);
		setVariable(0x54437BFFL /*_isInitPhase4*/, 1);
		if (Rnd.get(100) <= 33) {
			if (changeState(state -> NotifyPowerTower(blendTime)))
				return;
		}
		if (Rnd.get(100) <= 50) {
			if (changeState(state -> NotifyAvoidTower(blendTime)))
				return;
		}
		if (changeState(state -> NotifyCreateTower(blendTime)))
			return;
		changeState(state -> Battle_State(blendTime));
	}

	protected void Battle_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD320D983L /*Battle_State*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if (changeState(state -> Roar(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0x2B114CF7L /*_StampCombination_Count*/) >= 3 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_StampCombination(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x2B114CF7L /*_StampCombination_Count*/) >= 2 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_StampCombination(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 25 && getVariable(0x2B114CF7L /*_StampCombination_Count*/) >= 1 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_StampCombination(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getAngleToTarget(target) > 120 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Tail(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getAngleToTarget(target) < -120 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Tail(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 1500) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Range1(0.3)))
					return;
			}
		}
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 1 && target != null && getDistanceToTarget(target) < 400 && target != null && getAngleToTarget(target) < 60 && target != null && getAngleToTarget(target) > -60 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Breath(0.3)))
					return;
			}
		}
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 0 && target != null && getDistanceToTarget(target) < 700 && target != null && getAngleToTarget(target) < 50 && target != null && getAngleToTarget(target) > -50 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Stamp(0.3)))
					return;
			}
		}
		if (getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) >= 10 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Extensive_Logic(0.3)))
					return;
			}
		}
		if (getVariable(0x365FC370L /*_SummonAttack_Count*/) >= 10 && getPartyMembersCount()== 0 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Summon1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < 0 && target != null && getAngleToTarget(target) > -45 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < 45 && target != null && getAngleToTarget(target) > 0 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < -50 && target != null && getAngleToTarget(target) > -140 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Turn_L(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < 140 && target != null && getAngleToTarget(target) > 50 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Turn_R(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < -140 && target != null && getAngleToTarget(target) > -180 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Turn_180_L(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getAngleToTarget(target) < 180 && target != null && getAngleToTarget(target) > 140 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Turn_180_R(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
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
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 600) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 1500) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Range1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
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
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 1500) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Range1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60186BFFL /*Roar*/);
		setVariable(0xEBAA9AEAL /*_isRage*/, 1);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5404099FL /*Attack_Turn_L*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E7DE5BDL /*Attack_Turn_R*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_180_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF4BBE76EL /*Attack_Turn_180_L*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(2650559575L /*TURN_180_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_180_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8AE92FL /*Attack_Turn_180_R*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(3000360872L /*TURN_180_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()<= 5) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Summon1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2AF9384AL /*Attack_Summon1*/);
		setVariable(0x365FC370L /*_SummonAttack_Count*/, 0);
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Stamp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17EDD054L /*Attack_Stamp*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D9470D7L /*Attack_Breath*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(562050538L /*BATTLE_ATTACK5*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Range1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x56D3E488L /*Attack_Range1*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(2651160411L /*BATTLE_ATTACK6*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Tail(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9DA3C680L /*Attack_Tail*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(838500052L /*BATTLE_ATTACK7*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_StampCombination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA64CA6FEL /*Attack_StampCombination*/);
		if (getVariable(0x2B114CF7L /*_StampCombination_Count*/) >= 0) {
			setVariable(0x2B114CF7L /*_StampCombination_Count*/, getVariable(0x2B114CF7L /*_StampCombination_Count*/) - 1);
		}
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, getVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/) + 1);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(2468615530L /*BATTLE_ATTACK8*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Extensive_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2548FA50L /*Attack_Extensive_Logic*/);
		setVariable(0xE1A40CF9L /*_ExtensiveAttack_Count*/, 0);
		if(Rnd.getChance(33)) {
			if (changeState(state -> Attack_Extensive(0.3)))
				return;
		}
		if(Rnd.getChance(33)) {
			if (changeState(state -> Attack_Extensive2(0.3)))
				return;
		}
		if (changeState(state -> Attack_Extensive3(0.3)))
			return;
		changeState(state -> Attack_Extensive3(blendTime));
	}

	protected void Attack_Extensive(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD9183A53L /*Attack_Extensive*/);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(13371028L /*BATTLE_EXTENSIVEATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Extensive2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7B6B6055L /*Attack_Extensive2*/);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(3069742513L /*BATTLE_EXTENSIVEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Extensive3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8C7AD6F7L /*Attack_Extensive3*/);
		if (getPartyMembersCount()== 0) {
			setVariable(0x365FC370L /*_SummonAttack_Count*/, getVariable(0x365FC370L /*_SummonAttack_Count*/) + 1);
		}
		doAction(3521680701L /*BATTLE_EXTENSIVEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void PowerUpBuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0xA3DB2D87L /*PowerUpBuff*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(1460928270L /*POWER_UP_BUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void PowerUpBuffEnd(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0xDCF51EC9L /*PowerUpBuffEnd*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(116434830L /*POWER_UP_BUFF_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void AvoidBuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0x53582F37L /*AvoidBuff*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(508987170L /*AVOID_BUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void AvoidBuffEnd(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0x91A7E5C4L /*AvoidBuffEnd*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(2340580717L /*AVOID_BUFF_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void CreateServantBuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0xAA28FCABL /*CreateServantBuff*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(3776986072L /*CREATE_SERVANT_BUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void CreateServantEnd(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.skill);
		setState(0x89D1B9A2L /*CreateServantEnd*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_State(blendTime)))
				return;
		}
		doAction(2728860122L /*CREATE_SERVANT_BUFF_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_State(blendTime), 3000));
	}

	protected void NotifyPowerTower(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEA82ABE8L /*NotifyPowerTower*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().BuffPowerUp(getActor(), null));
		changeState(state -> Battle_State(blendTime));
	}

	protected void NotifyAvoidTower(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x14F4A3B1L /*NotifyAvoidTower*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().BuffAvoid(getActor(), null));
		changeState(state -> Battle_State(blendTime));
	}

	protected void NotifyCreateTower(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF88700F0L /*NotifyCreateTower*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().BuffCreateServant(getActor(), null));
		changeState(state -> Battle_State(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
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
	public EAiHandlerResult PowerUpBuffStart(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> PowerUpBuff(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult PowerUpBuffEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> PowerUpBuffEnd(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult AvoidBuffStart(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> AvoidBuff(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult AvoidBuffEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> AvoidBuffEnd(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult CreateServantBuffStart(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> CreateServantBuff(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
