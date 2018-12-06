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
@IAIName("party_leader_main")
public class Ai_party_leader_main extends CreatureAI {
	public Ai_party_leader_main(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xB3AF1929L /*_Dice*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0xC50373B9L /*_PartyMoveCount*/, 0);
		setVariable(0xBE2FF277L /*_haveLostSheep*/, 0);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xB78C1A8FL /*_WayPoint*/, 0);
		setVariableArray(0x31357F9CL /*_WayPointIndex*/, new Integer[] { 0, 0 });
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0x99703876L /*_SummonCount*/, 1);
		setVariable(0x7492188CL /*_FailFindPath*/, 0);
		setVariable(0x4F34C1C6L /*_AttackerType*/, getVariable(0x2D70D5BBL /*AI_AttackerType*/));
		setVariable(0xE35466B5L /*_TestType*/, getVariable(0x95784CFCL /*AI_TestType*/));
		setVariable(0x30AB9195L /*_PartyLeaderType*/, getVariable(0x1A348BADL /*AI_PartyLeaderType*/));
		setVariable(0x925166FDL /*_FirstAttack*/, getVariable(0x4EAF441FL /*AI_FirstAttack*/));
		setVariable(0x324F8EDBL /*_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		setVariable(0x7163CC1AL /*_WayPointKind*/, getVariable(0xA4D483DBL /*AI_WayPointKind*/));
		setVariable(0x95E21D80L /*_BT_Summon1*/, getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/));
		setVariable(0x3D5CF9E1L /*_BT_Summon2*/, getVariable(0x2CCF3240L /*AI_BT_Summon2*/));
		setVariable(0x88B92208L /*_BT_Summon3*/, getVariable(0xA71DD0EFL /*AI_BT_Summon3*/));
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
		setVariable(0xFB8913CAL /*_BT_Summon1_Distance*/, getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/));
		setVariable(0xBC03C888L /*_BT_Summon2_Distance*/, getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/));
		setVariable(0x4BB0DE11L /*_BT_Summon3_Distance*/, getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/));
		setVariable(0xD7DC43EBL /*_BT_Summon1_HP*/, getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/));
		setVariable(0xE9A6826AL /*_BT_Summon2_HP*/, getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/));
		setVariable(0x3A5B766DL /*_BT_Summon3_HP*/, getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/));
		setVariable(0x4F834EECL /*_BattleAttack1_RotateOff*/, getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/));
		setVariable(0x3ED92254L /*_BattleAttack2_RotateOff*/, getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/));
		setVariable(0xDA809C13L /*_BattleAttack3_RotateOff*/, getVariable(0xAE556258L /*AI_BattleAttack3_RotateOff*/));
		setVariable(0x13C0851EL /*_BT_RangeAttack1_RotateOff*/, getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/));
		setVariable(0x6D141007L /*_BT_RangeAttack2_RotateOff*/, getVariable(0x4F80D5A8L /*AI_BT_RangeAttack2_RotateOff*/));
		setVariable(0x98E70ACCL /*_BT_RangeAttack3_RotateOff*/, getVariable(0x4EA7E3D1L /*AI_BT_RangeAttack3_RotateOff*/));
		setVariable(0xEEC351A6L /*_EC_HighSpeedChase*/, getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/));
		setVariable(0x4148BCF8L /*_EnemyChase*/, getVariable(0xE97DEBF8L /*AI_EnemyChase*/));
		setVariable(0xB565E74EL /*_EC_Distance*/, getVariable(0x5103FB80L /*AI_EC_Distance*/));
		setVariable(0xC9221A03L /*_IsRushMode*/, getVariable(0xD241FE28L /*AI_IsRushMode*/));
		setVariable(0x4C6E8E19L /*_EC_LimitDistance*/, getVariable(0x468D3572L /*AI_EC_LimitDistance*/));
		if (target != null && getDistanceToTarget(target) < getVariable(0xE1A3C8C3L /*AI_Party_Distance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x164EDE6DL /*AI_Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xA7BAC7B1L /*AI_Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0xAEDD23B0L /*AI_Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xD2B80E1L /*AI_Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x3FE89DF6L /*AI_Party_CharacterKey5*/))) {
			createParty(getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/), getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Party_Wait(blendTime));
	}

	protected void Goat_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6BB3A90AL /*Goat_Logic*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(8));
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		if (getVariable(0xC50373B9L /*_PartyMoveCount*/) > 5 && getVariable(0xBE2FF277L /*_haveLostSheep*/) == 1) {
			setVariable(0xBE2FF277L /*_haveLostSheep*/, 0);
		}
		if (getVariable(0xC50373B9L /*_PartyMoveCount*/) > 10 && getVariable(0xBE2FF277L /*_haveLostSheep*/) == 0) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleCanRunAway(getActor(), null));
		}
		if (getVariable(0xBE2FF277L /*_haveLostSheep*/) == 1) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleDonRunAway(getActor(), null));
		}
		setVariable(0xC50373B9L /*_PartyMoveCount*/, getVariable(0xC50373B9L /*_PartyMoveCount*/) + 1);
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			if (changeState(state -> Party_MovePoint_Start_1(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			if (changeState(state -> Party_MovePoint_Start_2(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			if (changeState(state -> Party_MovePoint_Start_3(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			if (changeState(state -> Party_MovePoint_Start_4(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			if (changeState(state -> Party_MovePoint_Start_5(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			if (changeState(state -> Party_MovePoint_Start_6(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			if (changeState(state -> Party_MovePoint_Start_7(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			if (changeState(state -> Party_MovePoint_Start_8(blendTime)))
				return;
		}
		changeState(state -> Party_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1500));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		if (target != null && getDistanceToTarget(target) < getVariable(0xE1A3C8C3L /*AI_Party_Distance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x164EDE6DL /*AI_Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xA7BAC7B1L /*AI_Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0xAEDD23B0L /*AI_Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xD2B80E1L /*AI_Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x3FE89DF6L /*AI_Party_CharacterKey5*/))) {
			createParty(getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/), getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/));
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 2) {
			if (changeState(state -> Goat_Logic(0.3)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 1 && getVariable(0xE35466B5L /*_TestType*/) == 0) {
			if (changeState(state -> Party_MovePoint(0.3)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 1 && getVariable(0xE35466B5L /*_TestType*/) == 1) {
			if (changeState(state -> Party_MovePoint_Test(0.3)))
				return;
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) <= 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, false, object -> getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Party_Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) <= 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Party_Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, false, object -> getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (Rnd.get(100) < getVariable(0x64490D98L /*AI_RandomMove*/)) {
			if(getCallCount() == 5) {
				if (changeState(state -> Party_WalkRandom(0.3)))
					return;
			}
		}
		if (getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0 && getPartyMembersCount()>= getVariable(0x5CC080D0L /*AI_PartyMemberCount*/)) {
			if (changeState(state -> Party_ReadyForRush(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) <= 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Party_Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()>= getVariable(0x5CC080D0L /*AI_PartyMemberCount*/) && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			if (changeState(state -> Party_ReadyForRush(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0xE1A3C8C3L /*AI_Party_Distance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x164EDE6DL /*AI_Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xA7BAC7B1L /*AI_Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0xAEDD23B0L /*AI_Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xD2B80E1L /*AI_Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x3FE89DF6L /*AI_Party_CharacterKey5*/))) {
			createParty(getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/), getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/));
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, getVariable(0xF561B270L /*AI_RandomMove_Min*/), getVariable(0xEA5276A5L /*AI_RandomMove_Max*/), true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 2000));
	}

	protected void Party_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC252DE8L /*Party_Battle_Wait*/);
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -30 && getVariable(0xE5BD13F2L /*_Degree*/) <= 30 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -30 && getVariable(0xE5BD13F2L /*_Degree*/) <= 30 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAFB4C799L /*AI_BT_RangeAttack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4F73DC38L /*AI_BT_RangeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/)) {
			if (changeState(state -> HighSpeed_Chaser(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x4148BCF8L /*_EnemyChase*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.1)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) <= 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getTargetLevel(object) <= getVariable(0x233F196EL /*AI_CheckLevel*/))) {
				if (changeState(state -> Party_Battle_Wait(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 3000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 2000)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xE5BD13F2L /*_Degree*/) > -30 && getVariable(0xE5BD13F2L /*_Degree*/) < 30 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xE5BD13F2L /*_Degree*/) > -30 && getVariable(0xE5BD13F2L /*_Degree*/) < 30 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAFB4C799L /*AI_BT_RangeAttack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4F73DC38L /*AI_BT_RangeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 500)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/) && getVariable(0xC9221A03L /*_IsRushMode*/) == 0) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x2951F4DAL /*AI_EC_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x9C4927FEL /*AI_EC_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0x79C2E1A0L /*AI_EC_Attack1*/))) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x33993721L /*AI_EC_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4FD081E4L /*AI_EC_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CE6579BL /*AI_EC_Attack2*/))) {
				if (changeState(state -> Chase_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xF658D7B4L /*AI_EC_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x94A188D1L /*AI_EC_Attack3_HP*/)) {
			if(Rnd.getChance(getVariable(0x7A40F2AEL /*AI_EC_Attack3*/))) {
				if (changeState(state -> Chase_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x101CB71DL /*AI_EC_HighSpeedChase*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xE5BD13F2L /*_Degree*/) > -30 && getVariable(0xE5BD13F2L /*_Degree*/) < 30 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xF5FA4070L /*AI_BT_ChargeAttack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE69565E2L /*AI_BT_ChargeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x69679F4CL /*AI_BT_ChargeAttack2*/))) {
				if (changeState(state -> Battle_ChargeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xE5BD13F2L /*_Degree*/) > -30 && getVariable(0xE5BD13F2L /*_Degree*/) < 30 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xC983C8CCL /*AI_BT_ChargeAttack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xE29339A7L /*AI_BT_ChargeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xE468D9D1L /*AI_BT_ChargeAttack1*/))) {
				if (changeState(state -> Battle_ChargeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xC944200L /*AI_BT_Attack3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4D2683ABL /*AI_BT_Attack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.0)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x95DD3572L /*AI_BT_RangeAttack3_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0xB913E239L /*AI_BT_RangeAttack3_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xAFB4C799L /*AI_BT_RangeAttack3_HP*/)) {
			if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x1DD7BDC8L /*AI_BT_RangeAttack2_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x8C75F6C5L /*AI_BT_RangeAttack2_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4F73DC38L /*AI_BT_RangeAttack2_HP*/)) {
			if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (getVariable(0x4F34C1C6L /*_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) > getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 500)));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (getVariable(0x4F834EECL /*_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack1_NoRotate(0.0)))
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
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Attack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC37AE191L /*Battle_Attack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (getVariable(0x3ED92254L /*_BattleAttack2_RotateOff*/) == 1) {
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
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCA4082FDL /*Battle_Attack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (getVariable(0xDA809C13L /*_BattleAttack3_RotateOff*/) == 1) {
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
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x38730E2CL /*Battle_Attack3_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A7FFDBBL /*Battle_ChargeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3904990386L /*BATTLE_CHARGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_ChargeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6F4B95A3L /*Battle_ChargeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(435020623L /*BATTLE_CHARGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		if (getVariable(0x13C0851EL /*_BT_RangeAttack1_RotateOff*/) == 1) {
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
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x79AD2A8AL /*Battle_RangeAttack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
		if (getVariable(0x6D141007L /*_BT_RangeAttack2_RotateOff*/) == 1) {
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
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA781BA6DL /*Battle_RangeAttack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x395B66CCL /*Battle_RangeAttack3*/);
		if (getVariable(0x98E70ACCL /*_BT_RangeAttack3_RotateOff*/) == 1) {
			if (changeState(state -> Battle_RangeAttack3_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_RangeAttack3_Rotate(blendTime));
	}

	protected void Battle_RangeAttack3_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA0FFB070L /*Battle_RangeAttack3_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack3_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6A073F69L /*Battle_RangeAttack3_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Chase_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B1E3FE7L /*Chase_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4010467008L /*CHASE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Chase_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F732B22L /*Chase_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1351458155L /*CHASE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Chase_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD5435A5L /*Chase_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1648968437L /*CHASE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Party_MovePoint_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x63922042L /*Party_MovePoint_Test*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Test_Cow_05 Test_Cow_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1B468345L /*Party_MovePoint*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "cow_17 cow_35", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_ReadyForRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x76F50547L /*Party_ReadyForRush*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleReadyForRush(getActor(), null));
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Rush(blendTime), 1000));
	}

	protected void Party_Rush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC7D7B6B9L /*Party_Rush*/);
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 0) {
			if (changeState(state -> Party_Rush_Test(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 1) {
			if (changeState(state -> Party_Rush_1(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 2) {
			if (changeState(state -> Party_Rush_2(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 3) {
			if (changeState(state -> Party_Rush_3(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 4) {
			if (changeState(state -> Party_Rush_4(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 5) {
			if (changeState(state -> Party_Rush_5(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 6) {
			if (changeState(state -> Party_Rush_6(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 7) {
			if (changeState(state -> Party_Rush_7(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 8) {
			if (changeState(state -> Party_Rush_8(0.3)))
				return;
		}
		if (getVariable(0x7163CC1AL /*_WayPointKind*/) == 9) {
			if (changeState(state -> Party_Rush_9(0.3)))
				return;
		}
		changeState(state -> Party_Wait(blendTime));
	}

	protected void Party_Rush_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C838294L /*Party_Rush_Test*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x26EE8F1FL /*Party_Rush_1*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w149_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x98E251E8L /*Party_Rush_2*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "WestCamp_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2E403035L /*Party_Rush_3*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "n_att02_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9EFD0E8L /*Party_Rush_4*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "night_attack_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x801DE0F4L /*Party_Rush_5*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "n_att05_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA11BFD7FL /*Party_Rush_6*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "n_att06_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDCA2A6CAL /*Party_Rush_7*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "n_att07_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD2A51A26L /*Party_Rush_8*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Ser_SouthWest_Gate_Event_Arrive_Position", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_Rush_9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x95706BCL /*Party_Rush_9*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "n_att09_01", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 500)));
	}

	protected void Party_MovePoint_Start_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C0D1FB5L /*Party_MovePoint_Start_1*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_1(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE0E93AE6L /*Party_MovePoint_Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_1(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB0DE4DFL /*Party_MovePoint_Go_1*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_1", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2DF7CA6L /*Party_MovePoint_Start_2*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_2(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9DDDAF81L /*Party_MovePoint_Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_2(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x39C62023L /*Party_MovePoint_Go_2*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_2", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6C843A51L /*Party_MovePoint_Start_3*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_3(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A3E4FC7L /*Party_MovePoint_Wait_3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_3(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5A7B6E66L /*Party_MovePoint_Go_3*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_3", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9C17D706L /*Party_MovePoint_Start_4*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_4(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE9C2866FL /*Party_MovePoint_Wait_4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_4(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3DC0383DL /*Party_MovePoint_Go_4*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_4", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA2417838L /*Party_MovePoint_Start_5*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_5(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_5", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7ACF9CA4L /*Party_MovePoint_Wait_5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_5(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDCBDEEF3L /*Party_MovePoint_Go_5*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_5", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD172F3D8L /*Party_MovePoint_Start_6*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_6(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD5EBC468L /*Party_MovePoint_Wait_6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_6(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E80E001L /*Party_MovePoint_Go_6*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_6", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA04F1E0BL /*Party_MovePoint_Start_7*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_7(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_7", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC91413EL /*Party_MovePoint_Wait_7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_7(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCFCB0FE7L /*Party_MovePoint_Go_7*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_7", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEF863015L /*Party_MovePoint_Start_8*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_8(0.3)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_8", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBE70CB81L /*Party_MovePoint_Wait_8*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_8(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE95A9D6EL /*Party_MovePoint_Go_8*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_8", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
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
			scheduleState(state -> Party_Battle_Wait(blendTime), 1000);
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
			scheduleState(state -> Party_Battle_Wait(blendTime), 3000);
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
			scheduleState(state -> Party_Battle_Wait(blendTime), 1000);
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
			scheduleState(state -> Party_Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Party_Battle_Wait(blendTime), 2000);
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
	public EAiHandlerResult _helpme(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && (getTargetCharacterKey(target) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/))) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) != 0) {
			if(Rnd.getChance(getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) <= 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/)) && target != null && getTargetHp(target) > 0 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x324F8EDBL /*_FA_DetectAction_Set*/) >= 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/)) && target != null && getTargetHp(target) > 0 && getVariable(0x30AB9195L /*_PartyLeaderType*/) == 0) {
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
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xBD454E6L /*_FA_SufferAction_Set*/) >= 1 && getVariable(0x4F34C1C6L /*_AttackerType*/) != 0) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xBD454E6L /*_FA_SufferAction_Set*/) == 0 && getVariable(0x4F34C1C6L /*_AttackerType*/) != 0) {
			if (changeState(state -> Party_Battle_Wait(0.3)))
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
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB9CFA843L /*_Damage_Rigid*/) == 1) {
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
		if (changeState(state -> Damage_Fear(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFearReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 1) {
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Party_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getDistanceToTarget(target) < getVariable(0xE1A3C8C3L /*AI_Party_Distance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x164EDE6DL /*AI_Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xA7BAC7B1L /*AI_Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0xAEDD23B0L /*AI_Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xD2B80E1L /*AI_Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x3FE89DF6L /*AI_Party_CharacterKey5*/))) {
			createParty(getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/), getVariable(0xD36A3B3EL /*AI_PartyCount_Min*/));
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleLostWay(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xBE2FF277L /*_haveLostSheep*/, 1);
		if (getVariable(0xBE2FF277L /*_haveLostSheep*/) == 0) {
			setVariable(0xC50373B9L /*_PartyMoveCount*/, 0);
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleDonRunAway(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}
}
