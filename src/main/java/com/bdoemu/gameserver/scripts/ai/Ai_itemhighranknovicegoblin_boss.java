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
@IAIName("itemhighranknovicegoblin_boss")
public class Ai_itemhighranknovicegoblin_boss extends CreatureAI {
	public Ai_itemhighranknovicegoblin_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5328BAAAL /*_SwingAttCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 1);
		setVariable(0xBEE22793L /*_CombinationCount*/, 3);
		setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 1);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, 1);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, 0);
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, 0);
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
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
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1500));
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

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0xEF7AE808L /*_RoarCount*/) >= 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xEF7AE808L /*_RoarCount*/) <= 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_Roar(0.3)))
					return;
			}
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
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5328BAAAL /*_SwingAttCount*/, 0);
		setVariable(0xBEE22793L /*_CombinationCount*/, 3);
		setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 1);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Sit_Down(blendTime), 1000)));
	}

	protected void Sit_Down_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xD15C3128L /*Sit_Down_Turn*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Sit_Down(blendTime), 2000)));
	}

	protected void Sit_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7687D7F9L /*Sit_Down*/);
		doAction(1199709567L /*SIT_DOWN*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
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
		if (getVariable(0xEF7AE808L /*_RoarCount*/) <= 0) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xEF7AE808L /*_RoarCount*/) >= 1 && getVariable(0x3F487035L /*_HP*/) < 50) {
			if (changeState(state -> Roar(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> AttackSkill_Bomb(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> AttackSkill_Bomb(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1600)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Range(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1600)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Range(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && target != null && getDistanceToTarget(target) > 600 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) <= 0 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && target != null && getDistanceToTarget(target) > 600 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) <= 0 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Summon(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2(0.3)))
					return;
			}
		}
		if (getVariable(0x5328BAAAL /*_SwingAttCount*/) >= 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Swing(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) > 55 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
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

	protected void Battle_Wait_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7603F562L /*Battle_Wait_Roar*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xEF7AE808L /*_RoarCount*/) >= 1 && getVariable(0x3F487035L /*_HP*/) < 50) {
			if (changeState(state -> Roar(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Left_Roar(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25 && target != null && getDistanceToTarget(target) < 450) {
			if (changeState(state -> Battle_Turn_Right_Roar(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> AttackSkill_Bomb_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> AttackSkill_Bomb_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1600)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Range_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && getVariable(0xBD142893L /*_Attack_Bomb_Count*/) <= 0 && target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1600)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Range_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) > 50 && target != null && getDistanceToTarget(target) > 600 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) <= 0 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Summon_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) < 50 && target != null && getDistanceToTarget(target) > 600 && getVariable(0x7E57A115L /*_Attack_Summon_Count*/) <= 0 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Summon_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x5328BAAAL /*_SwingAttCount*/) >= 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Swing_Roar(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) > 55 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal2_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal_Roar(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
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
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
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
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 0);
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
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		doAction(3372977862L /*BATTLE_WALK_ROAR*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 0);
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
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (getVariable(0x5328BAAAL /*_SwingAttCount*/) >= 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Swing(0.3)))
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
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 0);
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
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getAngleToTarget(target) > -15 && target != null && getAngleToTarget(target) < 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump2_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal_Roar(0.3)))
					return;
			}
		}
		if (getVariable(0x5328BAAAL /*_SwingAttCount*/) >= 3 && target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Swing_Roar(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run_Roar(0.3)))
				return;
		}
		doAction(1496808095L /*BATTLE_RUN_ROAR*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x725D6F81L /*_Attack_Jump_Count*/, 0);
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
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Left_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x50CAE33DL /*Battle_Turn_Left_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		doAction(927517472L /*BATTLE_TURN_LEFT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Roar(blendTime)));
	}

	protected void Battle_Turn_Right_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE8144AA6L /*Battle_Turn_Right_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, getTime());
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, getVariable(0x8807FEFBL /*_Special_Stun_IngTime*/) - getVariable(0x795FB552L /*_Special_Stun_StartTime*/));
		doAction(115396126L /*BATTLE_TURN_RIGHT_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Roar(blendTime)));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60186BFFL /*Roar*/);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> {
			setVariable(0xEF7AE808L /*_RoarCount*/, getVariable(0xEF7AE808L /*_RoarCount*/) - 1);
			scheduleState(state -> Roar_A(blendTime), 500);
		});
	}

	protected void Roar_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC1C3DC59L /*Roar_A*/);
		doAction(2442593878L /*ROAR_A*/, blendTime, onDoActionEnd -> changeState(state -> Roar_B(blendTime)));
	}

	protected void Roar_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23BA48F2L /*Roar_B*/);
		doAction(3283400308L /*ROAR_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_End_Logic(blendTime), 500));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Swing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC0D3EEL /*Attack_Swing*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		setVariable(0x5328BAAAL /*_SwingAttCount*/, 0);
		doAction(3832528125L /*ATTACK_SWING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x11F71E61L /*Attack_Jump2*/);
		setVariable(0x725D6F81L /*_Attack_Jump_Count*/, getVariable(0x725D6F81L /*_Attack_Jump_Count*/) + 1);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(2350417439L /*ATTACK_JUMP2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, 13);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(3613527642L /*ATTACK_SUMMON1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Summon_Logic(blendTime)));
	}

	protected void Attack_Summon_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5901083BL /*Attack_Summon_Logic*/);
		if (getPartyMembersCount()== 1) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Attack_Enemy(getActor(), null));
		}
		scheduleState(state -> Attack_End_Logic(blendTime), 500);
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, 3);
		doAction(3674084903L /*ATTACK_RANGE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void AttackSkill_Bomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x612C4080L /*AttackSkill_Bomb*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, 3);
		doAction(2075962773L /*ATTACKSKILL_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2905D73L /*Attack_Combination*/);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) - 1);
		doAction(1058081582L /*ATTACK_COMBINATION*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x788C919BL /*Attack_Normal_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(224380186L /*ATTACK_NORMAL_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE31A0DEEL /*Attack_Normal2_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(678333289L /*ATTACK_NORMAL2_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Swing_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF3D5711BL /*Attack_Swing_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		setVariable(0x5328BAAAL /*_SwingAttCount*/, 0);
		doAction(3315849274L /*ATTACK_SWING_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Jump2_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CF04E91L /*Attack_Jump2_Roar*/);
		setVariable(0x725D6F81L /*_Attack_Jump_Count*/, getVariable(0x725D6F81L /*_Attack_Jump_Count*/) + 1);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(1961833172L /*ATTACK_JUMP2_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Summon_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA5CC5B2EL /*Attack_Summon_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, 13);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, getVariable(0xBD142893L /*_Attack_Bomb_Count*/) - 1);
		doAction(1131268059L /*ATTACK_SUMMON1_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Summon_Roar_Logic(blendTime)));
	}

	protected void Attack_Summon_Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9EE49689L /*Attack_Summon_Roar_Logic*/);
		if (getPartyMembersCount()== 1) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Attack_Enemy(getActor(), null));
		}
		scheduleState(state -> Attack_End_Logic(blendTime), 500);
	}

	protected void Attack_Range_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x520EB753L /*Attack_Range_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, 3);
		doAction(1394656935L /*ATTACK_RANGE_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void AttackSkill_Bomb_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x35685D3DL /*AttackSkill_Bomb_Roar*/);
		setVariable(0x7E57A115L /*_Attack_Summon_Count*/, getVariable(0x7E57A115L /*_Attack_Summon_Count*/) - 1);
		setVariable(0xBD142893L /*_Attack_Bomb_Count*/, 3);
		doAction(4241310661L /*ATTACKSKILL_BOMB_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Combination_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6EB2F170L /*Attack_Combination_Roar*/);
		setVariable(0xBEE22793L /*_CombinationCount*/, getVariable(0xBEE22793L /*_CombinationCount*/) - 1);
		doAction(1483057227L /*ATTACK_COMBINATION_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0xEF7AE808L /*_RoarCount*/) <= 0) {
			if (changeState(state -> Battle_Wait_Roar(0.4)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> changeState(state -> Damage_Stun_Ing(blendTime)));
	}

	protected void Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E79F126L /*Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 5000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Special_Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEF3EFF55L /*Special_Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> changeState(state -> Special_Damage_Stun_Ing(blendTime)));
	}

	protected void Special_Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB97BF3E2L /*Special_Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_Logic(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x698D06BDL /*Special_Damage_Stun_Logic*/);
		if(Rnd.getChance(60)) {
			if (changeState(state -> Attack_Summon(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 500 && getTargetHp(object) > 0 && getTargetAction(object) == 0x9EEE85L /*MiniGame16*/)) {
			if (changeState(state -> Special_Damage_Stun_Ing(0.3)))
				return;
		}
		changeState(state -> Special_Damage_Stun_End(blendTime));
	}

	protected void Special_Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC21C1C3DL /*Special_Damage_Stun_End*/);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, getTime());
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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
		if (getVariable(0x5328BAAAL /*_SwingAttCount*/) < 4) {
			setVariable(0x5328BAAAL /*_SwingAttCount*/, getVariable(0x5328BAAAL /*_SwingAttCount*/) + 1);
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
