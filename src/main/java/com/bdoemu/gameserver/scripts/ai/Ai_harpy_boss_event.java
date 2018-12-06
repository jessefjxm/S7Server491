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
@IAIName("harpy_boss_event")
public class Ai_harpy_boss_event extends CreatureAI {
	public Ai_harpy_boss_event(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x3CB677EBL /*_isFlyilngMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, 0);
		setVariable(0x983C4B84L /*_GeneralAttCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Notifier_Logic(blendTime), 500));
	}

	protected void CheckSpawn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2803EDBL /*CheckSpawn_Logic*/);
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 23060)) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		changeState(state -> Notifier_Logic(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if(Rnd.getChance(20)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getDistanceToTarget(object) >= 1500 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if(Rnd.getChance(20)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 1500 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_EVENT_KARANDA_SQAWN");
		changeState(state -> Start_Action(blendTime));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_KARANDA_TIMEOUT");
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
		setBehavior(EAIBehavior.idle);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1061244195L /*DETECT_ENEMY_END*/, blendTime, onDoActionEnd -> {
			setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> FailFindPathToTarget_Logic(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
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
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getDistanceToTarget(target) > 3500) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 75) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_General_Logic(0.3)))
					return;
			}
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 50) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_General_Logic(0.3)))
					return;
			}
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) >= 50 && getVariable(0x3F487035L /*_HP*/) <= 25) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_General_Logic(0.3)))
					return;
			}
		}
		if (getVariable(0x983C4B84L /*_GeneralAttCount*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 75) {
			if (changeState(state -> Attack_Phase_General(0.3)))
				return;
		}
		if (getVariable(0x983C4B84L /*_GeneralAttCount*/) == 1 && getVariable(0x3F487035L /*_HP*/) <= 50) {
			if (changeState(state -> Attack_Phase_General2(0.3)))
				return;
		}
		if (getVariable(0x983C4B84L /*_GeneralAttCount*/) == 2 && getVariable(0x3F487035L /*_HP*/) <= 25) {
			if (changeState(state -> Attack_Phase_General2(0.3)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) >= 50 && getPartyMembersCount()< 24 && target != null && getDistanceToTarget(target) < 2300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x6E0E85B9L /*_AttackCount*/) >= 50 && getPartyMembersCount()< 24 && target != null && getDistanceToTarget(target) < 2300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 700) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (changeState(state -> Attack_Range(0.3)))
			return;
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 2);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 3);
		doAction(3674084903L /*ATTACK_RANGE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Phase_General(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x194E7C96L /*Attack_Phase_General*/);
		doAction(37328067L /*ATTACK_GENERAL_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Phase_General_Ing(blendTime), 1000));
	}

	protected void Attack_Phase_General_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAE8B8AC4L /*Attack_Phase_General_Ing*/);
		doAction(883015344L /*ATTACK_GENERAL_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Phase_General_End(blendTime)));
	}

	protected void Attack_Phase_General_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC5332AC1L /*Attack_Phase_General_End*/);
		setVariable(0x983C4B84L /*_GeneralAttCount*/, getVariable(0x983C4B84L /*_GeneralAttCount*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 2);
		doAction(790325769L /*ATTACK_GENERAL_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Phase_General2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x528EAEBAL /*Attack_Phase_General2*/);
		doAction(3878087132L /*ATTACK_GENERAL2_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Phase_General2_Ing(blendTime), 1000));
	}

	protected void Attack_Phase_General2_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFB431D61L /*Attack_Phase_General2_Ing*/);
		doAction(2568566599L /*ATTACK_GENERAL2_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Phase_General2_End(blendTime)));
	}

	protected void Attack_Phase_General2_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1507349CL /*Attack_Phase_General2_End*/);
		setVariable(0x983C4B84L /*_GeneralAttCount*/, getVariable(0x983C4B84L /*_GeneralAttCount*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 2);
		doAction(3497851205L /*ATTACK_GENERAL2_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_General_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D70DFD1L /*Attack_General_Logic*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		if(Rnd.getChance(50)) {
			if (changeState(state -> Attack_General(0.3)))
				return;
		}
		changeState(state -> Attack_General2(blendTime));
	}

	protected void Attack_General(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88DD2B64L /*Attack_General*/);
		doAction(37328067L /*ATTACK_GENERAL_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_General_Ing(blendTime), 1000));
	}

	protected void Attack_General_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC512AB51L /*Attack_General_Ing*/);
		doAction(883015344L /*ATTACK_GENERAL_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General_End(blendTime)));
	}

	protected void Attack_General_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1063AC07L /*Attack_General_End*/);
		doAction(790325769L /*ATTACK_GENERAL_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_General2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD70732B3L /*Attack_General2*/);
		doAction(3878087132L /*ATTACK_GENERAL2_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_General2_Ing(blendTime), 1000));
	}

	protected void Attack_General2_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3219C7DCL /*Attack_General2_Ing*/);
		doAction(2568566599L /*ATTACK_GENERAL2_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General2_End(blendTime)));
	}

	protected void Attack_General2_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7E5656E9L /*Attack_General2_End*/);
		doAction(3497851205L /*ATTACK_GENERAL2_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) + 1);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(4003178407L /*ATTACK_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
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
