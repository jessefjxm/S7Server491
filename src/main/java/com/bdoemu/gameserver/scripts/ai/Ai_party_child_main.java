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
@IAIName("party_child_main")
public class Ai_party_child_main extends CreatureAI {
	public Ai_party_child_main(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0xDA5496BDL /*_DistanceToOwner*/, 0);
		setVariable(0x40CC9D51L /*_LostWayDice*/, 0);
		setVariable(0xAB17DA72L /*_LostWayDestination*/, 0);
		setVariable(0xC9113C13L /*_isLostWay*/, 0);
		setVariable(0x7492188CL /*_FailFindPath*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xABCDEF61L /*_PartyMemberType*/, getVariable(0xA64BE469L /*AI_PartyMemberType*/));
		setVariable(0x986648E6L /*_TurnAction*/, getVariable(0xCA881A37L /*AI_TurnAction*/));
		setVariable(0x98438471L /*_RunEnd*/, getVariable(0x21711A63L /*AI_RunEnd*/));
		setVariable(0x324F8EDBL /*_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
		setVariable(0x925166FDL /*_FirstAttack*/, getVariable(0x4EAF441FL /*AI_FirstAttack*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		clearAggro(true);
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
				if (changeState(state -> Escape(0.3)))
					return;
			}
		}
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1 && getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1 && isPartyMember()) {
			if (changeState(state -> Party_MoveToParent(0.3)))
				return;
		}
		if (isPartyMember() && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (changeState(state -> Party_MoveToParent_Goat(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Walk_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Walk_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9FE5C5L /*Walk_Random*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
				if (changeState(state -> Escape(0.3)))
					return;
			}
		}
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1 && getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 250, 500, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		if (getVariable(0x986648E6L /*_TurnAction*/) == 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0x986648E6L /*_TurnAction*/) == 0) {
			if (changeState(state -> Turn_Battle_Wait(0.3)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (isPartyMember() && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1) {
			if (changeState(state -> Party_MoveToParent(0.3)))
				return;
		}
		if (isPartyMember() && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (changeState(state -> Party_MoveToParent_Goat(0.3)))
				return;
		}
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
				if (changeState(state -> Escape(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDCE8DF7DL /*Escape*/);
		if (getVariable(0x96425EA0L /*AI_Escape_PreAction*/) == 1) {
			if (changeState(state -> Escape_PreAction(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		changeState(state -> Escape_Normal(blendTime));
	}

	protected void Escape_PreAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x889E9097L /*Escape_PreAction*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(426587747L /*TURN_ESCAPE*/, blendTime, onDoActionEnd -> changeState(state -> Escape_Normal(blendTime)));
	}

	protected void Escape_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x5BDC2607L /*Escape_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(getVariable(0x8FA1E7B6L /*AI_ES_MaxDistance*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Turn_180(blendTime), 2000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Move_RunAway_Wait(blendTime)));
	}

	protected void Move_RunAway_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30B14446L /*Move_RunAway_Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getDistanceToTarget(target) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Turn_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBF47FC3AL /*Turn_Battle_Wait*/);
		if (target != null && getDistanceToTarget(target) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Battle_Wait(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150 && getVariable(0x986648E6L /*_TurnAction*/) == 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150 && getVariable(0x986648E6L /*_TurnAction*/) == 0) {
			if (changeState(state -> Turn_Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHp(target) > 0 && getVariable(0x98438471L /*_RunEnd*/) == 1) {
			if (changeState(state -> Battle_Run_End(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150 && getVariable(0x986648E6L /*_TurnAction*/) == 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150 && getVariable(0x986648E6L /*_TurnAction*/) == 0) {
			if (changeState(state -> Turn_Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void Battle_Run_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1C78F86DL /*Battle_Run_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1516916056L /*BATTLE_RUN_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getDistanceToTarget(target) > getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
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
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getDistanceToTarget(target) > getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_Attack(0.3)))
					return;
			}
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEECD0FB6L /*Battle_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Rush_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E7CDB0EL /*Battle_Rush_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1464313205L /*ATTACK_RUSH_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		setVariable(0xDA5496BDL /*_DistanceToOwner*/, getDistanceToOwner());
		if (getVariable(0x6F05E9AFL /*_FollowMe*/) == 1 && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1) {
			if (changeState(state -> Party_MoveToParent(0.3)))
				return;
		}
		if (getVariable(0x6F05E9AFL /*_FollowMe*/) == 1 && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			if (changeState(state -> Party_MoveToParent_Goat(0.3)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 2) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Party_WalkRandom(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 200 + Rnd.get(-200,200)));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 300, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MoveToParent_Goat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x44C5F2B7L /*Party_MoveToParent_Goat*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MoveToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x133E56C1L /*Party_MoveToParent*/);
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_MoveToParentComplete(blendTime), 1000)));
	}

	protected void Party_MoveToParentComplete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29CC78DFL /*Party_MoveToParentComplete*/);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		doAction(1516916056L /*BATTLE_RUN_END*/, blendTime, onDoActionEnd -> changeState(state -> Party_WalkRandom(blendTime)));
	}

	protected void Party_MoveToOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3675B059L /*Party_MoveToOwner*/);
		setVariable(0xDA5496BDL /*_DistanceToOwner*/, getDistanceToOwner());
		if (getVariable(0xDA5496BDL /*_DistanceToOwner*/) <= 1000) {
			if (changeState(state -> Party_Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 300, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 3000);
		});
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 5000);
		});
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 400)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xABCDEF61L /*_PartyMemberType*/) == 2) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Escape(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0xABCDEF61L /*_PartyMemberType*/) == 1 && getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/)) {
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
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x26243C4BL /*_Damage_KnockBack*/) == 1) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x71B20CF2L /*_Damage_Stun*/) == 1) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x65B16C16L /*_Damage_KnockDown*/) == 1) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB0B44BDAL /*_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
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
	public EAiHandlerResult HandleFollowMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x6F05E9AFL /*_FollowMe*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Party_Wait(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePushsheep(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Party_MoveToOwner(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Party_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1 && getVariable(0x986648E6L /*_TurnAction*/) == 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1 && getVariable(0x986648E6L /*_TurnAction*/) == 0) {
			if (changeState(state -> Turn_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDonRunAway(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xC9113C13L /*_isLostWay*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCanRunAway(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xC9113C13L /*_isLostWay*/, 0);
		return EAiHandlerResult.BYPASS;
	}
}
