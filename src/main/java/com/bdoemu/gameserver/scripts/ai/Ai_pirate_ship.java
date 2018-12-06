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
@IAIName("pirate_ship")
public class Ai_pirate_ship extends CreatureAI {
	public Ai_pirate_ship(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0x870CD143L /*_IsPartyMember*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0xA2E704C3L /*_Party_Vanish*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x73DB50B5L /*AI_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x8385DA8EL /*AI_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/) });
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
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
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
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
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 5000) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
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

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().HandleBattleMode(getActor(), null));
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Search_Enemy_Order(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7A38885EL /*Search_Enemy_Order*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 5000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 20 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) > 1100) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 0 && target != null && getAngleToTarget(target) <= 20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -150 && target != null && getAngleToTarget(target) >= -180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 150 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -150 && target != null && getAngleToTarget(target) >= -180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 150 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 0 && target != null && getAngleToTarget(target) <= 20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -150 && target != null && getAngleToTarget(target) > -180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 150 && target != null && getAngleToTarget(target) < 180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -150 && target != null && getAngleToTarget(target) >= -180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 150 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Move_Chaser_RetryCurve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3A98C280L /*Move_Chaser_RetryCurve*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Move_Chaser_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xC76328CFL /*Move_Chaser_RetryCurveTurn*/);
		doAction(1616075405L /*MOVE_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser_RetryCurve(blendTime), 500)));
	}

	protected void Turn_L_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5898523EL /*Turn_L_Lv1*/);
		doAction(303734302L /*TURN_L_LV1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_R_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x49A84B2DL /*Turn_R_Lv1*/);
		doAction(3571452330L /*TURN_R_LV1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_L_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF3A5BE4BL /*Turn_L_Lv2*/);
		if (target != null && getAngleToTarget(target) <= 20 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) > 1100) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -150 && target != null && getAngleToTarget(target) > -180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 150 && target != null && getAngleToTarget(target) < 180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 150 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		doAction(12651492L /*TURN_L_LV2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_R_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34DD9ABDL /*Turn_R_Lv2*/);
		if (target != null && getAngleToTarget(target) <= 20 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) > 1100) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 0 && target != null && getAngleToTarget(target) <= 20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -150 && target != null && getAngleToTarget(target) > -180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 150 && target != null && getAngleToTarget(target) < 180 && target != null && getDistanceToTarget(target) < 900) {
			if (changeState(state -> Escape_Front(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -150 && target != null && getAngleToTarget(target) >= -180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		doAction(2389457420L /*TURN_R_LV2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Escape_Front(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1654D66L /*Escape_Front*/);
		if (target != null && getAngleToTarget(target) <= 20 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) > 1100) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 0 && target != null && getAngleToTarget(target) <= 20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -20 && target != null && getDistanceToTarget(target) <= 1100) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < -20 && target != null && getAngleToTarget(target) > -150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 2000)) {
			if (changeState(state -> Attaack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) > 20 && target != null && getAngleToTarget(target) < 150 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -150 && target != null && getAngleToTarget(target) >= -180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_L_Lv1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 150 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) >= 900) {
			if (changeState(state -> Turn_R_Lv1(0.3)))
				return;
		}
		doAction(20634775L /*MOVE_FRONT*/, blendTime, onDoActionEnd -> changeState(state -> Escape_Front(blendTime)));
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
		doTeleport(EAIMoveDestType.Random, 800, 0, 1, 1);
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
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Move_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1471881CL /*Move_ReturnToParent*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
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
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_ChaseToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBCCBD2E0L /*Move_ChaseToParent*/);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 150, false, ENaviType.ground, () -> {
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
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
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

	protected void Attaack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF22B89C6L /*Attaack_Normal1*/);
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> MovePoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> MovePoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if (changeState(state -> MovePoint5(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4B7E7FAL /*MovePoint4*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F460A97L /*MovePoint5*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3300 && isTargetDeliver(object))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && target != null && getTargetHp(target) > 0 && target != null && isTargetDeliver(target) && target != null && isCreatureVisible(target, false)) {
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
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Search_Enemy(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
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
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/)) {
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
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xA2E704C3L /*_Party_Vanish*/) == 0) {
			if (changeState(state -> Party_Wait_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
