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
@IAIName("monsters_main_plunder")
public class Ai_monsters_main_plunder extends CreatureAI {
	public Ai_monsters_main_plunder(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0x870CD143L /*_IsPartyMember*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, 0);
		setVariable(0xA2E704C3L /*_Party_Vanish*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Party_Start_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x34669B41L /*Party_Start_Logic*/);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Party_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4E76A6ABL /*Party_Wait_Logic*/);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), getVariable(0x87E37180L /*AI_Wait_CallCycleTime*/) + Rnd.get(-500,500)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().HandleBattleMode(getActor(), null));
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Search_Enemy_Order(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7A38885EL /*Search_Enemy_Order*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Party_Battle_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x451D9462L /*Party_Battle_Wait_Logic*/);
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x6B061951L /*AI_MA_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC3FD8E9L /*AI_MA_MaxDistance*/)) {
			if(Rnd.getChance(getVariable(0x60A54E45L /*AI_MoveAround*/))) {
				if (changeState(state -> Move_Around(0.5)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
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
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_NoRotate(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 3 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_ReturnToParent(blendTime), 2000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Weapon_In(blendTime), 10000)));
	}

	protected void Move_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1471881CL /*Move_ReturnToParent*/);
		clearAggro(true);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_ReturnToParent_Complete(blendTime), 10000)));
	}

	protected void Move_ReturnToParent_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0xE5801D43L /*Move_ReturnToParent_Complete*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
				if (changeState(state -> Weapon_In(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 10000);
		});
	}

	protected void Weapon_In(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626F781FL /*Weapon_In*/);
		doAction(1891792052L /*WEAPON_IN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Move_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x55F437ADL /*Move_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x6A74CA4CL /*AI_MA_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xF4D6D97FL /*AI_MA_RangeAttack1_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xF8792205L /*AI_MA_RangeAttack1*/))) {
				if (changeState(state -> Around_RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0xDCD50718L /*AI_MA_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC040C1A8L /*AI_MA_RangeAttack2_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x6BB56182L /*AI_MA_RangeAttack2*/))) {
				if (changeState(state -> Around_RangeAttack2(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.5)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveAround(getVariable(0x3AF07E15L /*AI_MA_TargetRadius*/) + Rnd.get(getVariable(0xD79E8369L /*AI_MA_MinMove*/), getVariable(0x46DE6ECFL /*AI_MA_MaxMove*/)), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1500)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x67695F37L /*Lost_Target*/);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Battle_Attack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC37AE191L /*Battle_Attack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCA4082FDL /*Battle_Attack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x79AD2A8AL /*Battle_RangeAttack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA781BA6DL /*Battle_RangeAttack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack3_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6A073F69L /*Battle_RangeAttack3_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x774996C5L /*Around_Attack1*/);
							if (getVariable(0x4C38FDC7L /*AI_MA_Attack1_RotateOff*/) == 1) {
				if (changeState(state -> Around_Attack1_NoRotate(0.3)))
					return;
			}
			changeState(state -> Around_Attack1_Rotate(blendTime));
	}

	protected void Around_Attack1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE25B1F95L /*Around_Attack1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(434909106L /*AROUND_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_Attack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1BBA890L /*Around_Attack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(434909106L /*AROUND_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4C99FC96L /*Around_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1080499018L /*AROUND_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x56BE167DL /*Around_RangeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(460537979L /*AROUND_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Death(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD4F2761DL /*Battle_Death*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Move_ChaseToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBCCBD2E0L /*Move_ChaseToParent*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(78190555L /*CHASER_OWNER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 150, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_ParentPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x838FBB02L /*Move_ParentPath*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(78190555L /*CHASER_OWNER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Parent_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x44B7ABF0L /*Move_Parent_Complete*/);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Combi_MoveSpeedDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x956E206L /*Combi_MoveSpeedDown*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3601242505L /*COMBI_MOVESPEED_DOWN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Combi_AttackSpeedDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E994519L /*Combi_AttackSpeedDown*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3824927449L /*COMBI_ATTACKSPEED_DOWN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Combi_PVDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCEB26B29L /*Combi_PVDown*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1532694810L /*COMBI_PV_DOWN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Combi_BuffCancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBA265A38L /*Combi_BuffCancel*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3741917618L /*COMBI_BUFF_CANCEL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Combi_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF6BDE8C5L /*Combi_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
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

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), getVariable(0x2E9C3CCFL /*_Stun_Time*/)));
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
	public EAiHandlerResult _helpme(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (Rnd.get(100) <= getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/) && getState() == 0x866C7489L /*Wait*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && isTargetDeliver(target) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/)) {
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
		if (getVariable(0x7EBC0F53L /*AI_Damage_Stun*/) == 1) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
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
		if (getVariable(0xA558FAB7L /*AI_Damage_Rigid*/) == 1) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
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
	public EAiHandlerResult _AllChildDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Die(0.5)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBattleMode(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy_Order(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x7A38885EL /*Search_Enemy_Order*/ && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Battle_Wait(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/)) {
			if (changeState(state -> Search_Enemy_Order(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x6F05E9AFL /*_FollowMe*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTeleportToParent(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> FailFindPath_ReturnToParent(0.5)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMeOwnerPath(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Move_ParentPath(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleHelpMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (Rnd.get(100) <= getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/) && getState() == 0x866C7489L /*Wait*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			setVariable(0x6F05E9AFL /*_FollowMe*/, 1);
		}
		if (changeState(state -> Party_Start_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1 && getVariable(0xA2E704C3L /*_Party_Vanish*/) == 0) {
			if (changeState(state -> Party_Battle_Wait_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xA2E704C3L /*_Party_Vanish*/) == 0) {
			if (changeState(state -> Party_Wait_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
