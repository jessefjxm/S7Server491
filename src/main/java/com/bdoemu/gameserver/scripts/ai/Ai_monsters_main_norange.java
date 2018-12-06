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
@IAIName("monsters_main_norange")
public class Ai_monsters_main_norange extends CreatureAI {
	public Ai_monsters_main_norange(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0x21C76BFFL /*_IsWater*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xC9221A03L /*_IsRushMode*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0x21E2720L /*_UseToOwnerPath*/, 0);
		setVariable(0xF4CC376DL /*_AlertAction*/, getVariable(0xD46F96BCL /*AI_AlertAction*/));
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		setVariable(0xD110DC99L /*_IsWaterType*/, getVariable(0xBCD5439FL /*AI_WaterType*/));
		setVariable(0x34A2F995L /*_IsAmphibious*/, getVariable(0xBD637BD3L /*AI_IsAmphibious*/));
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, new Integer[] { 0, 0 });
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xC64A8E9BL /*_PartyReturn*/, 0);
		setVariable(0x858C921CL /*_ReviveCount*/, getVariable(0x81A03861L /*AI_ReviveCount*/));
		setVariable(0x8816D447L /*_Weapon_Out*/, getVariable(0xDDE83C8CL /*AI_Weapon_Out*/));
		setVariable(0x1CF5EE87L /*_Damage_Stop*/, getVariable(0x1A7BA6F4L /*AI_Damage_Stop*/));
		setVariable(0x8D947A8L /*_Summon_Die*/, getVariable(0xBF6DAL /*AI_Summon_Die*/));
		setVariable(0xDDEBBD5L /*_Party_Die*/, getVariable(0xF22AE6CBL /*AI_Party_Die*/));
		setVariable(0xA2E704C3L /*_Party_Vanish*/, getVariable(0xFD5848ABL /*AI_Party_Vanish*/));
		setVariable(0x549051CDL /*_WayPointLoopType*/, getVariable(0xA701F14DL /*AI_WayPointLoopType*/));
		setVariable(0x19EB970L /*_WayPointLoopCount*/, getVariable(0xABA96D52L /*AI_WayPointLoopCount*/));
		setVariable(0x610E183EL /*_WayPointType*/, getVariable(0xC687E0D9L /*AI_WayPointType*/));
		setVariable(0x692F3C5CL /*_WayPointKey1*/, getVariable(0x3DFFB456L /*AI_WayPointKey1*/));
		setVariable(0x9231A1DL /*_WayPointKey2*/, getVariable(0xCD7084F0L /*AI_WayPointKey2*/));
		setVariable(0x7F4A9F05L /*_WayPointKey3*/, getVariable(0x73DB50B5L /*AI_WayPointKey3*/));
		setVariable(0x3ECDBD02L /*_WayPointKey4*/, getVariable(0x8385DA8EL /*AI_WayPointKey4*/));
		setVariable(0x6013B50FL /*_WayPointKey5*/, getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/));
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x692F3C5CL /*_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x9231A1DL /*_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x7F4A9F05L /*_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x3ECDBD02L /*_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x6013B50FL /*_WayPointKey5*/) });
		setVariable(0xEBC60317L /*_WayPoint1_Inverse*/, getVariable(0xC9674BC9L /*AI_WayPoint1_Inverse*/));
		setVariable(0x331FDCE2L /*_WayPoint2_Inverse*/, getVariable(0x51682587L /*AI_WayPoint2_Inverse*/));
		setVariable(0x684203A8L /*_WayPoint3_Inverse*/, getVariable(0x97E62B40L /*AI_WayPoint3_Inverse*/));
		setVariable(0xAC9DBF26L /*_WayPoint4_Inverse*/, getVariable(0xD2EFBA2CL /*AI_WayPoint4_Inverse*/));
		setVariable(0x36634413L /*_WayPoint5_Inverse*/, getVariable(0x9037566BL /*AI_WayPoint5_Inverse*/));
		setVariable(0x9C7C7BD5L /*_Waypoint1_Wait*/, getVariable(0x958D751AL /*AI_Waypoint1_Wait*/));
		setVariable(0xCDEFC6B9L /*_Waypoint2_Wait*/, getVariable(0xD76835CCL /*AI_Waypoint2_Wait*/));
		setVariable(0x2B107C14L /*_Waypoint3_Wait*/, getVariable(0x389F9138L /*AI_Waypoint3_Wait*/));
		setVariable(0x46B3DA05L /*_Waypoint4_Wait*/, getVariable(0xEC90837BL /*AI_Waypoint4_Wait*/));
		setVariable(0x6E58898BL /*_Waypoint5_Wait*/, getVariable(0xDD35D14CL /*AI_Waypoint5_Wait*/));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariable(0x1C36219AL /*AI_FA_SufferRangeAttack1_ShotBullet*/, getVariable(0xD5EDB509L /*AI_FA_SufferRangeAttack1_Shot*/));
		setVariable(0x2EF68FB1L /*AI_FA_SufferRangeAttack2_ShotBullet*/, getVariable(0x92872342L /*AI_FA_SufferRangeAttack2_Shot*/));
		setVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/, getVariable(0x10D1FA7EL /*AI_MA_RangeAttack1_Shot*/));
		setVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/, getVariable(0x6C848422L /*AI_MA_RangeAttack2_Shot*/));
		setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0xAB32793EL /*AI_BT_RangeAttack1_Shot*/));
		setVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/, getVariable(0x7296683CL /*AI_BT_RangeAttack2_Shot*/));
		setVariable(0x8E6F3126L /*AI_BT_RangeAttack3_ShotBullet*/, getVariable(0x4092F7B8L /*AI_BT_RangeAttack3_Shot*/));
		setVariable(0xD4E1FB0L /*AI_BS_RangeAttack1_ShotBullet*/, getVariable(0x44BBA70DL /*AI_BS_RangeAttack1_Shot*/));
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x10CBEB6CL /*AI_FA_SufferAction*/));
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, getVariable(0xC3B66543L /*AI_Stun_Time*/));
		setVariable(0x87B27D4AL /*_Freezing_Time*/, getVariable(0x6F08D801L /*AI_Freezing_Time*/));
		setVariable(0x870CD143L /*_IsPartyMember*/, 0);
		setVariable(0x79396437L /*_DarkSpirit_StartTime*/, 0);
		setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, 0);
		setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, 0);
		setVariable(0x79396437L /*_DarkSpirit_StartTime*/, getTime());
		if (getVariable(0x9AD1E354L /*AI_SummonStartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0) {
			if (changeState(state -> Summon_Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Start_Action_Swim(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 0 && getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Wait_Swim(blendTime)))
				return;
		}
		if (getVariable(0xCBCFFC4EL /*AI_StartActionMove*/) == 1) {
			if (changeState(state -> Start_ActionMove(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Summon_Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE56F2FBBL /*Summon_Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500 + Rnd.get(-500,500)));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1500 + Rnd.get(-500,500)));
	}

	protected void Start_Action_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC4DD1E79L /*Start_Action_Swim*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		doAction(2141126528L /*START_ACTION_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Swim(blendTime), 1500 + Rnd.get(-500,500)));
	}

	protected void Start_ActionMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF6BB104AL /*Start_ActionMove*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(272740183L /*START_ACTIONMOVE*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Party_Start_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x34669B41L /*Party_Start_Logic*/);
		if (getVariable(0x9AD1E354L /*AI_SummonStartAction*/) == 1 && getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Summon_Start_Action(blendTime)))
				return;
		}
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

	protected void LogOut_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDCD2CE78L /*LogOut_Logic*/);
		logout();
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Movepoint_Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0x6F05E9AFL /*_FollowMe*/) == 1 && getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x2804DEL /*AI_MoveRandom_PreAction*/) == 1 && Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random_PreAction(0.3)))
				return;
		}
		if (getVariable(0x2804DEL /*AI_MoveRandom_PreAction*/) == 0 && Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), getVariable(0x87E37180L /*AI_Wait_CallCycleTime*/) + Rnd.get(-500,500)));
	}

	protected void Wait_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA8755B38L /*Wait_Swim*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xFB9296CAL /*AI_MoveRandom_PreAction_Swim*/) == 1 && Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random_PreAction_Swim(0.3)))
				return;
		}
		if (getVariable(0x2804DEL /*AI_MoveRandom_PreAction*/) == 0 && Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if (changeState(state -> Move_Random_Swim(0.3)))
				return;
		}
		doAction(211154798L /*WAIT_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Swim(blendTime), getVariable(0x87E37180L /*AI_Wait_CallCycleTime*/) + Rnd.get(-500,500)));
	}

	protected void Alert_Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC9F2CEDAL /*Alert_Start_Action*/);
		clearAggro(false);
		setVariable(0x1408C89EL /*_IsAlert*/, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Alert_Start_Action_Logic(blendTime), 500 + Rnd.get(-1500,1500)));
	}

	protected void Alert_Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBAF1DB49L /*Alert_Start_Action_Logic*/);
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1) {
			if (changeState(state -> Alert_Search_Enemy(0.3)))
				return;
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 0) {
			if (changeState(state -> Alert_Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Alert_Battle_Wait(blendTime));
	}

	protected void Alert_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F198378L /*Alert_Wait*/);
		clearAggro(false);
		setVariable(0x1408C89EL /*_IsAlert*/, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Alert_Wait_Logic(blendTime), 500 + Rnd.get(-1500,1500)));
	}

	protected void Alert_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x30E5641AL /*Alert_Wait_Logic*/);
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1) {
			if (changeState(state -> Alert_Search_Enemy(0.3)))
				return;
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 0) {
			if (changeState(state -> Alert_Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Alert_Battle_Wait(blendTime));
	}

	protected void Alert_Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEE1EB4F8L /*Alert_Search_Enemy*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Alert_Battle_Wait(blendTime), 2000));
	}

	protected void Alert_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD46EF24L /*Alert_Battle_Wait*/);
		clearAggro(false);
		setVariable(0x1408C89EL /*_IsAlert*/, 1);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0x6F05E9AFL /*_FollowMe*/) == 1 && getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) > 60) {
			if (changeState(state -> Alert_Turn_Right(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) < -60) {
			if (changeState(state -> Alert_Turn_Left(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Alert_Battle_Wait(blendTime), 500));
	}

	protected void Alert_Battle_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2889F98DL /*Alert_Battle_Wait_Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < getVariable(0x468D3572L /*AI_EC_LimitDistance*/))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Alert_Recovery(blendTime));
	}

	protected void Alert_Recovery(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0x1AB1DC0CL /*Alert_Recovery*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Alert_Battle_Wait(blendTime), 100));
	}

	protected void Alert_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCE5DB899L /*Alert_Turn_Left*/);
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Alert_Battle_Wait(blendTime)));
	}

	protected void Alert_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDFD631E2L /*Alert_Turn_Right*/);
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Alert_Battle_Wait(blendTime)));
	}

	protected void Alert_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDBCBEF6AL /*Alert_Turn_180*/);
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Alert_Battle_Wait(blendTime)));
	}

	protected void Move_Random_PreAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x579C842L /*Move_Random_PreAction*/);
		doAction(573590497L /*MOVE_WALK_PREACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random(blendTime), 2000));
	}

	protected void Move_Random_EndAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8D36275BL /*Move_Random_EndAction*/);
		doAction(580477136L /*MOVE_WALK_ENDACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0x6F05E9AFL /*_FollowMe*/) == 1 && getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, getVariable(0xF561B270L /*AI_RandomMove_Min*/), getVariable(0xEA5276A5L /*AI_RandomMove_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Random_End(blendTime), getVariable(0xA8B1D988L /*AI_RandomMove_CallCycleTime*/))));
	}

	protected void Move_Random_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x455AE195L /*Move_Random_End*/);
		if (getVariable(0xB78D5ABAL /*AI_MoveRandom_EndAction*/) == 1) {
			if (changeState(state -> Move_Random_EndAction(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Move_Random_PreAction_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DD7C53FL /*Move_Random_PreAction_Swim*/);
		doAction(1360300233L /*MOVE_WALK_PREACTION_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random_Swim(blendTime), 2000));
	}

	protected void Move_Random_EndAction_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x547252A4L /*Move_Random_EndAction_Swim*/);
		doAction(3449300020L /*MOVE_WALK_ENDACTION_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Swim(blendTime), 2000));
	}

	protected void Move_Random_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2C8B2FEDL /*Move_Random_Swim*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		if (isDarkSpiritMonster()) {
			setVariable(0xF20FD832L /*_DarkSpirit_IngTime*/, getTime());
		}
		if (isDarkSpiritMonster()) {
			setVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/, getVariable(0xF20FD832L /*_DarkSpirit_IngTime*/) - getVariable(0x79396437L /*_DarkSpirit_StartTime*/));
		}
		if (isDarkSpiritMonster() && getVariable(0x11BDC6FBL /*_DarkSpirit_EndTime*/) >= 60000) {
			if (changeState(state -> LogOut_Logic(0.3)))
				return;
		}
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		doAction(1118681551L /*MOVE_WALK_SWIM*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, getVariable(0xF561B270L /*AI_RandomMove_Min*/), getVariable(0xEA5276A5L /*AI_RandomMove_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Random_End_Swim(blendTime), getVariable(0xA8B1D988L /*AI_RandomMove_CallCycleTime*/))));
	}

	protected void Move_Random_End_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2AA53F1DL /*Move_Random_End_Swim*/);
		if (getVariable(0xB78D5ABAL /*AI_MoveRandom_EndAction*/) == 1) {
			if (changeState(state -> Move_Random_EndAction_Swim(0.3)))
				return;
		}
		doAction(211154798L /*WAIT_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Swim(blendTime), 2000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) - 1);
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) - 1);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Search_Enemy_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x59B63714L /*Search_Enemy_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) - 1);
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) - 1);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		doAction(577920792L /*SEARCH_ENEMY_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Swim(blendTime), 2000));
	}

	protected void Search_Enemy_GoChase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB3CBD2A4L /*Search_Enemy_GoChase*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) - 1);
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) - 1);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser(blendTime), 1000));
	}

	protected void Search_Enemy_GoChase_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x45337C5FL /*Search_Enemy_GoChase_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) - 1);
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) - 1);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		doAction(577920792L /*SEARCH_ENEMY_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser_Swim(blendTime), 1000));
	}

	protected void Weapon_Out(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x169F23DBL /*Weapon_Out*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4142397618L /*WEAPON_OUT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Weapon_Out_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8ADA85DCL /*Weapon_Out_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3689010407L /*WEAPON_OUT_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Swim(blendTime), 2000));
	}

	protected void Move_Chaser_PreAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA5334A21L /*Move_Chaser_PreAction*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3581016767L /*MOVE_CHASER_PREACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser(blendTime), 1000));
	}

	protected void Move_Chaser_PreAction_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2394206EL /*Move_Chaser_PreAction_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2840314403L /*MOVE_CHASER_PREACTION_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chaser_Swim(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 1 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if(Rnd.getChance(getVariable(0xC8F2D381L /*AI_Defence_Chaser*/))) {
			if (changeState(state -> Move_Defence_Chaser(0.3)))
				return;
		}
		if (getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xB3A90433L /*AI_BT_DestructAttack_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x45AEF498L /*AI_BT_DestructAttack_HP*/)) {
			if(Rnd.getChance(getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/))) {
				if (changeState(state -> Battle_DestructAttack(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (getVariable(0x79C2E1A0L /*AI_EC_Attack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x2CE6579BL /*AI_EC_Attack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x7A40F2AEL /*AI_EC_Attack3*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xD741F92L /*AI_TP_Attack1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) >= getVariable(0x4B1E3044L /*AI_TP_Attack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xBBCD1865L /*AI_TP_Attack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x645BEC9FL /*AI_TP_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xD741F92L /*AI_TP_Attack1*/))) {
				if (changeState(state -> Battle_TargetPositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Move_Chaser_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2CB7BDL /*Move_Chaser_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
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
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 1 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser_Swim(0.3)))
				return;
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_Swim(0.3)))
					return;
			}
		}
		if (getVariable(0xD741F92L /*AI_TP_Attack1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) >= getVariable(0x4B1E3044L /*AI_TP_Attack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xBBCD1865L /*AI_TP_Attack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x645BEC9FL /*AI_TP_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xD741F92L /*AI_TP_Attack1*/))) {
				if (changeState(state -> Battle_TargetPositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait_Swim(0.3)))
				return;
		}
		doAction(1141561384L /*MOVE_CHASER_SWIM*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser_Swim(blendTime), 1000)));
	}

	protected void Move_Defence_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x17E5C8FEL /*Move_Defence_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0x34A2F995L /*_IsAmphibious*/) == 1 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Chaser_Swim(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 1 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xB3A90433L /*AI_BT_DestructAttack_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x45AEF498L /*AI_BT_DestructAttack_HP*/)) {
			if(Rnd.getChance(getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/))) {
				if (changeState(state -> Battle_DestructAttack(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (getVariable(0x79C2E1A0L /*AI_EC_Attack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x2CE6579BL /*AI_EC_Attack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x7A40F2AEL /*AI_EC_Attack3*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_Rotate(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(825954671L /*MOVE_DEFENCE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Defence_Chaser(blendTime), 1000)));
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
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if(Rnd.getChance(getVariable(0xBC187A89L /*AI_Defence_HighSpeedChase*/))) {
			if (changeState(state -> HighSpeed_Defence_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(blendTime)))
					return;
			}
		}
		if (getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xB3A90433L /*AI_BT_DestructAttack_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x45AEF498L /*AI_BT_DestructAttack_HP*/)) {
			if(Rnd.getChance(getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/))) {
				if (changeState(state -> Battle_DestructAttack(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (getVariable(0x79C2E1A0L /*AI_EC_Attack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x2CE6579BL /*AI_EC_Attack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x7A40F2AEL /*AI_EC_Attack3*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_Rotate(0.3)))
					return;
			}
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 1000)));
	}

	protected void HighSpeed_Chaser_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2224A173L /*HighSpeed_Chaser_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
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
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(blendTime)))
					return;
			}
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_Swim(0.3)))
					return;
			}
		}
		doAction(3774945013L /*CHASER_RUN_SWIM*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser_Swim(blendTime), 1000)));
	}

	protected void HighSpeed_Defence_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x3D705B8DL /*HighSpeed_Defence_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0x34A2F995L /*_IsAmphibious*/) == 1 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> HighSpeed_Chaser_Swim(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(blendTime)))
					return;
			}
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xB3A90433L /*AI_BT_DestructAttack_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x45AEF498L /*AI_BT_DestructAttack_HP*/)) {
			if(Rnd.getChance(getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/))) {
				if (changeState(state -> Battle_DestructAttack(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (getVariable(0x79C2E1A0L /*AI_EC_Attack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x2CE6579BL /*AI_EC_Attack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x7A40F2AEL /*AI_EC_Attack3*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) > -30 && target != null && getAngleToTarget(target) < 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_Rotate(0.3)))
					return;
			}
		}
		doAction(3178629899L /*CHASER_DEFENCE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Defence_Chaser(blendTime), 1000)));
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
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Wait_Swim(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
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
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_ReturnToParent(blendTime), 2000));
	}

	protected void Move_Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xCBA2B4E2L /*Move_Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1405914678L /*MOVE_ESCAPE*/, blendTime, onDoActionEnd -> escape(getVariable(0x8FA1E7B6L /*AI_ES_MaxDistance*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 3000)));
	}

	protected void CheckLevel_Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xF44A9B48L /*CheckLevel_Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2339884288L /*CHECKLEVEL_ESCAPE*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_CheckLevel(blendTime), 5000)));
	}

	protected void CheckLevel_Escape_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x6E25B419L /*CheckLevel_Escape_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(3918043157L /*CHECKLEVEL_ESCAPE_SWIM*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_CheckLevel_Swim(blendTime), 5000)));
	}

	protected void Run_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x5E320D92L /*Run_Random*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> AggroClear_Run_Random(blendTime), 3000)));
	}

	protected void Run_Random_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x964DEFD4L /*Run_Random_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		doAction(1141561384L /*MOVE_CHASER_SWIM*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> AggroClear_Run_Random_Swim(blendTime), 3000)));
	}

	protected void AggroClear_Run_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB85F042L /*AggroClear_Run_Random*/);
		clearAggro(true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void AggroClear_Run_Random_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCC529101L /*AggroClear_Run_Random_Swim*/);
		clearAggro(true);
		doAction(211154798L /*WAIT_SWIM*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Swim(blendTime)));
	}

	protected void Move_RunAway(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x9180DC90L /*Move_RunAway*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> escape(getVariable(0xB89EFC60L /*AI_RA_MaxDistance*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Turn_180(blendTime), 3000)));
	}

	protected void Move_RunAway_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xF26FE422L /*Move_RunAway_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		doAction(3064683397L /*MOVE_RUNAWAY_SWIM*/, blendTime, onDoActionEnd -> escape(getVariable(0xB89EFC60L /*AI_RA_MaxDistance*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Turn_180_Swim(blendTime), 3000)));
	}

	protected void Move_RunAway_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30B14446L /*Move_RunAway_Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void CheckLevel_RunAway(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xA2EDFBF7L /*CheckLevel_RunAway*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2319605744L /*CHECKLEVEL_RUNAWAY*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_CheckLevel(blendTime), 5000)));
	}

	protected void CheckLevel_RunAway_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC1B136DL /*CheckLevel_RunAway_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2012524154L /*CHECKLEVEL_RUNAWAY_SWIM*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_CheckLevel_Swim(blendTime), 5000)));
	}

	protected void Wait_CheckLevel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA664004CL /*Wait_CheckLevel*/);
		clearAggro(true);
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait_CheckLevel_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x75D6C08DL /*Wait_CheckLevel_Swim*/);
		clearAggro(true);
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(211154798L /*WAIT_SWIM*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Swim(blendTime)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		if (getVariable(0x3AFC23E1L /*AI_Move_Return_NotMove*/) == 1) {
			if (changeState(state -> Move_Return_NotMove(0.1)))
				return;
		}
		if (getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Move_Return_Move_Swim(0.1)))
				return;
		}
		changeState(state -> Move_Return_Move(blendTime));
	}

	protected void Move_Return_NotMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0x59168341L /*Move_Return_NotMove*/);
		doAction(3119505170L /*NOT_MOVE_RETURN*/, blendTime, onDoActionEnd -> {
			if (isDarkSpiritMonster()) {
				setVariable(0x79396437L /*_DarkSpirit_StartTime*/, getTime());
			}
			clearAggro(true);
			setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
			setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x10CBEB6CL /*AI_FA_SufferAction*/));
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
			setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
			setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
			if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
				if (changeState(state -> Weapon_In(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 10000);
		});
	}

	protected void Move_Return_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x469808EEL /*Move_Return_Move*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			if (isDarkSpiritMonster()) {
				setVariable(0x79396437L /*_DarkSpirit_StartTime*/, getTime());
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
			setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x10CBEB6CL /*AI_FA_SufferAction*/));
			setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
			setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
			setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
			if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
				if (changeState(state -> Weapon_In(0.3)))
					return true;
			}
			if (getVariable(0x372568CCL /*AI_Turn_Spawn*/) == 1) {
				if (changeState(state -> Turn_Spawn(0.3)))
					return true;
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Move_Return_Move_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x7E151FA5L /*Move_Return_Move_Swim*/);
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
		setVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/, getVariable(0x10CBEB6CL /*AI_FA_SufferAction*/));
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		doAction(1732167460L /*MOVE_RETURN_SWIM*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			if (isDarkSpiritMonster()) {
				setVariable(0x79396437L /*_DarkSpirit_StartTime*/, getTime());
			}
			if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
				if (changeState(state -> Weapon_In_Swim(0.3)))
					return true;
			}
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
		clearAggro(true);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
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
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> {
			if (isDarkSpiritMonster()) {
				setVariable(0x79396437L /*_DarkSpirit_StartTime*/, getTime());
			}
			if (getVariable(0x863100ADL /*AI_Weapon_In*/) == 1) {
				if (changeState(state -> Weapon_In(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 10000);
		});
	}

	protected void Revive(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x52671660L /*Revive*/);
		doAction(3872258133L /*REVIVE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Revive_Complete(blendTime), 3000));
	}

	protected void Revive_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0x83D03F19L /*Revive_Complete*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x858C921CL /*_ReviveCount*/, getVariable(0x858C921CL /*_ReviveCount*/) - 1);
		doAction(3232930915L /*REVIVE*/, blendTime, onDoActionEnd -> revive(getVariable(0x1C6456AEL /*AI_ReviveHpRate*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 3000)));
	}

	protected void Weapon_In(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626F781FL /*Weapon_In*/);
		doAction(1891792052L /*WEAPON_IN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xBF6DAL /*AI_Summon_Die*/) == 1) {
				if (changeState(state -> Summon_Die(0.3)))
					return;
			}
			if (getVariable(0x372568CCL /*AI_Turn_Spawn*/) == 1) {
				if (changeState(state -> Turn_Spawn(0.3)))
					return;
			}
			if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1) {
				if (changeState(state -> Start_Action(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 3000);
		});
	}

	protected void Weapon_In_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x849A42DFL /*Weapon_In_Swim*/);
		doAction(2972957987L /*WEAPON_IN_SWIM*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1) {
				if (changeState(state -> Start_Action_Swim(0.3)))
					return;
			}
			scheduleState(state -> Wait_Swim(blendTime), 3000);
		});
	}

	protected void Turn_Spawn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE67FFF90L /*Turn_Spawn*/);
		if (getVariable(0x9AD1E354L /*AI_SummonStartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0) {
			if (changeState(state -> Summon_Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Start_Action_Swim(blendTime)))
				return;
		}
		if (getVariable(0x37E25A7DL /*AI_StartAction*/) == 0 && getVariable(0xD110DC99L /*_IsWaterType*/) == 1) {
			if (changeState(state -> Wait_Swim(blendTime)))
				return;
		}
		if (getVariable(0xCBCFFC4EL /*AI_StartActionMove*/) == 1) {
			if (changeState(state -> Start_ActionMove(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Summon_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x14EAD959L /*Summon_Die*/);
		doAction(3304615689L /*SUMMON_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Die(blendTime), 20000));
	}

	protected void Move_Around_PreAction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x101A5CE7L /*Move_Around_PreAction*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(208315419L /*MOVE_AROUND_PREACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Around(blendTime), 1000));
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
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && (getAngleToTarget(target) >= -40 && getAngleToTarget(target) <= 40) && target != null && getDistanceToTarget(target) <= getVariable(0xDFE956BEL /*AI_BS_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x38DF4DC7L /*AI_BS_HP*/)) {
			if(Rnd.getChance(getVariable(0x3D6C82A2L /*AI_Backstep*/))) {
				if (changeState(state -> Move_BackStep(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x9F40743BL /*AI_ES_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x61AC0DF1L /*AI_ES_HP*/)) {
			if(Rnd.getChance(getVariable(0x36D3C519L /*AI_Escape*/))) {
				if (changeState(state -> Move_Escape(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0xF79690FDL /*AI_RA_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xA789A0D9L /*AI_RA_HP*/)) {
			if(Rnd.getChance(getVariable(0xE1EDA4F4L /*AI_RunAway*/))) {
				if (changeState(state -> Move_RunAway(0.1)))
					return;
			}
		}
		if (getVariable(0xF10B5B6EL /*AI_MA_Attack1*/) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0xDD508B3EL /*AI_MA_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9FFBA0E7L /*AI_MA_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xF10B5B6EL /*AI_MA_Attack1*/))) {
				if (changeState(state -> Around_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x13B166ECL /*AI_MA_Attack2*/) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x59CC58FEL /*AI_MA_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD58F9AA4L /*AI_MA_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x13B166ECL /*AI_MA_Attack2*/))) {
				if (changeState(state -> Around_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0xF8792205L /*AI_MA_RangeAttack1*/) > 0 && getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x6A74CA4CL /*AI_MA_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xF4D6D97FL /*AI_MA_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9E31AF78L /*AI_MA_RangeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xF8792205L /*AI_MA_RangeAttack1*/))) {
				if (changeState(state -> Around_RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0x6BB56182L /*AI_MA_RangeAttack2*/) > 0 && getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xDCD50718L /*AI_MA_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC040C1A8L /*AI_MA_RangeAttack2_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x46F50990L /*AI_MA_RangeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
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

	protected void Move_Around_FindTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x82540114L /*Move_Around_FindTarget*/);
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && (getAngleToTarget(target) >= -40 && getAngleToTarget(target) <= 40) && target != null && getDistanceToTarget(target) <= getVariable(0xDFE956BEL /*AI_BS_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x38DF4DC7L /*AI_BS_HP*/)) {
			if(Rnd.getChance(getVariable(0x3D6C82A2L /*AI_Backstep*/))) {
				if (changeState(state -> Move_BackStep(0.3)))
					return;
			}
		}
		if (getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x9F40743BL /*AI_ES_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x61AC0DF1L /*AI_ES_HP*/)) {
			if(Rnd.getChance(getVariable(0x36D3C519L /*AI_Escape*/))) {
				if (changeState(state -> Move_Escape(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0xF79690FDL /*AI_RA_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xA789A0D9L /*AI_RA_HP*/)) {
			if(Rnd.getChance(getVariable(0xE1EDA4F4L /*AI_RunAway*/))) {
				if (changeState(state -> Move_RunAway(0.1)))
					return;
			}
		}
		if (getVariable(0xF10B5B6EL /*AI_MA_Attack1*/) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0xDD508B3EL /*AI_MA_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9FFBA0E7L /*AI_MA_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xF10B5B6EL /*AI_MA_Attack1*/))) {
				if (changeState(state -> Around_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x13B166ECL /*AI_MA_Attack2*/) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x59CC58FEL /*AI_MA_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD58F9AA4L /*AI_MA_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x13B166ECL /*AI_MA_Attack2*/))) {
				if (changeState(state -> Around_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0xF8792205L /*AI_MA_RangeAttack1*/) > 0 && getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x6A74CA4CL /*AI_MA_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xF4D6D97FL /*AI_MA_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9E31AF78L /*AI_MA_RangeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xF8792205L /*AI_MA_RangeAttack1*/))) {
				if (changeState(state -> Around_RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0x6BB56182L /*AI_MA_RangeAttack2*/) > 0 && getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xDCD50718L /*AI_MA_RangeAttack2_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC040C1A8L /*AI_MA_RangeAttack2_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x46F50990L /*AI_MA_RangeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x6BB56182L /*AI_MA_RangeAttack2*/))) {
				if (changeState(state -> Around_RangeAttack2(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.5)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveAround(300 + Rnd.get(100, 300), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1500)));
	}

	protected void Move_BackStep(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x257A4497L /*Move_BackStep*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(243762293L /*MOVE_BACKSTEP*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> BackStep_Logic(blendTime), 2000);
		});
	}

	protected void BackStep_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD7A9175L /*BackStep_Logic*/);
		if (getVariable(0x148ACE46L /*AI_BS_RangeAttack1*/) > 0 && getVariable(0xD4E1FB0L /*AI_BS_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x317D8810L /*AI_BS_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x398EDE92L /*AI_BS_RangeAttack1_MaxDistance*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x148ACE46L /*AI_BS_RangeAttack1*/))) {
				if (changeState(state -> BackStep_RangeAttack1(0.3)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDE6A633CL /*Turn_180_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2364206887L /*TURN_180_SWIM*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Swim(blendTime)));
	}

	protected void Move_Warp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB406F43DL /*Move_Warp*/);
		doAction(1001169626L /*MOVE_WARP_A*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Warp_b(blendTime), 1000));
	}

	protected void Move_Warp_b(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5EDC6AAAL /*Move_Warp_b*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Warp_c(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Warp_c(0.3)))
				return;
		}
		doAction(4265546893L /*MOVE_WARP_B*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Warp_b(blendTime), 5000)));
	}

	protected void Move_Warp_c(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC663EF5EL /*Move_Warp_c*/);
		doAction(159963012L /*MOVE_WARP_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_WarpAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1BA75950L /*Move_WarpAttack*/);
		doAction(1001169626L /*MOVE_WARP_A*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_WarpAttack_b(blendTime), 1000));
	}

	protected void Move_WarpAttack_b(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1D3A1227L /*Move_WarpAttack_b*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_WarpAttack_c(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_WarpAttack_c(0.3)))
				return;
		}
		doAction(4265546893L /*MOVE_WARP_B*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_WarpAttack_b(blendTime), 5000)));
	}

	protected void Move_WarpAttack_c(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x13DBC32AL /*Move_WarpAttack_c*/);
		doAction(159963012L /*MOVE_WARP_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_WarpAttack_d(blendTime), 1000));
	}

	protected void Move_WarpAttack_d(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEFE2C2CBL /*Move_WarpAttack_d*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(1458648773L /*ATTACK_WARP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
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
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x9D0986ABL /*AI_Move_Return_Distance*/)) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x184F868DL /*AI_Special_Chase*/) == 1 && getVariable(0x4DA2BAB9L /*AI_Special_Action*/) == 1 && getVariable(0x4A50EDF7L /*AI_Check_BuffIndex*/) != 0 && target != null && checkBuff(target, getVariable(0x4A50EDF7L /*AI_Check_BuffIndex*/)) && target != null && getDistanceToTarget(target) >= getVariable(0x42FDA76EL /*AI_Special_Action_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4ABAABF5L /*AI_Special_Action_HP*/)) {
			if(Rnd.getChance(getVariable(0x8ECEC3D1L /*AI_Check_BuffIndex_Rate*/))) {
				if (changeState(state -> Special_Chase(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x4DA2BAB9L /*AI_Special_Action*/) == 1 && getVariable(0x4A50EDF7L /*AI_Check_BuffIndex*/) != 0 && target != null && checkBuff(target, getVariable(0x4A50EDF7L /*AI_Check_BuffIndex*/)) && target != null && getDistanceToTarget(target) <= getVariable(0x42FDA76EL /*AI_Special_Action_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4ABAABF5L /*AI_Special_Action_HP*/)) {
			if(Rnd.getChance(getVariable(0x8ECEC3D1L /*AI_Check_BuffIndex_Rate*/))) {
				if (changeState(state -> Special_Action(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x2577BBB2L /*AI_BT_Summon1_MemberCount*/) && getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x82A6D61AL /*_SummonCount1*/) >= 1 && target != null && getDistanceToTarget(target) <= getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/)) {
			if(Rnd.getChance(getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/))) {
				if (changeState(state -> Battle_Summon1(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0xB6A7EA69L /*AI_BT_Summon2_MemberCount*/) && getVariable(0x2CCF3240L /*AI_BT_Summon2*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xEE6747DL /*_SummonCount2*/) >= 1 && target != null && getDistanceToTarget(target) <= getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CCF3240L /*AI_BT_Summon2*/))) {
				if (changeState(state -> Battle_Summon2(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x920F2D8DL /*AI_BT_Summon3_MemberCount*/) && getVariable(0xA71DD0EFL /*AI_BT_Summon3*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xB9D9DDBAL /*_SummonCount3*/) >= 1 && target != null && getDistanceToTarget(target) <= getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/)) {
			if(Rnd.getChance(getVariable(0xA71DD0EFL /*AI_BT_Summon3*/))) {
				if (changeState(state -> Battle_Summon3(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_Warp(blendTime)))
					return;
			}
		}
		if (getVariable(0x44624429L /*AI_MoveWarp*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x71087D8L /*AI_MoveWarp_Attack*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0xCE5BB4E3L /*AI_MoveWarp_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x25DEC022L /*AI_MoveWarp_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAB67EBDCL /*AI_MoveWarp_HP*/)) {
			if(Rnd.getChance(getVariable(0x44624429L /*AI_MoveWarp*/))) {
				if (changeState(state -> Move_WarpAttack(blendTime)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= 155) {
			if(Rnd.getChance(getVariable(0x85CE5989L /*AI_Turn180*/))) {
				if (changeState(state -> Turn_180(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= -155) {
			if(Rnd.getChance(getVariable(0x85CE5989L /*AI_Turn180*/))) {
				if (changeState(state -> Turn_180(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) < getVariable(0x33A2279L /*Ai_TurnDegree_Left*/)) {
			if(Rnd.getChance(getVariable(0xCA881A37L /*AI_TurnAction*/))) {
				if (changeState(state -> Turn_Left(blendTime)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) > getVariable(0x66F7F942L /*Ai_TurnDegree_Right*/)) {
			if(Rnd.getChance(getVariable(0xCA881A37L /*AI_TurnAction*/))) {
				if (changeState(state -> Turn_Right(blendTime)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0x9F40743BL /*AI_ES_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x61AC0DF1L /*AI_ES_HP*/)) {
			if(Rnd.getChance(getVariable(0x36D3C519L /*AI_Escape*/))) {
				if (changeState(state -> Move_Escape(0.1)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0xF79690FDL /*AI_RA_MinDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xA789A0D9L /*AI_RA_HP*/)) {
			if(Rnd.getChance(getVariable(0xE1EDA4F4L /*AI_RunAway*/))) {
				if (changeState(state -> Move_RunAway(0.1)))
					return;
			}
		}
		if (target != null && (getAngleToTarget(target) >= -40 && getAngleToTarget(target) <= 40) && target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= getVariable(0xDFE956BEL /*AI_BS_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x38DF4DC7L /*AI_BS_HP*/)) {
			if(Rnd.getChance(getVariable(0x3D6C82A2L /*AI_Backstep*/))) {
				if (changeState(state -> Move_BackStep(0.1)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x6B061951L /*AI_MA_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC3FD8E9L /*AI_MA_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x3A82EEFCL /*AI_MA_HP*/) && getVariable(0x81898428L /*AI_MoveAround_PreAction*/) == 1) {
			if(Rnd.getChance(getVariable(0x60A54E45L /*AI_MoveAround*/))) {
				if (changeState(state -> Move_Around_PreAction(0.5)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x6B061951L /*AI_MA_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC3FD8E9L /*AI_MA_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x3A82EEFCL /*AI_MA_HP*/)) {
			if(Rnd.getChance(getVariable(0x60A54E45L /*AI_MoveAround*/))) {
				if (changeState(state -> Move_Around(0.5)))
					return;
			}
		}
		if (getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xB3A90433L /*AI_BT_DestructAttack_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x45AEF498L /*AI_BT_DestructAttack_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x7FFDF339L /*AI_BT_DestructAttack*/))) {
				if (changeState(state -> Battle_DestructAttack(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xBDC07D4BL /*AI_BT_Attack3*/) > 0 && getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0x23576610L /*AI_BT_Attack2*/) > 0 && getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
					return;
			}
		}
		if (getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/) > 0 && getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1_Rotate(0.3)))
					return;
			}
		}
		if (getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xD741F92L /*AI_TP_Attack1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getAngleToTarget(target) >= -30 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) >= getVariable(0x4B1E3044L /*AI_TP_Attack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xBBCD1865L /*AI_TP_Attack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x645BEC9FL /*AI_TP_Attack1_HP*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xD741F92L /*AI_TP_Attack1*/))) {
				if (changeState(state -> Battle_TargetPositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xBE61A9BCL /*AI_Chaser_PreAction*/) == 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser_PreAction(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.5)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && !isCreatureVisible(target, false)) {
			if (changeState(state -> Move_Chaser(0.5)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Battle_Wait_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC5155211L /*Battle_Wait_Swim*/);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
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
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/) > 0 && target != null && target != null && getTargetHp(target) > 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && target != null && getAngleToTarget(target) >= getVariable(0xBACBD4B0L /*AI_BT_RangeAttack1_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0x424127F0L /*AI_BT_RangeAttack1_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1_Swim(0.3)))
					return;
			}
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser_Swim(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xBE61A9BCL /*AI_Chaser_PreAction*/) == 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser_PreAction_Swim(0.1)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && getDistanceToTarget(target) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser_Swim(0.5)))
				return;
		}
		if (target != null && target != null && getTargetHp(target) > 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && target != null && !isCreatureVisible(target, false)) {
			if (changeState(state -> Move_Chaser_Swim(0.5)))
				return;
		}
		doAction(902766309L /*BATTLE_WAIT_SWIM*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Swim(blendTime), 500));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x67695F37L /*Lost_Target*/);
		setVariable(0x21C76BFFL /*_isWater*/, getBitFlag_NaviType(getCurrentPos_NaviType(getBodyHeight()),getNaviTypeStringToEnum("water")));
		if (getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Lost_Target_Swim(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Lost_Target_Ground(blendTime)));
	}

	protected void Lost_Target_Ground(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6AC0C2A3L /*Lost_Target_Ground*/);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_GoChase(0.1)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && (!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect))))) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_GoChase(0.1)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && (!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect))))) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Move_Chaser(0.5)))
					return;
			}
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && (!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect))))) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Move_Chaser(0.5)))
					return;
			}
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 0 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isTargetDeliver(object) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xB78C1A8FL /*_WayPoint*/) == 1) {
			if (changeState(state -> AttackMove_Destination(blendTime)))
				return;
		}
		if (getVariable(0xB78C1A8FL /*_WayPoint*/) == 2) {
			if (changeState(state -> AttackMoveFast_Destination(blendTime)))
				return;
		}
		if (getVariable(0xB78C1A8FL /*_WayPoint*/) == 3) {
			if (changeState(state -> AttackMove_Target(blendTime)))
				return;
		}
		if (getVariable(0xB78C1A8FL /*_WayPoint*/) == 4) {
			if (changeState(state -> AttackMoveFast_Target(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Lost_Target_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5874C874L /*Lost_Target_Swim*/);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Player, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_GoChase_Swim(0.1)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Player, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy_Swim(0.1)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Player, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Move_Chaser_Swim(0.1)))
					return;
			}
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 0 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Wait_Swim(0.3)))
				return;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Player, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait_Swim(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Special_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xA232E79BL /*Special_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x42FDA76EL /*AI_Special_Action_Distance*/)) {
			if (changeState(state -> Special_Action(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 1 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if(getCallCount() == getVariable(0x5AA84EA9L /*AI_EC_LimitLoop*/)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		doAction(1471226122L /*SPECIAL_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Special_Chase(blendTime), 1500)));
	}

	protected void Special_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9ADE146BL /*Special_Action*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1259299072L /*SPECIAL_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack1_NoRotate(0.1)))
				return;
		}
		changeState(state -> Battle_Attack1_Rotate(blendTime));
	}

	protected void Battle_Attack1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x33FF8DD5L /*Battle_Attack1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x177460ABL /*AI_BattleAttack1_Count*/) == 1) {
				if (changeState(state -> Battle_Attack1_End(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Attack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC37AE191L /*Battle_Attack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x177460ABL /*AI_BattleAttack1_Count*/) == 1) {
				if (changeState(state -> Battle_Attack1_End(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Attack1_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30896617L /*Battle_Attack1_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376609251L /*BATTLE_ATTACK1_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Attack1_End(blendTime), 10000));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_Attack2_Rotate(blendTime));
	}

	protected void Battle_Attack2_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF62732EDL /*Battle_Attack2_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack3_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_Attack3_Rotate(blendTime));
	}

	protected void Battle_Attack3_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC39F0C4AL /*Battle_Attack3_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x38730E2CL /*Battle_Attack3_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A7FFDBBL /*Battle_ChargeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3904990386L /*BATTLE_CHARGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6F4B95A3L /*Battle_ChargeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(435020623L /*BATTLE_CHARGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_TargetPositionAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB049C4D7L /*Battle_TargetPositionAttack1*/);
		doAction(1662588112L /*BATTLE_POSITIONATTACK1*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_TargetPositionAttack1_Ing(blendTime), 2000));
	}

	protected void Battle_TargetPositionAttack1_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xB1B3C819L /*Battle_TargetPositionAttack1_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3114108399L /*BATTLE_POSITIONATTACK1_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_TargetPositionAttack1_End(blendTime), 2000)));
	}

	protected void Battle_TargetPositionAttack1_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6E8C2803L /*Battle_TargetPositionAttack1_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(898509181L /*BATTLE_POSITIONATTACK1_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
							if (getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack1_NoRotate(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack1_Rotate(blendTime));
	}

	protected void Battle_RangeAttack1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34C25ACL /*Battle_RangeAttack1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_RangeAttack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x79AD2A8AL /*Battle_RangeAttack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_RangeAttack1_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFC0E5B06L /*Battle_RangeAttack1_Swim*/);
							if (getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack1_NoRotate_Swim(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack1_Rotate_Swim(blendTime));
	}

	protected void Battle_RangeAttack1_Rotate_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F53A199L /*Battle_RangeAttack1_Rotate_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2329299101L /*BATTLE_RANGEATTACK1_SWIM*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait_Swim(blendTime));
		});
	}

	protected void Battle_RangeAttack1_NoRotate_Swim(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAA692D2EL /*Battle_RangeAttack1_NoRotate_Swim*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2329299101L /*BATTLE_RANGEATTACK1_SWIM*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait_Swim(blendTime));
		});
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
							if (getVariable(0x4F80D5A8L /*AI_BT_RangeAttack2_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack2_NoRotate(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack2_Rotate(blendTime));
	}

	protected void Battle_RangeAttack2_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE5C096DL /*Battle_RangeAttack2_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/) != 999) {
				setVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/, getVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_RangeAttack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA781BA6DL /*Battle_RangeAttack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/) != 999) {
				setVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/, getVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Chase_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B1E3FE7L /*Chase_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4010467008L /*CHASE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Chase_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F732B22L /*Chase_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1351458155L /*CHASE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Chase_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD5435A5L /*Chase_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1648968437L /*CHASE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAA93C72EL /*Damage_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1112546316L /*DAMAGE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA5554808L /*Damage_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2181925973L /*DAMAGE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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

	protected void Around_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1D30708L /*Around_Attack2*/);
							if (getVariable(0x602AD40FL /*AI_MA_Attack2_RotateOff*/) == 1) {
				if (changeState(state -> Around_Attack2_NoRotate(0.3)))
					return;
			}
			changeState(state -> Around_Attack2_Rotate(blendTime));
	}

	protected void Around_Attack2_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5429FE7EL /*Around_Attack2_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3173509898L /*AROUND_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_Attack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x71C5A263L /*Around_Attack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3173509898L /*AROUND_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4C99FC96L /*Around_RangeAttack1*/);
		if (getVariable(0x28EECF08L /*AI_MA_RangeAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Around_RangeAttack1_NoRotate(0.3)))
				return;
		}
		changeState(state -> Around_RangeAttack1_Rotate(blendTime));
	}

	protected void Around_RangeAttack1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93344E20L /*Around_RangeAttack1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) != 999) {
			setVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/, getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) - 1);
		}
		doAction(1080499018L /*AROUND_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD836F8FBL /*Around_RangeAttack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) != 999) {
			setVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/, getVariable(0x406D6B1DL /*AI_MA_RangeAttack1_ShotBullet*/) - 1);
		}
		doAction(1080499018L /*AROUND_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x56BE167DL /*Around_RangeAttack2*/);
		if (getVariable(0x99217A48L /*AI_MA_RangeAttack2_RotateOff*/) == 1) {
			if (changeState(state -> Around_RangeAttack2_NoRotate(0.3)))
				return;
		}
		changeState(state -> Around_RangeAttack2_Rotate(blendTime));
	}

	protected void Around_RangeAttack2_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88E75CCCL /*Around_RangeAttack2_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) != 999) {
			setVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/, getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) - 1);
		}
		doAction(460537979L /*AROUND_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Around_RangeAttack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x81073EC7L /*Around_RangeAttack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) != 999) {
			setVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/, getVariable(0xF7EE9472L /*AI_MA_RangeAttack2_ShotBullet*/) - 1);
		}
		doAction(460537979L /*AROUND_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Summon1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B822FB3L /*Battle_Summon1*/);
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x82A6D61AL /*_SummonCount1*/) - 1);
		doAction(3697722837L /*BATTLE_SUMMON1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Summon2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7BFB62FDL /*Battle_Summon2*/);
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xEE6747DL /*_SummonCount2*/) - 1);
		doAction(3814969495L /*BATTLE_SUMMON2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Summon3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7540224L /*Battle_Summon3*/);
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0xB9D9DDBAL /*_SummonCount3*/) - 1);
		doAction(2882522470L /*BATTLE_SUMMON3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_DestructAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5E2AFD6L /*Battle_DestructAttack*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_Death(blendTime)))
				return;
		}
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		doAction(3496387840L /*BATTLE_DESTRUCTATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Death(blendTime)));
	}

	protected void Battle_Death(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD4F2761DL /*Battle_Death*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void BackStep_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x681F0AB4L /*BackStep_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(676376496L /*BACKSTEP_RANGEATTACK1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xD4E1FB0L /*AI_BS_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0xD4E1FB0L /*AI_BS_RangeAttack1_ShotBullet*/, getVariable(0xD4E1FB0L /*AI_BS_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Movepoint_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFA1508C0L /*Movepoint_End*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Movepoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x244DE952L /*Movepoint_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0xEBC60317L /*_WayPoint1_Inverse*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Move_WayPoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0xEBC60317L /*_WayPoint1_Inverse*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Move_WayPoint_Inverse(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0x331FDCE2L /*_WayPoint2_Inverse*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> Move_WayPoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0x331FDCE2L /*_WayPoint2_Inverse*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> Move_WayPoint2_Inverse(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0x684203A8L /*_WayPoint3_Inverse*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> Move_WayPoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0x684203A8L /*_WayPoint3_Inverse*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> Move_WayPoint3_Inverse(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0xAC9DBF26L /*_WayPoint4_Inverse*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> Move_WayPoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0xAC9DBF26L /*_WayPoint4_Inverse*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> Move_WayPoint4_Inverse(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0x36634413L /*_WayPoint5_Inverse*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 5) {
			if (changeState(state -> Move_WayPoint5(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0x36634413L /*_WayPoint5_Inverse*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 5) {
			if (changeState(state -> Move_WayPoint5_Inverse(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Move_WayPoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8F2D3B47L /*Move_WayPoint*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			if (getVariable(0x549051CDL /*_WayPointLoopType*/) == 1) {
				setVariable(0x19EB970L /*_WayPointLoopCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x9C7C7BD5L /*_Waypoint1_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint1_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint_Inverse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC486D023L /*Move_WayPoint_Inverse*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x9C7C7BD5L /*_Waypoint1_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint1_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9554FCBFL /*Move_WayPoint2*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0xCDEFC6B9L /*_Waypoint2_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint2_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint2_Inverse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAFC31E11L /*Move_WayPoint2_Inverse*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0xCDEFC6B9L /*_Waypoint2_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint2_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x782710B0L /*Move_WayPoint3*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x2B107C14L /*_Waypoint3_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint3_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint3_Inverse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5CC11632L /*Move_WayPoint3_Inverse*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x2B107C14L /*_Waypoint3_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint3_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x750B7E6EL /*Move_WayPoint4*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x46B3DA05L /*_Waypoint4_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint4_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint4_Inverse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4B582493L /*Move_WayPoint4_Inverse*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x46B3DA05L /*_Waypoint4_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint4_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7897BB6FL /*Move_WayPoint5*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x6E58898BL /*_Waypoint5_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint5_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint5_Inverse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3439E40CL /*Move_WayPoint5_Inverse*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x6E58898BL /*_Waypoint5_Wait*/) == 1) {
				if (changeState(state -> Move_WayPoint5_Wait(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint1_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE6FEFC8DL /*Move_WayPoint1_Wait*/);
		doAction(1406060599L /*WAYPOINT1_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Movepoint_Logic(blendTime), 3000));
	}

	protected void Move_WayPoint2_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD9A604BBL /*Move_WayPoint2_Wait*/);
		doAction(3711366926L /*WAYPOINT2_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Movepoint_Logic(blendTime), 3000));
	}

	protected void Move_WayPoint3_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFFBC92B1L /*Move_WayPoint3_Wait*/);
		doAction(327957765L /*WAYPOINT3_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Movepoint_Logic(blendTime), 3000));
	}

	protected void Move_WayPoint4_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA7BCB371L /*Move_WayPoint4_Wait*/);
		doAction(1075833884L /*WAYPOINT4_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Movepoint_Logic(blendTime), 3000));
	}

	protected void Move_WayPoint5_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC2BD723AL /*Move_WayPoint5_Wait*/);
		doAction(2553006136L /*WAYPOINT5_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Movepoint_Logic(blendTime), 3000));
	}

	protected void Move_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA5879D4L /*Move_Destination*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x31357F9CL /*_WayPointIndex*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void MoveFast_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x29B7F003L /*MoveFast_Destination*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x31357F9CL /*_WayPointIndex*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void Move_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5D4A2DE6L /*Move_Target*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderTarget, 0, 0, 0, getVariable(0x8768AE92L /*AI_Move_Destination_Min*/), getVariable(0xA89E4C12L /*AI_Move_Destination_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void MoveFast_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEA00F6BFL /*MoveFast_Target*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderTarget, 0, 0, 0, getVariable(0x8768AE92L /*AI_Move_Destination_Min*/), getVariable(0xA89E4C12L /*AI_Move_Destination_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void AttackMove_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAEC769D6L /*AttackMove_Destination*/);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 1);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x31357F9CL /*_WayPointIndex*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void AttackMoveFast_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1C8AFB3BL /*AttackMoveFast_Destination*/);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 2);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x31357F9CL /*_WayPointIndex*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void AttackMove_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5AAD18DL /*AttackMove_Target*/);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 3);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderTarget, 0, 0, 0, getVariable(0x8768AE92L /*AI_Move_Destination_Min*/), getVariable(0xA89E4C12L /*AI_Move_Destination_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void AttackMoveFast_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB8597FBAL /*AttackMoveFast_Target*/);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 4);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.1)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderTarget, 0, 0, 0, getVariable(0x8768AE92L /*AI_Move_Destination_Min*/), getVariable(0xA89E4C12L /*AI_Move_Destination_Max*/), false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), getVariable(0xBC06334EL /*AI_Move_Destination_CallCycleTime*/))));
	}

	protected void Move_ChaseToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBCCBD2E0L /*Move_ChaseToParent*/);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
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
		}, onExit -> scheduleState(state -> Move_Parent_Complete(blendTime), 1000)));
	}

	protected void Move_ParentPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x838FBB02L /*Move_ParentPath*/);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && isDarkSpiritMonster()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Parent_Complete(blendTime), 1000)));
	}

	protected void Move_Parent_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x44B7ABF0L /*Move_Parent_Complete*/);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void ReadyForRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7D894A34L /*ReadyForRush*/);
		setVariable(0xC9221A03L /*_IsRushMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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

	protected void Damage_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC0FB5440L /*Damage_Normal*/);
		doAction(2420816374L /*DAMAGE_NORMAL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
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
			if (changeState(state -> Lost_Target(blendTime)))
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
			if (changeState(state -> Lost_Target(blendTime)))
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
			if (changeState(state -> Lost_Target(blendTime)))
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
			if (changeState(state -> Lost_Target(blendTime)))
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
			if (changeState(state -> Lost_Target(blendTime)))
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
			if (changeState(state -> Lost_Target(blendTime)))
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

	protected void Damage_Die_Handler(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB0B373DBL /*Damage_Die_Handler*/);
		if (getVariable(0x1CF5EE87L /*_Damage_Stop*/) == 1) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0x4E36E74L /*AI_Stop_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x5FE07E6L /*AI_Stop_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0xBE8B8DBBL /*AI_Stop_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0x54D4B658L /*AI_Stop_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC996B53L /*AI_Stop_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0xB2CB7E71L /*AI_Stop_Distance*/)).forEach(consumer -> consumer.getAi().HandleStop(getActor(), null));
		}
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die_Handler(blendTime), 20000));
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
		if (Rnd.get(100) <= getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/) && (getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _Alert(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF4CC376DL /*_AlertAction*/) == 1) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xF4CC376DL /*_AlertAction*/) == 1 && getVariable(0x1408C89EL /*_IsAlert*/) == 0 && getVariable(0x37E25A7DL /*AI_StartAction*/) == 1 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && target != null && getDistanceToTarget(target) < 1300 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Alert_Start_Action(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xF4CC376DL /*_AlertAction*/) == 1 && getVariable(0x1408C89EL /*_IsAlert*/) == 0 && getVariable(0x37E25A7DL /*AI_StartAction*/) == 0 && getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && target != null && getDistanceToTarget(target) < 1300 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Alert_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && isTargetDeliver(target) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xD110DC99L /*_IsWaterType*/) == 1 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xD110DC99L /*_IsWaterType*/) == 1 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) >= 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && getAngleToTarget(target) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && target != null && isTargetDeliver(target) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xD110DC99L /*_IsWaterType*/) == 0 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && getAngleToTarget(target) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xD110DC99L /*_IsWaterType*/) == 1 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 1 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && getAngleToTarget(target) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xD110DC99L /*_IsWaterType*/) == 1 && getVariable(0xA71F8225L /*AI_IsPlunder*/) == 0 && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && getAngleToTarget(target) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0 && getVariable(0xF4CC376DL /*_AlertAction*/) == 1 && getVariable(0x1408C89EL /*_IsAlert*/) == 0) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1300).forEach(consumer -> consumer.getAi()._Alert(getActor(), null));
		}
		if ((getState() == 0xC9F2CEDAL /*Alert_Start_Action*/ || getState() == 0x8F198378L /*Alert_Wait*/ || getState() == 0xEE1EB4F8L /*Alert_Search_Enemy*/ || getState() == 0xD46EF24L /*Alert_Battle_Wait*/)) {
			if (changeState(state -> Alert_Battle_Wait_Logic(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/)) {
			if (changeState(state -> Battle_Wait(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x59B63714L /*Search_Enemy_Swim*/ || getState() == 0x45337C5FL /*Search_Enemy_GoChase_Swim*/)) {
			if (changeState(state -> Battle_Wait_Swim(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x39B3FBC2L /*Move_Chaser*/ || getState() == 0x4C327D19L /*HighSpeed_Chaser*/ || getState() == 0x55F437ADL /*Move_Around*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0x67695F37L /*Lost_Target*/ || getState() == 0xB5FDC949L /*Battle_Attack1*/ || getState() == 0xC349CD1FL /*Battle_RangeAttack1*/ || getState() == 0xC0FB5440L /*Damage_Normal*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0xBE9509ECL /*AI_Damage_Normal*/) == 1) {
			if (changeState(state -> Damage_Normal(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x3BC297BEL /*AI_Run_Random_Set*/) >= 1 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Run_Random(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xA8755B38L /*Wait_Swim*/ || getState() == 0x2C8B2FEDL /*Move_Random_Swim*/ || getState() == 0xC4DD1E79L /*Start_Action_Swim*/) && getVariable(0x3BC297BEL /*AI_Run_Random_Set*/) >= 1 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Run_Random_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x881B0A76L /*Start_Action*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x881B0A76L /*Start_Action*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) >= 1 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xA8755B38L /*Wait_Swim*/ || getState() == 0x2AA53F1DL /*Move_Random_End_Swim*/ || getState() == 0x547252A4L /*Move_Random_EndAction_Swim*/ || getState() == 0x2C8B2FEDL /*Move_Random_Swim*/ || getState() == 0xC4DD1E79L /*Start_Action_Swim*/ || getState() == 0xAC1B136DL /*CheckLevel_RunAway_Swim*/ || getState() == 0x6E25B419L /*CheckLevel_Escape_Swim*/ || getState() == 0x75D6C08DL /*Wait_CheckLevel_Swim*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) >= 1 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Search_Enemy_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x8816D447L /*_Weapon_Out*/) == 1 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Weapon_Out(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xA8755B38L /*Wait_Swim*/ || getState() == 0x2AA53F1DL /*Move_Random_End_Swim*/ || getState() == 0x547252A4L /*Move_Random_EndAction_Swim*/ || getState() == 0x2C8B2FEDL /*Move_Random_Swim*/ || getState() == 0xC4DD1E79L /*Start_Action_Swim*/ || getState() == 0xAC1B136DL /*CheckLevel_RunAway_Swim*/ || getState() == 0x6E25B419L /*CheckLevel_Escape_Swim*/ || getState() == 0x75D6C08DL /*Wait_CheckLevel_Swim*/ || getState() == 0x59B63714L /*Search_Enemy_Swim*/ || getState() == 0x45337C5FL /*Search_Enemy_GoChase_Swim*/) && getVariable(0x8816D447L /*_Weapon_Out*/) == 1 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Weapon_Out_Swim(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) == 0 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Battle_Wait(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xA8755B38L /*Wait_Swim*/ || getState() == 0x2AA53F1DL /*Move_Random_End_Swim*/ || getState() == 0x547252A4L /*Move_Random_EndAction_Swim*/ || getState() == 0x2C8B2FEDL /*Move_Random_Swim*/ || getState() == 0xC4DD1E79L /*Start_Action_Swim*/ || getState() == 0xAC1B136DL /*CheckLevel_RunAway_Swim*/ || getState() == 0x6E25B419L /*CheckLevel_Escape_Swim*/ || getState() == 0x75D6C08DL /*Wait_CheckLevel_Swim*/ || getState() == 0x59B63714L /*Search_Enemy_Swim*/ || getState() == 0x45337C5FL /*Search_Enemy_GoChase_Swim*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) == 0 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0) {
			if (changeState(state -> Battle_Wait_Swim(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if(Rnd.getChance(getVariable(0x6A933A62L /*AI_Counter_KnockBack*/))) {
			if (changeState(state -> Counter_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
		if (getVariable(0x7ADD470CL /*AI_Counter_KnockDown*/) == 0 && getVariable(0xF9798513L /*AI_Damage_KnockDown*/) == 1) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7ADD470CL /*AI_Counter_KnockDown*/) != 0) {
			if(Rnd.getChance(getVariable(0x7ADD470CL /*AI_Counter_KnockDown*/))) {
				if (changeState(state -> Counter_KnockDown(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
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
		if (getVariable(0x6FE070DDL /*AI_Counter_Bound*/) == 0 && getVariable(0x9B63B813L /*AI_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x6FE070DDL /*AI_Counter_Bound*/) != 0) {
			if(Rnd.getChance(getVariable(0x6FE070DDL /*AI_Counter_Bound*/))) {
				if (changeState(state -> Counter_Bound(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
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
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) != 0) {
			if(Rnd.getChance(getVariable(0x382E1DD9L /*AI_Counter_Stun*/))) {
				if (changeState(state -> Counter_Stun(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
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
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, getSenderInfoDestination());
		if (changeState(state -> Move_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, getSenderInfoDestination());
		if (changeState(state -> MoveFast_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Move_Target(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MoveFast_Target(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationAttackMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, getSenderInfoDestination());
		if (changeState(state -> AttackMove_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationAttackFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, getSenderInfoDestination());
		if (changeState(state -> AttackMoveFast_Destination(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetAttackMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> AttackMove_Target(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetAttackFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> AttackMoveFast_Target(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ForcePeaceful(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ForceReturn(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ForceSleep(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOrderSkill1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ForceWakeup(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x858C921CL /*_ReviveCount*/) >= 1) {
			if (changeState(state -> Revive(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x1CF5EE87L /*_Damage_Stop*/) == 1) {
			if (changeState(state -> Damage_Die_Handler(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if (getVariable(0xF6AE5F64L /*AI_CombiStun*/) == 1 && getSelfCombinePoint() == getVariable(0x2039E04FL /*AI_CombiPoint_Stun*/) && getState() != 0x3FB3341CL /*Damage_Stun*/) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x8A623399L /*AI_CombiMoveSpeed*/) == 1 && getSelfCombinePoint() == getVariable(0x91BF1152L /*AI_CombiPoint_MoveSpeed*/) && getState() != 0x3FB3341CL /*Damage_Stun*/) {
			if (changeState(state -> Combi_MoveSpeedDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xE8122469L /*AI_CombiAttackSpeed*/) == 1 && getSelfCombinePoint() == getVariable(0x94D7EE0EL /*AI_CombiPoint_AttackSpeed*/) && getState() != 0x3FB3341CL /*Damage_Stun*/) {
			if (changeState(state -> Combi_AttackSpeedDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x689F7572L /*AI_CombiPVDown*/) == 1 && getSelfCombinePoint() == getVariable(0xDCDC21C5L /*AI_CombiPoint_PVDown*/) && getState() != 0x3FB3341CL /*Damage_Stun*/) {
			if (changeState(state -> Combi_PVDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xBEBAC2BCL /*AI_CombiBuffCancel*/) == 1 && getState() != 0x3FB3341CL /*Damage_Stun*/) {
			if (changeState(state -> Combi_BuffCancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Battle_Wait(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x39B3FBC2L /*Move_Chaser*/ || getState() == 0x4C327D19L /*HighSpeed_Chaser*/ || getState() == 0x55F437ADL /*Move_Around*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0x67695F37L /*Lost_Target*/ || getState() == 0xB5FDC949L /*Battle_Attack1*/ || getState() == 0xC349CD1FL /*Battle_RangeAttack1*/ || getState() == 0xC0FB5440L /*Damage_Normal*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0xBE9509ECL /*AI_Damage_Normal*/) == 1 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Damage_Normal(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x8377635AL /*Move_Random*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x881B0A76L /*Start_Action*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x9AE09B3DL /*AI_FA_SufferAttack1_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0xE507A51FL /*AI_FA_SufferAttack1*/))) {
				if (changeState(state -> Damage_Attack1(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0x881B0A76L /*Start_Action*/ && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && target != null && getDistanceToTarget(target) <= getVariable(0x616E53A8L /*AI_FA_SufferAttack2_Distance*/) && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if(Rnd.getChance(getVariable(0x11BFED07L /*AI_FA_SufferAttack2*/))) {
				if (changeState(state -> Damage_Attack2(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) >= 1 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x8816D447L /*_Weapon_Out*/) == 1 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Weapon_Out(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x838FBB02L /*Move_ParentPath*/ || getState() == 0xBCCBD2E0L /*Move_ChaseToParent*/ || getState() == 0x455AE195L /*Move_Random_End*/ || getState() == 0x8D36275BL /*Move_Random_EndAction*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x881B0A76L /*Start_Action*/ || getState() == 0xA2EDFBF7L /*CheckLevel_RunAway*/ || getState() == 0xF44A9B48L /*CheckLevel_Escape*/ || getState() == 0xA664004CL /*Wait_CheckLevel*/ || getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0xB3CBD2A4L /*Search_Enemy_GoChase*/ || getState() == 0xFA1508C0L /*Movepoint_End*/ || getState() == 0x244DE952L /*Movepoint_Logic*/ || getState() == 0x8F2D3B47L /*Move_WayPoint*/ || getState() == 0x9554FCBFL /*Move_WayPoint2*/ || getState() == 0x782710B0L /*Move_WayPoint3*/ || getState() == 0x750B7E6EL /*Move_WayPoint4*/ || getState() == 0x7897BB6FL /*Move_WayPoint5*/) && getVariable(0x60A05FC4L /*AI_FA_SufferAction_Set*/) == 0 && getVariable(0x8816D447L /*_Weapon_Out*/) == 0 && getVariable(0x2D70D5BBL /*AI_AttackerType*/) != 0 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Battle_Wait(0.5)))
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
		setVariable(0x21E2720L /*_UseToOwnerPath*/, 1);
		if (getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 1) {
			if (changeState(state -> Move_ParentPath(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleHelpMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (Rnd.get(100) <= getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
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
		setVariable(0xC64A8E9BL /*_PartyReturn*/, 1);
		setVariable(0x870CD143L /*_IsPartyMember*/, 0);
		if (getVariable(0xDDEBBD5L /*_Party_Die*/) == 1) {
			if (changeState(state -> Battle_Death(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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

	@Override
	public EAiHandlerResult HandleReadyForRush(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> ReadyForRush(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
