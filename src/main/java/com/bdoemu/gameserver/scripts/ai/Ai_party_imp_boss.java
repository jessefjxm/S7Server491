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
@IAIName("party_imp_boss")
public class Ai_party_imp_boss extends CreatureAI {
	public Ai_party_imp_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x781C4E46L /*_MyHP*/, 100);
		setVariable(0x7557FE40L /*_TownIn*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Party_Wait(blendTime));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && (getTargetCharacterKey(target) == 20007 || getTargetCharacterKey(target) == 20008 || getTargetCharacterKey(target) == 20009 || getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012)) {
			createParty(3, 3);
		}
		if (getVariable(0x7557FE40L /*_TownIn*/) == 0 && getPartyMembersCount()< 40) {
			if (changeState(state -> Party_Patrol_Walk(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x7557FE40L /*_TownIn*/) == 0 && getPartyMembersCount()>= 40) {
			if (changeState(state -> Party_ReadyForRush(blendTime)))
				return;
		}
		if (getVariable(0x7557FE40L /*_TownIn*/) == 1) {
			if (changeState(state -> Party_Rush_InTown(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Party_Patrol_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1572E19DL /*Party_Patrol_Walk*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && (getTargetCharacterKey(target) == 20007 || getTargetCharacterKey(target) == 20008 || getTargetCharacterKey(target) == 20009 || getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012)) {
			createParty(3, 3);
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		if (getPartyMembersCount()>= 40) {
			if (changeState(state -> Party_ReadyForRush(blendTime)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_patrol_9 imp_patrol_11 imp_patrol_13 imp_patrol_16 imp_patrol_18 imp_patrol_20 imp_patrol_22 imp_patrol_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Patrol_Walk(blendTime), 1000)));
	}

	protected void Party_ReadyForRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x76F50547L /*Party_ReadyForRush*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleReadyForRush(getActor(), null));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		if (getPartyMembersCount()<= 40 && target != null && getDistanceToTarget(target) < 1500 && target != null && (getTargetCharacterKey(target) == 20007 || getTargetCharacterKey(target) == 20008 || getTargetCharacterKey(target) == 20009 || getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012)) {
			createParty(3, 3);
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Rush(blendTime), 1000));
	}

	protected void Party_Rush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC7D7B6B9L /*Party_Rush*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_rushtown_22", ENaviType.ground, () -> {
			setVariable(0x7557FE40L /*_TownIn*/, 1);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_Rush_InTown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7E15DFC1L /*Party_Rush_InTown*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Party_Battle_Wait(blendTime)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_rushtown_27 imp_rushtown_31 imp_rushtown_36 imp_rushtown_41 imp_rushtown_47", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC252DE8L /*Party_Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x781C4E46L /*_MyHP*/, getHpRate());
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 180) {
			if (changeState(state -> Battle_Turn_Right_180(0.3)))
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
		if (target != null && getDistanceToTarget(target) > 350) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Swing(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Combination(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (getVariable(0x781C4E46L /*_MyHP*/) < 30 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_A(0.3)))
					return;
			}
		}
		if (getVariable(0x781C4E46L /*_MyHP*/) < 30 && target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Stamp_A(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Stamp(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Swing(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
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
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Party_Battle_Wait(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Stamp(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Combination(0.4)))
				return;
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
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
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Party_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 90) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Combination(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Swing(0.4)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(400 + Rnd.get(0, 100), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Party_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Stamp(0.4)))
					return;
			}
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 1000)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Party_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Stamp(0.4)))
					return;
			}
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD719C96L /*Dash*/);
		doAction(3487632696L /*DASH*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000)));
	}

	protected void Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92ACD724L /*Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2752325056L /*JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Stamp(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Swing(0.4)))
					return;
			}
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Stamp(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Swing(0.4)))
					return;
			}
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1915812192L /*BATTLE_TURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Stamp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17EDD054L /*Attack_Stamp*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(898649786L /*ATTACK_STAMP_READY*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Combination_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2438B72CL /*Attack_Combination_Ready*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3519135295L /*ATTACKSKILL_COMBINATION_READY*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Combination(blendTime)));
	}

	protected void Attack_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2905D73L /*Attack_Combination*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3437802803L /*ATTACKSKILL_COMBINATION*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Combination_2(blendTime)));
	}

	protected void Attack_Combination_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8A0BA481L /*Attack_Combination_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3026010468L /*ATTACKSKILL_COMBINATION_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Combination_3(blendTime)));
	}

	protected void Attack_Combination_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88FACD9L /*Attack_Combination_3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(374330200L /*ATTACKSKILL_COMBINATION_3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Combination_4(blendTime)));
	}

	protected void Attack_Combination_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF2B8FFC4L /*Attack_Combination_4*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4286803687L /*ATTACKSKILL_COMBINATION_4*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Combination_5(blendTime)));
	}

	protected void Attack_Combination_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3367B304L /*Attack_Combination_5*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3794117725L /*ATTACKSKILL_COMBINATION_5*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Swing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC0D3EEL /*Attack_Swing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(122753980L /*ATTACKSKILL_SWING_READY*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_A1(blendTime)));
	}

	protected void Attack_Swing_A1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x49072B13L /*Attack_Swing_A1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(219042788L /*ATTACKSKILL_SWING_A1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_A2(blendTime)));
	}

	protected void Attack_Swing_A2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x364F4E9BL /*Attack_Swing_A2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3528707147L /*ATTACKSKILL_SWING_A2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_B1(blendTime)));
	}

	protected void Attack_Swing_B1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA52DA63EL /*Attack_Swing_B1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2968694518L /*ATTACKSKILL_SWING_B1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_B2(blendTime)));
	}

	protected void Attack_Swing_B2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB882E493L /*Attack_Swing_B2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1350112465L /*ATTACKSKILL_SWING_B2*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6EF010AFL /*Attack_Swing_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(219042788L /*ATTACKSKILL_SWING_A1*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Attack_Stamp_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x40761FE1L /*Attack_Stamp_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1219749755L /*ATTACK_STAMP*/, blendTime, onDoActionEnd -> changeState(state -> Party_Battle_Wait(blendTime)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x677C6416L /*Party_Wait*/ || getState() == 0x1572E19DL /*Party_Patrol_Walk*/ || getState() == 0x76F50547L /*Party_ReadyForRush*/ || getState() == 0xC7D7B6B9L /*Party_Rush*/ || getState() == 0x7E15DFC1L /*Party_Rush_InTown*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Party_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Evade(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult GuardCrash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20007 || getTargetCharacterKey(target) == 20008 || getTargetCharacterKey(target) == 20009 || getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012)) {
			createParty(1, 1);
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReadyForRush(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleHelpMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_Run(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_Walk(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationAttackMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_Walk(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationAttackFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_Run(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
