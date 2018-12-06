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
@IAIName("fieldancientworm_boss")
public class Ai_fieldancientworm_boss extends CreatureAI {
	public Ai_fieldancientworm_boss(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0xC093931CL /*_isUnderMode*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0x492117ADL /*_UnderAttackCount*/, 0);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, 0);
		setVariable(0x3BD27844L /*_HPAttackCount*/, 0);
		setVariable(0x78DED487L /*_InhaleAttack*/, 0);
		setVariable(0x8312D5E6L /*_phase2*/, 0);
		setVariable(0x42BFA62L /*_Inhale_Time_Start*/, 0);
		setVariable(0x5271B87DL /*_Inhale_Time_Ing*/, 0);
		setVariable(0xCC534B99L /*_Inhale_Time_End*/, 0);
		setVariable(0x573A0F0L /*_Under_StartTime*/, 0);
		setVariable(0x7C3DAA05L /*_Under_IngTime*/, 0);
		setVariable(0x5315610EL /*_Under_EndTime*/, 0);
		setVariable(0xB1D5D591L /*_Deep_Under_StartTime*/, 0);
		setVariable(0xF8904CE7L /*_Deep_Under_IngTime*/, 0);
		setVariable(0xD98563C9L /*_Deep_Under_EndTime*/, 0);
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
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23073)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23916)) {
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
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 16000 && getTargetCharacterKey(object) == 23916)) {
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
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_ANCIENTWORM_SQAWN");
		changeState(state -> Start_Action_Wait(blendTime));
	}

	protected void Start_Action_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5A1E79F6L /*Start_Action_Wait*/);
		if(getCallCount() == 2) {
			if (changeState(state -> Start_Action_Hold_On(0.3)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Wait(blendTime), 1000));
	}

	protected void Start_Action_Hold_On(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3EEA0F76L /*Start_Action_Hold_On*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(1668635516L /*START_ACTION_HOLD_ON*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1500));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Start_Action(0.3)))
				return;
		}
		scheduleState(state -> Start_Action_Hold_On(blendTime), 100);
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		setVariable(0x42BFA62L /*_Inhale_Time_Start*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 23000));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait_P2(blendTime)))
				return;
		}
		if (getVariable(0xC093931CL /*_isUnderMode*/) == 1) {
			if (changeState(state -> Deep_Under_Walk(blendTime)))
				return;
		}
		changeState(state -> Attack_End_Logic(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0)) {
					if (changeState(state -> Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0)) {
					if (changeState(state -> Battle_Wait_P2(0.3)))
						return;
				}
			}
		}
		if (getVariable(0xC093931CL /*_isUnderMode*/) == 1) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0)) {
					if (changeState(state -> Deep_Under_Walk(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait_P2(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0xC093931CL /*_isUnderMode*/) == 1) {
			if (changeState(state -> Under_End(blendTime)))
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
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3600) {
			if (changeState(state -> Move_Return(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Under_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B164A18L /*Under_Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Deep_Under_Walk(0.2)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Return(0.4)))
				return;
		}
		doAction(4243387614L /*BATTLE_WAIT_UNDER*/, blendTime, onDoActionEnd -> scheduleState(state -> Under_Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xC093931CL /*_isUnderMode*/, 1);
		setVariable(0xC093931CL /*_isUnderMode*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
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
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_P2(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			changeState(state -> Under_Str(blendTime));
		});
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_ANCIENTWORM_TIMEOUT");
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
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

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
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
		if (getVariable(0xC093931CL /*_isUnderMode*/) == 1) {
			if (changeState(state -> Deep_Under_Walk(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait_P2(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3600) {
			doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath_Logic_Under(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x856801BFL /*FailFindPath_Logic_Under*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget_Under(0.3)))
				return;
		}
		changeState(state -> FailFindPathToTarget_Logic(blendTime));
	}

	protected void FailFindPathToTarget_Under(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7F727A22L /*FailFindPathToTarget_Under*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(4243387614L /*BATTLE_WAIT_UNDER*/, blendTime, onDoActionEnd -> scheduleState(state -> FailFindPathToTarget_Logic_Under(blendTime), 1000));
	}

	protected void FailFindPathToTarget_Logic_Under(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xACBA1EE1L /*FailFindPathToTarget_Logic_Under*/);
		if (getVariable(0xC093931CL /*_isUnderMode*/) == 1) {
			if (changeState(state -> Deep_Under_Walk(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait_P2(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3600) {
			doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		}
		changeState(state -> Under_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xC093931CL /*_isUnderMode*/, 0);
		setVariable(0x5271B87DL /*_Inhale_Time_Ing*/, getTime());
		setVariable(0xCC534B99L /*_Inhale_Time_End*/, getVariable(0x5271B87DL /*_Inhale_Time_Ing*/) - getVariable(0x42BFA62L /*_Inhale_Time_Start*/));
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) > 12) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 4000) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 75 && getVariable(0x3BD27844L /*_HPAttackCount*/) == 0) {
			if (changeState(state -> Attack_Inhale2(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0x3BD27844L /*_HPAttackCount*/) == 1) {
			if (changeState(state -> Under_Str2(0.3)))
				return;
		}
		if (getVariable(0xCC534B99L /*_Inhale_Time_End*/) > 60000 && target != null && getDistanceToTarget(target) < 5000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Inhale(0.3)))
					return;
			}
		}
		if (getVariable(0x8CE8C824L /*_EmissionAttack*/) >= 1) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) >= 2500 && getDistanceToTarget(object) <= 5000 && getTargetHeightdiff(object) > getVariable(0x27274876L /*_TargetBodyHeight*/) && getTargetHeightdiff(object) < getVariable(0x3715AB9DL /*_MyBodyHeight*/))) {
					if (changeState(state -> Attack_Emission(0.3)))
						return;
				}
			}
		}
		if (target != null && getDistanceToTarget(target) > 2500 && target != null && getDistanceToTarget(target) <= 5000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) < 900 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait_P2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1D04E1CAL /*Battle_Wait_P2*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xC093931CL /*_isUnderMode*/, 0);
		setVariable(0x5271B87DL /*_Inhale_Time_Ing*/, getTime());
		setVariable(0xCC534B99L /*_Inhale_Time_End*/, getVariable(0x5271B87DL /*_Inhale_Time_Ing*/) - getVariable(0x42BFA62L /*_Inhale_Time_Start*/));
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) > 12) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 4000) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 25 && getVariable(0x3BD27844L /*_HPAttackCount*/) == 2) {
			if (changeState(state -> Attack_Inhale3(0.3)))
				return;
		}
		if (getVariable(0xCC534B99L /*_Inhale_Time_End*/) > 60000 && getVariable(0x78DED487L /*_InhaleAttack*/) == 0 && target != null && getDistanceToTarget(target) < 5000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Lightning(0.3)))
					return;
			}
		}
		if (getVariable(0xCC534B99L /*_Inhale_Time_End*/) > 60000 && getVariable(0x78DED487L /*_InhaleAttack*/) == 1 && target != null && getDistanceToTarget(target) < 5000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Inhale(0.3)))
					return;
			}
		}
		if (getVariable(0x8CE8C824L /*_EmissionAttack*/) >= 1) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) >= 2500 && getDistanceToTarget(object) <= 5000 && getTargetHeightdiff(object) > getVariable(0x27274876L /*_TargetBodyHeight*/) && getTargetHeightdiff(object) < getVariable(0x3715AB9DL /*_MyBodyHeight*/))) {
					if (changeState(state -> Attack_Emission(0.3)))
						return;
				}
			}
		}
		if (target != null && getDistanceToTarget(target) > 2500 && target != null && getDistanceToTarget(target) <= 5000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Under_Str(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) < 900 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal2_Logic(0)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 2500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Attack_Normal3_Logic(0)))
					return;
			}
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_P2(blendTime), 100));
	}

	protected void Attack_Normal2_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF5039702L /*Attack_Normal2_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Normal2_Hideen(0.3)))
				return;
		}
		if (changeState(state -> Attack_Normal2(0.3)))
			return;
		changeState(state -> Battle_Wait_P2(blendTime));
	}

	protected void Attack_Normal3_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x70EC00E6L /*Attack_Normal3_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Normal3_Hideen(0.3)))
				return;
		}
		if (changeState(state -> Attack_Normal3(0.3)))
			return;
		changeState(state -> Battle_Wait_P2(blendTime));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Hideen(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7EC9CE4AL /*Attack_Normal2_Hideen*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3955284699L /*BATTLE_ATTACK2_HIDDEN*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3_Hideen(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA2526B84L /*Attack_Normal3_Hideen*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2465629947L /*BATTLE_ATTACK3_HIDDEN*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Inhale(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB68A7E21L /*Attack_Inhale*/);
		setVariable(0x78DED487L /*_InhaleAttack*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, getVariable(0x8CE8C824L /*_EmissionAttack*/) + 3);
		setVariable(0x42BFA62L /*_Inhale_Time_Start*/, getTime());
		doAction(63557765L /*INHALE_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Emission(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEE3290BBL /*Attack_Emission*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, getVariable(0x8CE8C824L /*_EmissionAttack*/) - 1);
		doAction(3636538705L /*EMISSION_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Inhale2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4F04EC5AL /*Attack_Inhale2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, getVariable(0x8CE8C824L /*_EmissionAttack*/) + 3);
		setVariable(0x3BD27844L /*_HPAttackCount*/, 1);
		doAction(63557765L /*INHALE_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Inhale3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x133DBE3AL /*Attack_Inhale3*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, getVariable(0x8CE8C824L /*_EmissionAttack*/) + 3);
		setVariable(0x3BD27844L /*_HPAttackCount*/, 0);
		doAction(63557765L /*INHALE_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Lightning(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7238A3DAL /*Attack_Lightning*/);
		setVariable(0x78DED487L /*_InhaleAttack*/, 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x8CE8C824L /*_EmissionAttack*/, getVariable(0x8CE8C824L /*_EmissionAttack*/) + 3);
		setVariable(0x42BFA62L /*_Inhale_Time_Start*/, getTime());
		doAction(705255295L /*LIGHTNING_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Under_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x687BE13L /*Under_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xC093931CL /*_isUnderMode*/, 1);
		setVariable(0x492117ADL /*_UnderAttackCount*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(3589734263L /*UNDERGROUND_STR*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			changeState(state -> Deep_Under_Walk(blendTime));
		});
	}

	protected void Under_Str2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x18F11008L /*Under_Str2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8312D5E6L /*_phase2*/, 1);
		setVariable(0x78DED487L /*_InhaleAttack*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3BD27844L /*_HPAttackCount*/, 2);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		doAction(4040028869L /*PHASESHIFT_ATTACK*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			changeState(state -> Under_Str2_P2(blendTime));
		});
	}

	protected void Under_Str2_P2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA6B180C9L /*Under_Str2_P2*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(1703761260L /*PHASESHIFT_ATTACK_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Deep_Under_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xA33DE1D9L /*Deep_Under_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 50 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Long_Under_Attack1(0)))
					return;
			}
		}
		if (getVariable(0x492117ADL /*_UnderAttackCount*/) >= 4) {
			if(Rnd.getChance(100)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) >= 0)) {
					if (changeState(state -> Under_End(0.3)))
						return;
				}
			}
		}
		if (getVariable(0x492117ADL /*_UnderAttackCount*/) >= 2) {
			if(Rnd.getChance(20)) {
				if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 475 && getTargetHeightdiff(object) > getVariable(0x27274876L /*_TargetBodyHeight*/) && getTargetHeightdiff(object) < getVariable(0x3715AB9DL /*_MyBodyHeight*/))) {
					if (changeState(state -> Under_End(0.3)))
						return;
				}
			}
		}
		if (target != null && getDistanceToTarget(target) <= 50 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Under_Attack1(0)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Deep_Under_Walk(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Deep_Under_Walk(0.3)))
				return;
		}
		doAction(2857307552L /*UNDERGROUND_DEEP_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic_Under(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Deep_Under_Walk(blendTime), 100)));
	}

	protected void Under_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x925FB0B0L /*Under_End*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(555451679L /*UNDERGROUND_END*/, blendTime, onDoActionEnd -> changeState(state -> Under_End_Logic(blendTime)));
	}

	protected void Under_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2E07A58L /*Under_End_Logic*/);
		setVariable(0xC093931CL /*_isUnderMode*/, 0);
		if (getVariable(0x8312D5E6L /*_phase2*/) == 0 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x8312D5E6L /*_phase2*/) == 1 && getVariable(0xC093931CL /*_isUnderMode*/) == 0) {
			if (changeState(state -> Battle_Wait_P2(blendTime)))
				return;
		}
		changeState(state -> Battle_Wait_P2(blendTime));
	}

	protected void Under_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x47D3CDFDL /*Under_Attack1*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(420095113L /*BATTLE_DEEPUNDER_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Under_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6AD6F3EL /*Under_Attack2*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(2787916246L /*BATTLE_DEEPUNDER_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Under_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD3DA25L /*Under_Attack3*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(118927469L /*BATTLE_DEEPUNDER_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Under_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x14AF57DAL /*Long_Under_Attack1*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(3191511147L /*BATTLE_DEEPUNDER_ATTACK_LONG_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Under_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3214F7L /*Long_Under_Attack2*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(4074520814L /*BATTLE_DEEPUNDER_ATTACK_LONG_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Under_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFDAC4A07L /*Long_Under_Attack3*/);
		setVariable(0x492117ADL /*_UnderAttackCount*/, getVariable(0x492117ADL /*_UnderAttackCount*/) + 1);
		doAction(219394378L /*BATTLE_DEEPUNDER_ATTACK_LONG_3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
