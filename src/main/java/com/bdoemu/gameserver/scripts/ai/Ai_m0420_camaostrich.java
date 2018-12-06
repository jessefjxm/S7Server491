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
@IAIName("m0420_camaostrich")
public class Ai_m0420_camaostrich extends CreatureAI {
	public Ai_m0420_camaostrich(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0x870CD143L /*_IsPartyMember*/, 0);
		setVariable(0x64490D98L /*AI_RandomMove*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x1408C89EL /*_IsAlert*/, 0);
		if (isPartyMember()) {
			setVariable(0x870CD143L /*_IsPartyMember*/, 1);
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 1500) {
			if (changeState(state -> Move_ReturnToParent(0.3)))
				return;
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 1) {
			if(getCallCount() == 5) {
				if (changeState(state -> Move_Random(0.4)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x1408C89EL /*_IsAlert*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12 && getVariable(0x870CD143L /*_IsPartyMember*/) == getVariable(0xA3F567B9L /*0`*/)) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_ReturnToParent_Clear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDC38FC5L /*Move_ReturnToParent_Clear*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			clearAggro(true);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Move_ReturnToParent_ClearNoHandler(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8255C122L /*Move_ReturnToParent_ClearNoHandler*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			clearAggro(true);
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Move_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x1471881CL /*Move_ReturnToParent*/);
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Move_Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xCBA2B4E2L /*Move_Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 600 && target != null && isCreatureVisible(target, false)) {
			if (changeState(state -> Battle_Attack4(0.3)))
				return;
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Attack4(blendTime), 3000)));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && target == null) {
			if (changeState(state -> Move_ReturnToParent_Clear(blendTime)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && target != null && isTargetInHouseAny(target)) {
			if (changeState(state -> Move_ReturnToParent_Clear(blendTime)))
				return;
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && target != null && getDistanceToTarget(target) > 2500) {
			if (changeState(state -> Move_ReturnToParent_Clear(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 3 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> FailFindPath_ReturnToParent(0.3)))
				return;
		}
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> Waiting(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void FailFindPath_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6504D93DL /*FailFindPath_ReturnToParent*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_ReturnToParent(blendTime), 2000));
	}

	protected void TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A6D56A5L /*TargetChange_Logic*/);
		if(Rnd.getChance(30)) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
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
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000) {
			if (changeState(state -> Move_ReturnToParent_Clear(0.3)))
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
		if (target != null && (getAngleToTarget(target) >= -40 && getAngleToTarget(target) <= 40) && target != null && target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) <= 500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Escape(0.1)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -45) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 45) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= 155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= -155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 650) {
			if(Rnd.getChance(7)) {
				if (changeState(state -> Battle_Attack4(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 50 && target != null && getDistanceToTarget(target) <= 800) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Chaser_Run(0.2)))
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
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1 && getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 5000) {
			if(getCallCount() == 40) {
				if (changeState(state -> Move_ReturnToParent_Clear(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -45) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 45) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= 155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= -155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 650) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Attack4(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Chaser_Run(0.2)))
				return;
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

	protected void Waiting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x138AF201L /*Waiting*/);
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 100) {
			if (changeState(state -> Move_ParentPath(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 2500 && getTargetHp(object) > 0 && getTendency(object) < 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_ParentPath(blendTime), 1000));
	}

	protected void Move_ParentPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x838FBB02L /*Move_ParentPath*/);
		if (getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) <= 100) {
			if (changeState(state -> Waiting(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 2500 && getTargetHp(object) > 0 && getTendency(object) < 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_ParentPath(blendTime), 500)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -45) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 45) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= 155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= -155) {
			if (changeState(state -> Turn_180(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 650) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Attack4(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 230 && getVariable(0x3F487035L /*_HP*/) > 20) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 2000)));
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
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Battle_Attack4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF05721A5L /*Battle_Attack4*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 21527 && getDistanceToTarget(object) < 2800).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 4000));
	}

	protected void Search_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1E247A0DL /*Search_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 4000)) {
			if (changeState(state -> Search_Enemy(0.3)))
				return;
		}
		changeState(state -> Search_Logic(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Battle_Attack4(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x71F28994L /*Battle_Wait*/ && getState() != 0x39B3FBC2L /*Move_Chaser*/ && getState() != 0xB5FDC949L /*Battle_Attack1*/ && getState() != 0xD72BCC90L /*Battle_Attack2*/ && getState() != 0x94302B26L /*Battle_Attack3*/ && getState() != 0xF05721A5L /*Battle_Attack4*/ && getState() != 0x1471881CL /*Move_ReturnToParent*/) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMeOwnerPath(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Waiting(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 1200) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Attack4(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBrotherCall(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xDC38FC5L /*Move_ReturnToParent_Clear*/) {
			if (changeState(state -> Move_ReturnToParent_ClearNoHandler(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if(Rnd.getChance(25)) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if(Rnd.getChance(25)) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _AllChildDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.5)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Searching(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Logic(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
