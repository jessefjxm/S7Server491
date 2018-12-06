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
@IAIName("kamawood_boss")
public class Ai_kamawood_boss extends CreatureAI {
	public Ai_kamawood_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3B79F487L /*_AppearCount*/, 3);
		setVariable(0x636C9126L /*_BonusCount*/, 1);
		setVariable(0x73E18951L /*_SummonDummyCount*/, 1);
		setVariable(0xBF33D5C1L /*_BrokenShieldCount*/, 0);
		setVariable(0x3549AFC5L /*_CombinationAttackCount*/, 3);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(807741651L /*Avoid_Wait*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2500)) {
			if (changeState(state -> Search_Enemy(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Avoid_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE712FF1EL /*Avoid_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1102012989L /*AVOID_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait(blendTime), 1000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 600, 1000, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Return_A(blendTime), 3000)));
	}

	protected void Move_Return_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x28780112L /*Move_Return_A*/);
		doAction(3467964157L /*TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_B(blendTime), 1000));
	}

	protected void Move_Return_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1CE83D51L /*Move_Return_B*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1357246757L /*TELEPORT_A*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Return_C(blendTime), 1000)));
	}

	protected void Move_Return_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x406B839CL /*Move_Return_C*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 3000, 0, 0);
		doAction(3090905698L /*TELEPORT_B*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Return(getActor(), null));
			changeState(state -> Wait(blendTime));
		});
	}

	protected void Turn_Left_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBEA2021EL /*Turn_Left_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2306117058L /*TURN_LEFT_ATTACK*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 400) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_OneHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 400) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_Leg(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Right_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC6870DEL /*Turn_Right_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(350541807L /*TURN_RIGHT_ATTACK*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 400) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_OneHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 400) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_Leg(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Right_Attack_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B894F82L /*Turn_Right_Attack_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1417729292L /*TURN_RIGHT_180_ATTACK*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000) {
				if (changeState(state -> Attack_Logic(0.2)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88C3A72EL /*Teleport*/);
		doAction(3467964157L /*TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_A(blendTime), 2000));
	}

	protected void Teleport_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xACCC88E6L /*Teleport_A*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1357246757L /*TELEPORT_A*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Teleport_B(blendTime), 2000)));
	}

	protected void Teleport_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFB4D907FL /*Teleport_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3090905698L /*TELEPORT_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Teleport_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAF405B4EL /*Teleport_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Teleport(getActor(), null));
		doAction(2086689193L /*TELEPORT_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Teleport_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBF80036EL /*Teleport_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4209887786L /*TELEPORT_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Teleport_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x767EEE1FL /*Teleport_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Teleport(getActor(), null));
		doAction(224527414L /*TELEPORT_BACK*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.2)))
					return;
			}
		}
		if(getCallCount() == 360) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		changeState(state -> TargetLost(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPath(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A6D56A5L /*TargetChange_Logic*/);
		if(Rnd.getChance(30)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (target != null && getTargetHp(target) > 0 && getVariable(0x3B79F487L /*_AppearCount*/) > 1) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Order(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && getVariable(0x73E18951L /*_SummonDummyCount*/) == 1) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Summon_Dummy(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Explosion(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Fragment(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1500) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Leg(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Throw(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Attack_OneHand(0.2)))
					return;
			}
		}
		changeState(state -> Move_Chaser(blendTime));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		if (target != null && getDistanceToTarget(target) >= 800) {
			if (changeState(state -> Teleport(0.2)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) >= 800)) {
				if (changeState(state -> Teleport(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Teleport_Back(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Turn_Right_Attack_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Turn_Right_Attack_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -75) {
			if (changeState(state -> Turn_Left_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 75) {
			if (changeState(state -> Turn_Right_Attack(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3B79F487L /*_AppearCount*/) == 2 && getVariable(0xBF33D5C1L /*_BrokenShieldCount*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 70) {
			if (changeState(state -> Disappear(0.1)))
				return;
		}
		if (getVariable(0x3B79F487L /*_AppearCount*/) == 1 && getVariable(0xBF33D5C1L /*_BrokenShieldCount*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 40) {
			if (changeState(state -> Disappear(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Teleport_Logic(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 40 && target != null && getAngleToTarget(target) <= -40) {
			if (changeState(state -> Turn_Logic(0.2)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 7000) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Teleport_Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Bonus(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93A23B84L /*Bonus*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Disappear_Dummy(getActor(), null));
		setVariable(0x636C9126L /*_BonusCount*/, 0);
		doAction(268949649L /*BONUS*/, blendTime, onDoActionEnd -> changeState(state -> Die_Logic(blendTime)));
	}

	protected void Before_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B32239L /*Before_Die*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Disappear_Dummy(getActor(), null));
		doAction(1174134199L /*BEFORE_DIE*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23751).forEach(consumer -> consumer.getAi().Die(getActor(), null));
			scheduleState(state -> Die_Logic(blendTime), 3000);
		});
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23751).forEach(consumer -> consumer.getAi().Die(getActor(), null));
		changeState(state -> Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(1290660348L /*TELEPORT_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 2000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Appear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA355D539L /*Appear*/);
		setVariable(0x3B79F487L /*_AppearCount*/, getVariable(0x3B79F487L /*_AppearCount*/) - 1);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Appear_Dummy(getActor(), null));
		doAction(519999600L /*APPEAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void BrokenShield_Appear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x843EED14L /*BrokenShield_Appear*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xBF33D5C1L /*_BrokenShieldCount*/, 1);
		doAction(519999600L /*APPEAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Disappear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B12AE75L /*Disappear*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Disappear_Dummy(getActor(), null));
		doAction(1903499173L /*DISAPPEAR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23751).forEach(consumer -> consumer.getAi().Revival(getActor(), null));
			changeState(state -> Avoid_Wait(blendTime));
		});
	}

	protected void Attack_OneHand(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8739DEAAL /*Attack_OneHand*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4123063841L /*ATTACK_ONEHAND*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Leg(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x36B6682CL /*Attack_Leg*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(585721422L /*ATTACK_LEG*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3EE4B73AL /*Attack_Explosion*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2587605505L /*ATTACK_EXPLOSION*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(522473655L /*ATTACK_JUMP_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Throw(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7DBBD7A9L /*Attack_Throw*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) > 1000 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Attack_Throw_Ing(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Throw_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6C6856C2L /*Attack_Throw_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2952405236L /*ATTACK_THROW*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Fragment(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x329F6D67L /*Attack_Fragment*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3797302244L /*ATTACK_FRAGMENT*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Opin_Kill(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2A1AC194L /*Opin_Kill*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x636C9126L /*_BonusCount*/, 0);
		getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23751).forEach(consumer -> consumer.getAi().Die(getActor(), null));
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Parent_Die(getActor(), null));
		doAction(2342462203L /*OPIN_KILL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Order(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9926580AL /*Order*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23751).forEach(consumer -> consumer.getAi().Order(getActor(), null));
		setVariable(0x3549AFC5L /*_CombinationAttackCount*/, getVariable(0x3549AFC5L /*_CombinationAttackCount*/) - 1);
		doAction(4157206235L /*ORDER*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 1000));
	}

	protected void Summon_Dummy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD5742CE3L /*Summon_Dummy*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x73E18951L /*_SummonDummyCount*/, getVariable(0x73E18951L /*_SummonDummyCount*/) - 1);
		doAction(2754271230L /*SUMMON_DUMMY*/, blendTime, onDoActionEnd -> changeState(state -> Teleport_Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 5 && getVariable(0x636C9126L /*_BonusCount*/) == 1) {
			if (changeState(state -> Opin_Kill(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 1200) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Broken_Leg(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Appear(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Delete_Die(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Delete_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Broken_Shield(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> BrokenShield_Appear(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
