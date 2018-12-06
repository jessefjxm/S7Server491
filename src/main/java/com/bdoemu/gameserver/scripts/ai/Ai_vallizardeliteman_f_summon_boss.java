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
@IAIName("vallizardeliteman_f_summon_boss")
public class Ai_vallizardeliteman_f_summon_boss extends CreatureAI {
	public Ai_vallizardeliteman_f_summon_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x4B258DBFL /*AI_BT_Summon1_Count*/));
		setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xA664E0FAL /*AI_BT_Summon2_Count*/));
		setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0x68A8E57L /*AI_BT_Summon3_Count*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if(Rnd.getChance(50)) {
				if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 1500)) {
					if (changeState(state -> Battle_Wait(0.3)))
						return;
				}
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 1500)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Search_Enemy(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
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
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(60)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_PositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_PositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
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
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_PositionAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_RangeAttack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Chase_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Battle_Attack3(0.3)))
				return;
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
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 2000)));
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
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

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_PositionAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3633FB8FL /*Battle_PositionAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1662588112L /*BATTLE_POSITIONATTACK1*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x395B66CCL /*Battle_RangeAttack3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Chase_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B1E3FE7L /*Chase_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4010467008L /*CHASE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Summon1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B822FB3L /*Battle_Summon1*/);
		doAction(3697722837L /*BATTLE_SUMMON1*/, blendTime, onDoActionEnd -> {
			setVariable(0x82A6D61AL /*_SummonCount1*/, getVariable(0x82A6D61AL /*_SummonCount1*/) - 1);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Summon2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7BFB62FDL /*Battle_Summon2*/);
		doAction(3814969495L /*BATTLE_SUMMON2*/, blendTime, onDoActionEnd -> {
			setVariable(0xEE6747DL /*_SummonCount2*/, getVariable(0xEE6747DL /*_SummonCount2*/) - 1);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Summon3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7540224L /*Battle_Summon3*/);
		doAction(2882522470L /*BATTLE_SUMMON3*/, blendTime, onDoActionEnd -> {
			setVariable(0xB9D9DDBAL /*_SummonCount3*/, getVariable(0xB9D9DDBAL /*_SummonCount3*/) - 1);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
