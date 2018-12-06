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
@IAIName("stonegolem_boss")
public class Ai_stonegolem_boss extends CreatureAI {
	public Ai_stonegolem_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x497EFEA4L /*_AttackDie*/, 0);
		setVariable(0xC19EE3CDL /*_StompCount*/, 0);
		setVariable(0xC6A18D4DL /*_isSummon*/, getVariable(0x94DB406BL /*AI_isSummon*/));
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
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
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
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
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000 && getVariable(0xC6A18D4DL /*_isSummon*/) == 1) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000 && getVariable(0xC6A18D4DL /*_isSummon*/) == 1) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
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

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.1)))
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

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
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
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
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
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Logic(0.1)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Turn_Left(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= 35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Turn_Right(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= 1200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Chaser(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -135 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -45) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Turn_Left(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 45) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Turn_Right(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1200)) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && getVariable(0xC19EE3CDL /*_StompCount*/) >= 5 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 450 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack(0.2)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 1700) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Roll(0.4)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 20 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Chop(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Chop_A(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Chop_B(0.3)))
					return;
			}
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.5)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 800)) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.2)))
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
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Battle_Wait(0.2)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x3126FF0FL /*isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Battle_Wait(0.2)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 1500)) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Roll(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 750 && getDistanceToTarget(target, false) <= 1100)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack(0.2)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x3126FF0FL /*isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Kick(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1100) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Roll(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 20 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop_A(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop_B(0.3)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Turn_Left(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= 35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Turn_Right(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack(0.3)))
				return;
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1100) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Roll(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 20 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop_A(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 80 && target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 900) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Chop_B(0.3)))
					return;
			}
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(450 + Rnd.get(350, 550), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 180) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 330) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 180) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 180) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 330) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack1(0.3)))
					return;
			}
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 330) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Turn_180(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2417AD84L /*Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xC19EE3CDL /*_StompCount*/, getVariable(0xC19EE3CDL /*_StompCount*/) + 1);
		doAction(927601588L /*BATTLE_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x566D5E96L /*Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xC19EE3CDL /*_StompCount*/, 0);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2557095778L /*BATTLE_ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC369511CL /*Attack_Kick*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3572319367L /*BATTLE_ATTACK_KICK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Roll(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3BE4B175L /*Attack_Roll*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3738328107L /*BATTLE_ATTACK_ROLL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB73FBDB3L /*Attack_Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3442683276L /*BATTLE_ATTACK_TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E8D0930L /*Attack_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3329719241L /*BATTLE_ATTACK_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9B11BF9L /*Attack_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(460887176L /*BATTLE_ATTACK_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Die_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF820AFFCL /*Attack_Die_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1940296681L /*ATTACK_DIE_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Chop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x48303732L /*Attack_Chop*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1036356655L /*ATTACK_CHOP*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Battle_Wait(0.5)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Chop_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x23AEC757L /*Attack_Chop_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3112239168L /*ATTACK_CHOP_A*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Battle_Wait(0.5)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Chop_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x761CBC89L /*Attack_Chop_B*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(576115724L /*ATTACK_CHOP_B*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Battle_Wait(0.5)))
				return;
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2500));
	}

	protected void Attack_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x44937B88L /*Attack_Die*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x497EFEA4L /*_AttackDie*/, 1);
		doAction(2968116384L /*ATTACK_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Die_A(blendTime), 1000));
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
		if (target != null && getTargetHp(target) > 0 && getState() == 0xF1ED1C65L /*Start_Action_Logic*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 5 && getVariable(0x497EFEA4L /*_AttackDie*/) == 0) {
			if (changeState(state -> Attack_Die(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
