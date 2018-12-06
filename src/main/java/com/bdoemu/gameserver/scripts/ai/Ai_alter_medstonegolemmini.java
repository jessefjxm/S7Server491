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
@IAIName("alter_medstonegolemmini")
public class Ai_alter_medstonegolemmini extends CreatureAI {
	public Ai_alter_medstonegolemmini(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x445D4635L /*_isNHMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x2FC284DEL /*Attack6_Count*/, 3);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rotate_Logic(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 1700 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Rotate_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x65942A11L /*Rotate_Logic*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 27136)) {
				if (changeState(state -> Wait_Rotate(blendTime)))
					return;
			}
		}
		changeState(state -> Rotate_Logic(blendTime));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Detect_Target(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (getVariable(0xED6ED050L /*AI_MoveRandom*/) > 1 && Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-1000,1000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/))) {
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
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xA8B1D988L /*AI_RandomMove_CallCycleTime*/))));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, 1000, 1000, 1, 500);
		changeState(state -> Rotate_Logic(blendTime));
	}

	protected void Return_Shrine(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2E0CB188L /*Return_Shrine*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Attack2(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Return_Shrine(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
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

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -35 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179 && target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Turn_Left(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 35 && getVariable(0xE5BD13F2L /*_Degree*/) <= 179 && target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Turn_Right(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) < -35 && getVariable(0xE5BD13F2L /*_Degree*/) > 35 && target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Turn_180(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 75 && getVariable(0x2FC284DEL /*Attack6_Count*/) >= 3 && getPartyMembersCount()<= 1) {
			if(Rnd.getChance(getVariable(0x8A52D6C5L /*AI_BT_Attack6*/))) {
				if (changeState(state -> Attack6(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x2FC284DEL /*Attack6_Count*/) >= 2 && getPartyMembersCount()<= 1) {
			if(Rnd.getChance(getVariable(0x8A52D6C5L /*AI_BT_Attack6*/))) {
				if (changeState(state -> Attack6(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 25 && getVariable(0x2FC284DEL /*Attack6_Count*/) >= 1 && getPartyMembersCount()<= 1) {
			if(Rnd.getChance(getVariable(0x8A52D6C5L /*AI_BT_Attack6*/))) {
				if (changeState(state -> Attack6(0.3)))
					return;
			}
		}
		if (getVariable(0xC16C6D8AL /*AI_BT_Attack5*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x9BE24E3BL /*AI_BT_Attack5_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= 75 && getVariable(0x445D4635L /*_isNHMode*/) == 0) {
			if(Rnd.getChance(getVariable(0xC16C6D8AL /*AI_BT_Attack5*/))) {
				if (changeState(state -> Attack5(0.3)))
					return;
			}
		}
		if (getVariable(0xC16C6D8AL /*AI_BT_Attack5*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x9BE24E3BL /*AI_BT_Attack5_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= 30 && getVariable(0x445D4635L /*_isNHMode*/) == 0) {
			if(Rnd.getChance(getVariable(0xC16C6D8AL /*AI_BT_Attack5*/))) {
				if (changeState(state -> Attack5(0.3)))
					return;
			}
		}
		if (getVariable(0xC16C6D8AL /*AI_BT_Attack5*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0xA90ED516L /*AI_BT_Attack5_1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x445D4635L /*_isNHMode*/) == 1) {
			if(Rnd.getChance(getVariable(0x8B00525AL /*AI_BT_Attack5_1*/))) {
				if (changeState(state -> Attack5_1(0.3)))
					return;
			}
		}
		if (getVariable(0xF847CAE4L /*AI_BT_Attack4*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x2C020EEAL /*AI_BT_Attack4_Distance*/)) {
			if(Rnd.getChance(getVariable(0xF847CAE4L /*AI_BT_Attack4*/))) {
				if (changeState(state -> Attack4(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x445D4635L /*_isNHMode*/) == 0) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= 50) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Attack2(0.4)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> RangeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x445D4635L /*_isNHMode*/) == 1) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= 30) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> Battle_Run(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> RangeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4CA0FB7DL /*AI_BT_Attack4_1*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x700D6993L /*AI_BT_Attack4_1_Distance*/)) {
			if(Rnd.getChance(getVariable(0x4CA0FB7DL /*AI_BT_Attack4_1*/))) {
				if (changeState(state -> Attack4_1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
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
		setBehavior(EAIBehavior.idle);
		setState(0xEB438BF9L /*Battle_Run*/);
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Run_A(blendTime), 2000));
	}

	protected void Battle_Run_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD191680AL /*Battle_Run_A*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(684665779L /*BATTLE_RUN_A*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Run_B(blendTime), 2000)));
	}

	protected void Battle_Run_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x225E3036L /*Battle_Run_B*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4CA0FB7DL /*AI_BT_Attack4_1*/) > 0 && target != null && getDistanceToTarget(target) < getVariable(0x700D6993L /*AI_BT_Attack4_1_Distance*/)) {
			if (changeState(state -> Attack4_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(1152629261L /*BATTLE_RUN_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x566D5E96L /*Attack1*/);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CF46EC2L /*Attack2*/);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x409C8A78L /*Attack3*/);
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x841F80F1L /*Attack4*/);
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack4_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF96301CFL /*Attack4_1*/);
		doAction(3525718704L /*BATTLE_ATTACK4_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF481CD50L /*Attack5*/);
		setVariable(0x445D4635L /*_isNHMode*/, 1);
		doAction(562050538L /*BATTLE_ATTACK5*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack5_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x15B7703BL /*Attack5_1*/);
		setVariable(0x445D4635L /*_isNHMode*/, 0);
		doAction(2897542081L /*BATTLE_ATTACK5_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA1BB2003L /*Attack6*/);
		doAction(2651160411L /*BATTLE_ATTACK6*/, blendTime, onDoActionEnd -> {
			setVariable(0x2FC284DEL /*Attack6_Count*/, getVariable(0x2FC284DEL /*Attack6_Count*/) - 1);
			changeState(state -> Attack6_Logic(blendTime));
		});
	}

	protected void Attack6_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x69EB2CECL /*Attack6_Logic*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		changeState(state -> Attack6_A(blendTime));
	}

	protected void Attack6_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x878733C6L /*Attack6_A*/);
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Attack6_B(0.3)))
				return;
		}
		doAction(3513398461L /*BATTLE_ATTACK6_A*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack6_A(blendTime), 1000));
	}

	protected void Attack6_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7D3BEA9EL /*Attack6_B*/);
		doAction(672339101L /*BATTLE_ATTACK6_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC565C218L /*RangeAttack1*/);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x38EC4EF7L /*RangeAttack2*/);
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E8D0930L /*Attack_Turn_Left*/);
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9B11BF9L /*Attack_Turn_Right*/);
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB73FBDB3L /*Attack_Turn_180*/);
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 1000));
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
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xEC3F34D2L /*Detect_Target*/) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult ChildAllDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
