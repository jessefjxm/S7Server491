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
@IAIName("summon_turking_subordinate")
public class Ai_summon_turking_subordinate extends CreatureAI {
	public Ai_summon_turking_subordinate(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xFE24B34DL /*_turnDice*/, 0);
		setVariable(0x87B27D4AL /*_freezing_time*/, 0);
		setVariable(0x2E9C3CCFL /*_stun_time*/, 0);
		setVariable(0x9C1A0E76L /*_fear*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerdistance*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Turn_Logic(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Start_Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9B73A2CCL /*Start_Turn_Logic*/);
		setVariable(0xFE24B34DL /*_turnDice*/, getRandom(110));
		if (getVariable(0xFE24B34DL /*_turnDice*/) <= 10) {
			if (changeState(state -> Summon_Start_Turn_30(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 10 && getVariable(0xFE24B34DL /*_turnDice*/) <= 20) {
			if (changeState(state -> Summon_Start_Turn_60(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 20 && getVariable(0xFE24B34DL /*_turnDice*/) <= 30) {
			if (changeState(state -> Summon_Start_Turn_90(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 30 && getVariable(0xFE24B34DL /*_turnDice*/) <= 40) {
			if (changeState(state -> Summon_Start_Turn_120(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 40 && getVariable(0xFE24B34DL /*_turnDice*/) <= 50) {
			if (changeState(state -> Summon_Start_Turn_150(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 50 && getVariable(0xFE24B34DL /*_turnDice*/) <= 60) {
			if (changeState(state -> Summon_Start_Turn_180(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 60 && getVariable(0xFE24B34DL /*_turnDice*/) <= 70) {
			if (changeState(state -> Summon_Start_Turn__30(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 70 && getVariable(0xFE24B34DL /*_turnDice*/) <= 80) {
			if (changeState(state -> Summon_Start_Turn__60(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 80 && getVariable(0xFE24B34DL /*_turnDice*/) <= 90) {
			if (changeState(state -> Summon_Start_Turn__90(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 90 && getVariable(0xFE24B34DL /*_turnDice*/) <= 100) {
			if (changeState(state -> Summon_Start_Turn__120(blendTime)))
				return;
		}
		if (getVariable(0xFE24B34DL /*_turnDice*/) >= 100) {
			if (changeState(state -> Summon_Start_Turn__150(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Summon_Start_Turn_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xD19C134BL /*Summon_Start_Turn_30*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn_60(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x4341635CL /*Summon_Start_Turn_60*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 60, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn_90(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x432EF53EL /*Summon_Start_Turn_90*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn_120(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x763E8153L /*Summon_Start_Turn_120*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 120, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn_150(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x57EA600L /*Summon_Start_Turn_150*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 150, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xCAFFC412L /*Summon_Start_Turn_180*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 180, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn__30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xEBC85B2EL /*Summon_Start_Turn_-30*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn__60(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xC642255DL /*Summon_Start_Turn_-60*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -60, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn__90(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x30BC02C3L /*Summon_Start_Turn_-90*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn__120(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x5DD3467DL /*Summon_Start_Turn_-120*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -120, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Turn__150(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x9585533BL /*Summon_Start_Turn_-150*/);
		doAction(1949525877L /*START_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -150, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_Action(blendTime), 100)));
	}

	protected void Summon_Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE56F2FBBL /*Summon_Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1500));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getDistanceToTarget(object) >= 1500 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1500 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getDistanceToTarget(object) >= 1500 && getTargetHp(object) > 0)) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1500 && getDistanceToTarget(object) >= 1000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		if (target == null) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.5)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		if (target == null) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		if (target == null) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 3 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> FailFindPath_ReturnToParent(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 2000));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		doTeleport(EAIMoveDestType.Random, 200, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void FailFindPath_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6504D93DL /*FailFindPath_ReturnToParent*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 2000));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Summon_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x14EAD959L /*Summon_Die*/);
		doAction(3304615689L /*SUMMON_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Die(blendTime), 20000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Counter_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE7255872L /*Counter_KnockBack*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1451681896L /*COUNTER_KNOCKBACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Stun_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5158BE87L /*Damage_Stun_Start*/);
		doAction(674441006L /*DAMAGE_STUN_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Start_Logic(blendTime), 1000));
	}

	protected void Damage_Stun_Start_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4CCA9A11L /*Damage_Stun_Start_Logic*/);
		if (getVariable(0x5E287C7FL /*AI_Damage_Stun_Debuff*/) == 1) {
			if (changeState(state -> Damage_Stun_Debuff(0.3)))
				return;
		}
		if (getVariable(0x5E287C7FL /*AI_Damage_Stun_Debuff*/) == 0) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Damage_Stun_Debuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x89BC239DL /*Damage_Stun_Debuff*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(3495730359L /*DAMAGE_STUN_DEBUFF*/, blendTime, onDoActionEnd -> changeState(state -> Damage_Stun_End_Logic(blendTime)));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End_Logic(blendTime), getVariable(0x2E9C3CCFL /*_Stun_Time*/)));
	}

	protected void Damage_Stun_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x656F5FCL /*Damage_Stun_End_Logic*/);
		if (getVariable(0x85829700L /*AI_Damage_Stun_StartAction*/) == 1) {
			if (changeState(state -> Damage_Stun_End(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Counter_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9446F23L /*Counter_Stun*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3836323897L /*COUNTER_STUN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x20D7E548L /*Damage_Stop*/);
		doAction(1199011494L /*DAMAGE_STOP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), getVariable(0xE1F002D2L /*AI_Stop_CallCycleTime*/)));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Counter_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCD6419E7L /*Counter_KnockDown*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3570834151L /*COUNTER_KNOCKDOWN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Counter_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7EC0A58DL /*Counter_Bound*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1264349027L /*COUNTER_BOUND*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_AirFloat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xFE54DA01L /*Damage_AirFloat*/);
		doAction(834965090L /*DAMAGE_AIRFLOAT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_AirSmash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xF3E1435DL /*Damage_AirSmash*/);
		doAction(3455519139L /*DAMAGE_AIRSMASH*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_DownSmash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xAD9D0DFL /*Damage_DownSmash*/);
		doAction(460682976L /*DAMAGE_DOWNSMASH*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Counter_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x584B0647L /*Counter_Rigid*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2893154600L /*COUNTER_RIGID*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Freeze(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D0DCCD1L /*Damage_Freeze*/);
		doAction(2514923161L /*DAMAGE_FREEZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Freeze_End(blendTime), getVariable(0x87B27D4AL /*_Freezing_Time*/)));
	}

	protected void Damage_Freeze_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDEC6952L /*Damage_Freeze_End*/);
		doAction(1366805764L /*DAMAGE_FREEZE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Damage_Release(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82D0AC8EL /*Damage_Release*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 400, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Damage_Fear_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 400)));
	}

	protected void Damage_Fear_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE6704563L /*Damage_Fear_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Fear(blendTime), 1000));
	}

	protected void ForcePeaceful(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x26C394BEL /*ForcePeaceful*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> ForcePeaceful(blendTime), 1000));
	}

	protected void ForceSleep(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6171EFE4L /*ForceSleep*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> ForceSleep(blendTime), 1000));
	}

	protected void ForceReturn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x71674A20L /*ForceReturn*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 400, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> ForcePeaceful(blendTime), 1000)));
	}

	protected void ForceWakeup(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E3AFFF9L /*ForceWakeup*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xCF465EDCL /*Search_Enemy*/) {
			if (changeState(state -> Battle_Wait(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xCA081A50L /*AI_Damage_KnockBack*/) == 1) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x81E0C485L /*AI_Counter_Rigid*/) == 0 && getVariable(0xA558FAB7L /*AI_Damage_Rigid*/) == 1 && getVariable(0xCA081A50L /*AI_Damage_KnockBack*/) == 0) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF9798513L /*AI_Damage_KnockDown*/) == 1) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x81E0C485L /*AI_Counter_Rigid*/) == 0 && getVariable(0xA558FAB7L /*AI_Damage_Rigid*/) == 1 && getVariable(0xF9798513L /*AI_Damage_KnockDown*/) == 0) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x9B63B813L /*AI_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, eventData[0]);
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) == 0 && getVariable(0x7EBC0F53L /*AI_Damage_Stun*/) == 1 && getVariable(0x85829700L /*AI_Damage_Stun_StartAction*/) == 1) {
			if (changeState(state -> Damage_Stun_Start(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) == 0 && getVariable(0x5E287C7FL /*AI_Damage_Stun_Debuff*/) == 1 && getVariable(0x85829700L /*AI_Damage_Stun_StartAction*/) == 1) {
			if (changeState(state -> Damage_Stun_Start(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) == 0 && getVariable(0x5E287C7FL /*AI_Damage_Stun_Debuff*/) == 1 && getVariable(0x85829700L /*AI_Damage_Stun_StartAction*/) == 0) {
			if (changeState(state -> Damage_Stun_Debuff(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) == 0 && getVariable(0x7EBC0F53L /*AI_Damage_Stun*/) == 1 && getVariable(0x85829700L /*AI_Damage_Stun_StartAction*/) == 0) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() != 0xBF725BC4L /*Damage_KnockBack*/ || getState() != 0x3FB3341CL /*Damage_Stun*/ || getState() != 0x69E1FC3AL /*Damage_KnockDown*/ || getState() != 0x119675D3L /*Damage_Bound*/ || getState() != 0x5374AB60L /*Damage_Capture*/ || getState() != 0x6A4B0B1DL /*Damage_Rigid*/ || getState() != 0x82D0AC8EL /*Damage_Release*/ || getState() != 0x4E1B659L /*Damage_Die*/)) {
			if(Rnd.getChance(getVariable(0xBDBB4979L /*AI_Stop_Rate*/))) {
				if (changeState(state -> Damage_Stop(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAirFloat(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x29588795L /*AI_Damage_AirFloat*/) == 1) {
			if(Rnd.getChance(getVariable(0xBCAEDD2BL /*AI_AirFloat_Rate*/))) {
				if (changeState(state -> Damage_AirFloat(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAirSmash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xAE5D6D33L /*AI_Damage_AirSmash*/) == 1) {
			if(Rnd.getChance(getVariable(0x491EA6E3L /*AI_AirSmash_Rate*/))) {
				if (changeState(state -> Damage_AirSmash(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDownSmash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x850B1B43L /*AI_Damage_DownSmash*/) == 1) {
			if(Rnd.getChance(getVariable(0x6FF1F45L /*AI_DownSmash_Rate*/))) {
				if (changeState(state -> Damage_DownSmash(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x9B63B813L /*AI_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9B63B813L /*AI_Damage_Bound*/) == 0) {
			if (changeState(state -> Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x81E0C485L /*AI_Counter_Rigid*/) == 0 && getVariable(0xA558FAB7L /*AI_Damage_Rigid*/) == 1) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x81E0C485L /*AI_Counter_Rigid*/) != 0) {
			if(Rnd.getChance(getVariable(0x81E0C485L /*AI_Counter_Rigid*/))) {
				if (changeState(state -> Counter_Rigid(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFeared(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_Fear*/, 1);
		if (getVariable(0x9EA67D0EL /*AI_Damage_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFearReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		if (changeState(state -> Battle_Wait(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleIceFreeze(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x87B27D4AL /*_Freezing_Time*/, eventData[0]);
		if (getVariable(0xB7548E00L /*AI_Damage_Freeze*/) == 1) {
			if (changeState(state -> Damage_Freeze(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x81E0C485L /*AI_Counter_Rigid*/) == 0 && getVariable(0xA558FAB7L /*AI_Damage_Rigid*/) == 1 && getVariable(0xB7548E00L /*AI_Damage_Freeze*/) == 0) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReadyForRush(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
