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
@IAIName("monster_object")
public class Ai_monster_object extends CreatureAI {
	public Ai_monster_object(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
		setVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/, getVariable(0x3CBCA867L /*AI_FA_DetectAction*/));
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, 0);
		setVariable(0x25A483AL /*AI_BT_RangeAttack1_CoolCountN*/, 0);
		setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0xAB32793EL /*AI_BT_RangeAttack1_Shot*/));
		setVariable(0xC4CCD61BL /*AI_BT_RangeAttack2_ShotBullet*/, getVariable(0x7296683CL /*AI_BT_RangeAttack2_Shot*/));
		setVariable(0x8E6F3126L /*AI_BT_RangeAttack3_ShotBullet*/, getVariable(0x4092F7B8L /*AI_BT_RangeAttack3_Shot*/));
		setVariable(0x1CF5EE87L /*_Damage_Stop*/, getVariable(0x1A7BA6F4L /*AI_Damage_Stop*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0 && getAngleToTarget(object) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && getAngleToTarget(object) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), getVariable(0x87E37180L /*AI_Wait_CallCycleTime*/) + Rnd.get(-500,500)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
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
		if (target != null && getDistanceToTarget(target) >= 3000) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getPartyMembersCount()<= getVariable(0x2577BBB2L /*AI_BT_Summon1_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x82A6D61AL /*_SummonCount1*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/)) {
			if(Rnd.getChance(getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/))) {
				if (changeState(state -> Battle_Summon1(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0xB6A7EA69L /*AI_BT_Summon2_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xEE6747DL /*_SummonCount2*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CCF3240L /*AI_BT_Summon2*/))) {
				if (changeState(state -> Battle_Summon2(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x920F2D8DL /*AI_BT_Summon3_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xB9D9DDBAL /*_SummonCount3*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/)) {
			if(Rnd.getChance(getVariable(0xA71DD0EFL /*AI_BT_Summon3*/))) {
				if (changeState(state -> Battle_Summon3(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && getVariable(0xE5BD13F2L /*_Degree*/) >= getVariable(0xBACBD4B0L /*AI_BT_RangeAttack1_MinDegree*/) && getVariable(0xE5BD13F2L /*_Degree*/) <= getVariable(0x424127F0L /*AI_BT_RangeAttack1_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Wait_Damage_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8933C40EL /*Wait_Damage_75*/);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait_Damage_75(0.3)))
				return;
		}
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Wait_Damage_75(blendTime)))
				return;
		}
		if (getPartyMembersCount()<= getVariable(0x2577BBB2L /*AI_BT_Summon1_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x82A6D61AL /*_SummonCount1*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/)) {
			if(Rnd.getChance(getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/))) {
				if (changeState(state -> Battle_Summon75_1(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0xB6A7EA69L /*AI_BT_Summon2_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xEE6747DL /*_SummonCount2*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CCF3240L /*AI_BT_Summon2*/))) {
				if (changeState(state -> Battle_Summon75_2(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x920F2D8DL /*AI_BT_Summon3_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xB9D9DDBAL /*_SummonCount3*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/)) {
			if(Rnd.getChance(getVariable(0xA71DD0EFL /*AI_BT_Summon3*/))) {
				if (changeState(state -> Battle_Summon75_3(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && getVariable(0xE5BD13F2L /*_Degree*/) >= getVariable(0xBACBD4B0L /*AI_BT_RangeAttack1_MinDegree*/) && getVariable(0xE5BD13F2L /*_Degree*/) <= getVariable(0x424127F0L /*AI_BT_RangeAttack1_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack75_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack75_1(0.3)))
					return;
			}
		}
		doAction(2712928271L /*BATTLE_WAIT_DAMAGE_75*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_75(blendTime), 1000));
	}

	protected void Wait_Damage_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x56840A98L /*Wait_Damage_50*/);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait_Damage_50(0.3)))
				return;
		}
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		if (getPartyMembersCount()<= getVariable(0x2577BBB2L /*AI_BT_Summon1_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x82A6D61AL /*_SummonCount1*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/)) {
			if(Rnd.getChance(getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/))) {
				if (changeState(state -> Battle_Summon50_1(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0xB6A7EA69L /*AI_BT_Summon2_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xEE6747DL /*_SummonCount2*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CCF3240L /*AI_BT_Summon2*/))) {
				if (changeState(state -> Battle_Summon50_2(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x920F2D8DL /*AI_BT_Summon3_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xB9D9DDBAL /*_SummonCount3*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/)) {
			if(Rnd.getChance(getVariable(0xA71DD0EFL /*AI_BT_Summon3*/))) {
				if (changeState(state -> Battle_Summon50_3(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && getVariable(0xE5BD13F2L /*_Degree*/) >= getVariable(0xBACBD4B0L /*AI_BT_RangeAttack1_MinDegree*/) && getVariable(0xE5BD13F2L /*_Degree*/) <= getVariable(0x424127F0L /*AI_BT_RangeAttack1_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack50_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack50_1(0.3)))
					return;
			}
		}
		doAction(2007861085L /*BATTLE_WAIT_DAMAGE_50*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_50(blendTime), 1000));
	}

	protected void Wait_Damage_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA54E2A5EL /*Wait_Damage_25*/);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait_Damage_50(0.3)))
				return;
		}
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		if (getPartyMembersCount()<= getVariable(0x2577BBB2L /*AI_BT_Summon1_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x82A6D61AL /*_SummonCount1*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0xF5CF1EE5L /*AI_BT_Summon1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x26D1B2E6L /*AI_BT_Summon1_HP*/)) {
			if(Rnd.getChance(getVariable(0x7DFA4FFDL /*AI_BT_Summon1*/))) {
				if (changeState(state -> Battle_Summon25_1(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0xB6A7EA69L /*AI_BT_Summon2_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xEE6747DL /*_SummonCount2*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x1D38150EL /*AI_BT_Summon2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xBC8E6618L /*AI_BT_Summon2_HP*/)) {
			if(Rnd.getChance(getVariable(0x2CCF3240L /*AI_BT_Summon2*/))) {
				if (changeState(state -> Battle_Summon25_2(0.3)))
					return;
			}
		}
		if (getPartyMembersCount()<= getVariable(0x920F2D8DL /*AI_BT_Summon3_MemberCount*/) && getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0xB9D9DDBAL /*_SummonCount3*/) >= 1 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x6930A994L /*AI_BT_Summon3_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xC7E8981DL /*AI_BT_Summon3_HP*/)) {
			if(Rnd.getChance(getVariable(0xA71DD0EFL /*AI_BT_Summon3*/))) {
				if (changeState(state -> Battle_Summon25_3(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) > 0 && getVariable(0xF630F33AL /*_Distance*/) >= getVariable(0xC928C29FL /*AI_BT_RangeAttack1_MinDistance*/) && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x9354A910L /*AI_BT_RangeAttack1_MaxDistance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0xD947DBACL /*AI_BT_RangeAttack1_HP*/) && getVariable(0xE5BD13F2L /*_Degree*/) >= getVariable(0xBACBD4B0L /*AI_BT_RangeAttack1_MinDegree*/) && getVariable(0xE5BD13F2L /*_Degree*/) <= getVariable(0x424127F0L /*AI_BT_RangeAttack1_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
			if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
				if (changeState(state -> Battle_RangeAttack25_1(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/) <= 0 && getVariable(0xF630F33AL /*_Distance*/) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack25_1(0.3)))
					return;
			}
		}
		doAction(3258093981L /*BATTLE_WAIT_DAMAGE_25*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_25(blendTime), 1000));
	}

	protected void Damage_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x72132ECDL /*Damage_75*/);
		doAction(4075634643L /*DAMAGE_75*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_75(blendTime), 2000));
	}

	protected void Damage_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC6E66A1BL /*Damage_50*/);
		doAction(4158814158L /*DAMAGE_50*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_50(blendTime), 2000));
	}

	protected void Damage_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B7613DAL /*Damage_25*/);
		doAction(1904061850L /*DAMAGE_25*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Damage_25(blendTime), 2000));
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

	protected void Battle_Summon75_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8D40411BL /*Battle_Summon75_1*/);
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x82A6D61AL /*_SummonCount1*/) - 1);
		doAction(1600000836L /*BATTLE_SUMMON75_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_75(blendTime)));
	}

	protected void Battle_Summon75_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7FDBBA39L /*Battle_Summon75_2*/);
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xEE6747DL /*_SummonCount2*/) - 1);
		doAction(767535693L /*BATTLE_SUMMON75_2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_75(blendTime)));
	}

	protected void Battle_Summon75_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D6F0D41L /*Battle_Summon75_3*/);
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0xB9D9DDBAL /*_SummonCount3*/) - 1);
		doAction(3163277882L /*BATTLE_SUMMON75_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_75(blendTime)));
	}

	protected void Battle_Summon50_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x14A02548L /*Battle_Summon50_1*/);
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x82A6D61AL /*_SummonCount1*/) - 1);
		doAction(4177388279L /*BATTLE_SUMMON50_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_50(blendTime)));
	}

	protected void Battle_Summon50_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x49938CFBL /*Battle_Summon50_2*/);
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xEE6747DL /*_SummonCount2*/) - 1);
		doAction(2616427784L /*BATTLE_SUMMON50_2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_50(blendTime)));
	}

	protected void Battle_Summon50_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA292ADL /*Battle_Summon50_3*/);
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0xB9D9DDBAL /*_SummonCount3*/) - 1);
		doAction(369546638L /*BATTLE_SUMMON50_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_50(blendTime)));
	}

	protected void Battle_Summon25_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD33E9E9L /*Battle_Summon25_1*/);
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x82A6D61AL /*_SummonCount1*/) - 1);
		doAction(1417895803L /*BATTLE_SUMMON25_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_25(blendTime)));
	}

	protected void Battle_Summon25_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x25E78611L /*Battle_Summon25_2*/);
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xEE6747DL /*_SummonCount2*/) - 1);
		doAction(326549743L /*BATTLE_SUMMON25_2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_25(blendTime)));
	}

	protected void Battle_Summon25_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7B4676CDL /*Battle_Summon25_3*/);
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0xB9D9DDBAL /*_SummonCount3*/) - 1);
		doAction(1523442671L /*BATTLE_SUMMON25_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_25(blendTime)));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, getVariable(0x8DB8E719L /*AI_BT_Attack1_CoolCount*/));
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
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
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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

	protected void Battle_Attack75_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x38CF5CE0L /*Battle_Attack75_1*/);
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, getVariable(0x8DB8E719L /*AI_BT_Attack1_CoolCount*/));
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack75_1_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_Attack75_1_Rotate(blendTime));
	}

	protected void Battle_Attack75_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8A546BCL /*Battle_Attack75_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_75(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_75(blendTime)));
	}

	protected void Battle_Attack75_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7D67300EL /*Battle_Attack75_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_75(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_75(blendTime)));
	}

	protected void Battle_Attack50_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7EE4FB74L /*Battle_Attack50_1*/);
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, getVariable(0x8DB8E719L /*AI_BT_Attack1_CoolCount*/));
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack50_1_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_Attack50_1_Rotate(blendTime));
	}

	protected void Battle_Attack50_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBF58B990L /*Battle_Attack50_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_50(blendTime)));
	}

	protected void Battle_Attack50_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDB069057L /*Battle_Attack50_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_50(blendTime)));
	}

	protected void Battle_Attack25_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x622C127FL /*Battle_Attack25_1*/);
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack25_1_NoRotate(0.3)))
				return;
		}
		changeState(state -> Battle_Attack25_1_Rotate(blendTime));
	}

	protected void Battle_Attack25_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB86D59F3L /*Battle_Attack25_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_25(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_25(blendTime)));
	}

	protected void Battle_Attack25_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4EB06A55L /*Battle_Attack25_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_25(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Damage_25(blendTime)));
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

	protected void Battle_RangeAttack75_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x21A1FA14L /*Battle_RangeAttack75_1*/);
							if (getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack75_1_NoRotate(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack75_1_Rotate(blendTime));
	}

	protected void Battle_RangeAttack75_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B0F678L /*Battle_RangeAttack75_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_75(blendTime)))
				return;
		}
		doAction(2790837227L /*BATTLE_RANGEATTACK75_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_75(blendTime));
		});
	}

	protected void Battle_RangeAttack75_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF73CF36CL /*Battle_RangeAttack75_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_75(blendTime)))
				return;
		}
		doAction(2790837227L /*BATTLE_RANGEATTACK75_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_75(blendTime));
		});
	}

	protected void Battle_RangeAttack50_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x47292664L /*Battle_RangeAttack50_1*/);
							if (getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack50_1_NoRotate(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack50_1_Rotate(blendTime));
	}

	protected void Battle_RangeAttack50_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A9DF8B4L /*Battle_RangeAttack50_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		doAction(52627774L /*BATTLE_RANGEATTACK50_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_50(blendTime));
		});
	}

	protected void Battle_RangeAttack50_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x47DC8580L /*Battle_RangeAttack50_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_50(blendTime)))
				return;
		}
		doAction(52627774L /*BATTLE_RANGEATTACK50_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_50(blendTime));
		});
	}

	protected void Battle_RangeAttack25_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8D095D34L /*Battle_RangeAttack25_1*/);
							if (getVariable(0xA6AE24BFL /*AI_BT_RangeAttack1_RotateOff*/) == 1) {
				if (changeState(state -> Battle_RangeAttack25_1_NoRotate(0.3)))
					return;
			}
			changeState(state -> Battle_RangeAttack25_1_Rotate(blendTime));
	}

	protected void Battle_RangeAttack25_1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE24644DCL /*Battle_RangeAttack25_1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_25(blendTime)))
				return;
		}
		doAction(1511226049L /*BATTLE_RANGEATTACK25_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_25(blendTime));
		});
	}

	protected void Battle_RangeAttack25_1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5ABF431AL /*Battle_RangeAttack25_1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Damage_25(blendTime)))
				return;
		}
		doAction(1511226049L /*BATTLE_RANGEATTACK25_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) != 999) {
				setVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/, getVariable(0x2D200EA6L /*AI_BT_RangeAttack1_ShotBullet*/) - 1);
			}
			changeState(state -> Wait_Damage_25(blendTime));
		});
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

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
			if (changeState(state -> Damage_Fear(0.3)))
				return;
		}
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
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
		setBehavior(EAIBehavior.idle);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Fear(blendTime), 400));
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
		setBehavior(EAIBehavior.idle);
		setState(0x71674A20L /*ForceReturn*/);
		doAction(3119505170L /*NOT_MOVE_RETURN*/, blendTime, onDoActionEnd -> scheduleState(state -> ForcePeaceful(blendTime), 1000));
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
		if (Rnd.get(100) <= getVariable(0x1FB5AA49L /*AI_HelpMe_Rate*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect)))) && getVariable(0xE6D1989CL /*AI_FA_DetectAction_Set*/) <= 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1 && target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= getVariable(0x8FFEA5EBL /*AI_SearchEnemy_MinDegree*/) && target != null && getAngleToTarget(target) <= getVariable(0xB8902B20L /*AI_SearchEnemy_MaxDegree*/) && target != null && isCreatureVisible(target, false)) {
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
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x3F487035L /*_HP*/) <= 100 && getVariable(0x3F487035L /*_HP*/) > 75) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x1B822FB3L /*Battle_Summon1*/ || getState() == 0x7BFB62FDL /*Battle_Summon2*/ || getState() == 0xC7540224L /*Battle_Summon3*/ || getState() == 0xB5FDC949L /*Battle_Attack1*/ || getState() == 0xC349CD1FL /*Battle_RangeAttack1*/) && getVariable(0x3F487035L /*_HP*/) <= 75 && getVariable(0x3F487035L /*_HP*/) > 50) {
			if (changeState(state -> Damage_75(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8933C40EL /*Wait_Damage_75*/ || getState() == 0x72132ECDL /*Damage_75*/ || getState() == 0x8D40411BL /*Battle_Summon75_1*/ || getState() == 0x7FDBBA39L /*Battle_Summon75_2*/ || getState() == 0x9D6F0D41L /*Battle_Summon75_3*/ || getState() == 0x38CF5CE0L /*Battle_Attack75_1*/ || getState() == 0x21A1FA14L /*Battle_RangeAttack75_1*/) && getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x3F487035L /*_HP*/) > 25) {
			if (changeState(state -> Damage_50(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x56840A98L /*Wait_Damage_50*/ || getState() == 0xC6E66A1BL /*Damage_50*/ || getState() == 0x14A02548L /*Battle_Summon50_1*/ || getState() == 0x49938CFBL /*Battle_Summon50_2*/ || getState() == 0xA292ADL /*Battle_Summon50_3*/ || getState() == 0x7EE4FB74L /*Battle_Attack50_1*/ || getState() == 0x47292664L /*Battle_RangeAttack50_1*/) && getVariable(0x3F487035L /*_HP*/) <= 25 && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> Damage_25(0.3)))
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
		if (getVariable(0x382E1DD9L /*AI_Counter_Stun*/) == 0 && getVariable(0x7EBC0F53L /*AI_Damage_Stun*/) == 1) {
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
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
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
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x1CF5EE87L /*_Damage_Stop*/) == 1) {
			if (changeState(state -> Damage_Die_Handler(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
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
}
