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
@IAIName("desertdragon_boss_new")
public class Ai_desertdragon_boss_new extends CreatureAI {
	public Ai_desertdragon_boss_new(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x347D80C5L /*_isFlyingMode*/, 0);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xEE30ABEAL /*_FlyingAttackCount*/, 0);
		setVariable(0xC9441251L /*_Flying_StartTime*/, 0);
		setVariable(0x6C5AEA35L /*_Flying_IngTime*/, 0);
		setVariable(0xDE434BB1L /*_Flying_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckSpawn_Logic(blendTime), 100));
	}

	protected void CheckSpawn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2803EDBL /*CheckSpawn_Logic*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23032)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23914)) {
			if (changeState(state -> Waiting(0.3)))
				return;
		}
		changeState(state -> Notifier_Logic(blendTime));
	}

	protected void Waiting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x138AF201L /*Waiting*/);
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23914)) {
				if (changeState(state -> Waiting(0.3)))
					return;
			}
			scheduleState(state -> Notifier_Logic(blendTime), 1000);
		});
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_DESERTDRAGON_SQAWN");
		changeState(state -> Start_Action_Wait(blendTime));
	}

	protected void Start_Action_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5A1E79F6L /*Start_Action_Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Start_Action(0.3)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Wait(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1500));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		scheduleState(state -> Wait(blendTime), 100);
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if (changeState(state -> Flying_Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getDistanceToTarget(object) >= 1500 && getTargetHp(object) > 0)) {
					if (changeState(state -> Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 1500 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
					if (changeState(state -> Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getDistanceToTarget(object) >= 1500 && getTargetHp(object) > 0)) {
					if (changeState(state -> Flying_Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 1500 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
					if (changeState(state -> Flying_Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Flying_Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if (changeState(state -> Flying_Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		scheduleState(state -> Move_Return(blendTime), 1000);
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1000, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
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
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_DESERTDRAGON_TIMEOUT");
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
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
		changeState(state -> FailFindPathToTarget_Logic(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
				if (changeState(state -> Flying_Move_Return(blendTime)))
					return;
			}
			if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
				if (changeState(state -> Move_Return(blendTime)))
					return;
			}
			scheduleState(state -> Move_Return(blendTime), 500);
		});
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
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 1) {
			if (changeState(state -> Flying_Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x347D80C5L /*_isFlyingMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x347D80C5L /*_isFlyingMode*/, 0);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) > 30 && getPartyMembersCount()<= 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Tack_Off(0.3)))
					return;
			}
		}
		if(Rnd.getChance(5)) {
			if (changeState(state -> Attack_Jump_L(0.3)))
				return;
		}
		if(Rnd.getChance(5)) {
			if (changeState(state -> Attack_Jump_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 25 && target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_L(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -25 && target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_R(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 75 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) <= 0 && getPartyMembersCount()== 0) {
			if (changeState(state -> Attack_Summon(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) == 1 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 25 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) == 2 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Jump_B(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 1200 && target != null && getDistanceToTarget(target) < 1700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_F(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getAngleToTarget(target) <= 0 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Chaser_Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE184D264L /*Chaser_Run_Stop*/);
		doAction(3532557289L /*CHASER_RUN_STOP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(553295030L /*BATTLE_ATTACK_L*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1450156184L /*BATTLE_ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5404099FL /*Attack_Turn_L*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(8553849L /*BATTLE_ATTACK_TURNL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E7DE5BDL /*Attack_Turn_R*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(545568805L /*BATTLE_ATTACK_TURNR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDCB9BE7EL /*Attack_Jump_F*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3872114358L /*BATTLE_JUMP_F*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x22346455L /*Attack_Jump_B*/);
		doAction(1664194426L /*BATTLE_JUMP_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Breath(blendTime)));
	}

	protected void Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D9470D7L /*Attack_Breath*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(811060020L /*BATTLE_BREATH_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD0F781DDL /*Attack_Jump_L*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(801180298L /*JUMP_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD33E54E7L /*Attack_Jump_R*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3508935626L /*JUMP_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Tack_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x95AA6E1EL /*Tack_Off*/);
		setVariable(0x347D80C5L /*_isFlyingMode*/, 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xC9441251L /*_Flying_StartTime*/, getTime());
		doAction(556456020L /*FLYING_TAKE_OFF_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Tack_Off_Ing(blendTime), 1000));
	}

	protected void Tack_Off_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC7B98003L /*Tack_Off_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x347D80C5L /*_isFlyingMode*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(1759787312L /*FLYING_TAKE_OFF_ING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 10, 80, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000)));
	}

	protected void Landing_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA725A3L /*Landing_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x347D80C5L /*_isFlyingMode*/, 0);
		setVariable(0xEE30ABEAL /*_FlyingAttackCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(116698182L /*LANDING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 150, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Landing(blendTime), 1000)));
	}

	protected void Landing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBE797C29L /*Landing*/);
		setVariable(0x347D80C5L /*_isFlyingMode*/, 0);
		doAction(2038631646L /*LANDING_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Flying_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9526A4B9L /*Flying_Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x347D80C5L /*_isFlyingMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x6C5AEA35L /*_Flying_IngTime*/, getTime());
		setVariable(0xDE434BB1L /*_Flying_EndTime*/, getVariable(0x6C5AEA35L /*_Flying_IngTime*/) - getVariable(0xC9441251L /*_Flying_StartTime*/));
		if (getVariable(0xDE434BB1L /*_Flying_EndTime*/) >= 180000) {
			if (changeState(state -> Landing_Ing(0.3)))
				return;
		}
		if (getVariable(0xEE30ABEAL /*_FlyingAttackCount*/) > 5) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Landing_Ing(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Flying_Random(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Flying_Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 4500) {
			if (changeState(state -> Attack_FlyIng_FireBall(0.3)))
				return;
		}
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000));
	}

	protected void Flying_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x67926D3AL /*Flying_Random*/);
		if (target != null && getDistanceToTarget(target) < 2500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_FlyIng_FireBall(0.3)))
					return;
			}
		}
		doAction(1917870029L /*FLYING_MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 2000, 2300, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_End_Logic(blendTime), 1000)));
	}

	protected void Flying_Random_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBDD8214FL /*Flying_Random_Up*/);
		doAction(1917870029L /*FLYING_MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 400, 80, 150, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 500)));
	}

	protected void Flying_Random_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x541C29C5L /*Flying_Random_RetryCurveTurn*/);
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Random(blendTime), 500)));
	}

	protected void Flying_Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x79717AA0L /*Flying_Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1101834158L /*FLYING_MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000)));
	}

	protected void Flying_Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x51E17F2FL /*Flying_Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 2000) {
			if (changeState(state -> Attack_FlyIng_Breth(0.4)))
				return;
		}
		doAction(1917870029L /*FLYING_MOVE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Attack_End_Logic(blendTime), 100)));
	}

	protected void Attack_FlyIng_FireBall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC59DD57FL /*Attack_FlyIng_FireBall*/);
		setVariable(0xEE30ABEAL /*_FlyingAttackCount*/, getVariable(0xEE30ABEAL /*_FlyingAttackCount*/) + 1);
		doAction(1133120658L /*FLYING_BATTLE_ATTACK_FIREBALL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_FlyIng_Breth(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1187153EL /*Attack_FlyIng_Breth*/);
		setVariable(0xEE30ABEAL /*_FlyingAttackCount*/, getVariable(0xEE30ABEAL /*_FlyingAttackCount*/) + 4);
		doAction(983432511L /*FLYING_BATTLE_ATTACK_BREATH_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(8553849L /*BATTLE_ATTACK_TURNL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(545568805L /*BATTLE_ATTACK_TURNR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(4090772231L /*BATTLE_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Move_UnderGround_Str(blendTime)));
	}

	protected void Move_UnderGround_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8012F96L /*Move_UnderGround_Str*/);
		doAction(3223985144L /*MOVE_UNDERGROUND_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_UnderGround_Logic(blendTime), 1000));
	}

	protected void Move_UnderGround_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE7882558L /*Move_UnderGround_Logic*/);
		if(Rnd.getChance(40)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) <= 4000 && getDistanceToTarget(object) >= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Move_UnderGround_Chaser(0.3)))
					return;
			}
		}
		if(Rnd.getChance(40)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) <= 3000 && getDistanceToTarget(object) >= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Move_UnderGround_Chaser(0.3)))
					return;
			}
		}
		if(Rnd.getChance(40)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) <= 2000 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Move_UnderGround_Chaser(0.3)))
					return;
			}
		}
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) <= 1000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_UnderGround_Chaser(0.3)))
				return;
		}
		scheduleState(state -> Move_UnderGround_End(blendTime), 1000);
	}

	protected void Move_UnderGround_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x273F5DEL /*Move_UnderGround_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_UnderGround_End(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Move_UnderGround_End(0.3)))
				return;
		}
		doAction(2889086551L /*MOVE_UNDERGROUND_ING*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Move_UnderGround_End(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_UnderGround_Chaser(blendTime), 100)));
	}

	protected void Move_UnderGround_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x163A41BEL /*Move_UnderGround_End*/);
		doAction(4079838800L /*MOVE_UNDERGROUND_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
