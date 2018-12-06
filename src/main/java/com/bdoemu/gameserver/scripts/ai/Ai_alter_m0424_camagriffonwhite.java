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
@IAIName("alter_m0424_camagriffonwhite")
public class Ai_alter_m0424_camagriffonwhite extends CreatureAI {
	public Ai_alter_m0424_camagriffonwhite(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 1500)) {
			if (changeState(state -> Chaser_Run(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rotate_Logic(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500 + Rnd.get(-500,500)));
	}

	protected void Rotate_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x65942A11L /*Rotate_Logic*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 27135)) {
				if (changeState(state -> Wait_Rotate(blendTime)))
					return;
			}
		}
		changeState(state -> Rotate_Logic(blendTime));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Search_Enemy(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1200)) {
			if (changeState(state -> Search_Enemy(0.2)))
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
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1200 && getTargetHp(object) > 0)) {
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
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 10000 && getTargetCharacterKey(object) == 27135)) {
				if (changeState(state -> Return_Shrine(blendTime)))
					return;
			}
		}
		clearAggro(true);
		changeState(state -> Move_Return(blendTime));
	}

	protected void Return_Shrine(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2E0CB188L /*Return_Shrine*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Attack_TwoHand(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Return_Shrine(blendTime), 1000)));
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Run(blendTime)));
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
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_TwoHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_OneHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Head(0.2)))
					return;
			}
			changeState(state -> Attack_Head(blendTime));
		});
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Run(blendTime)));
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
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300 && getVariable(0x3F487035L /*_HP*/) > 20) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_TwoHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300 && getVariable(0x3F487035L /*_HP*/) > 20) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_OneHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Head(0.2)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3DED39BEL /*Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2728809040L /*TURN_RIGHT_ATTACK_180*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Run(blendTime)));
	}

	protected void Turn_Right_Attack_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B894F82L /*Turn_Right_Attack_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2728809040L /*TURN_RIGHT_ATTACK_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 550) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Jump_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA600AC5BL /*Jump_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3508935626L /*JUMP_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(33)) {
					if (changeState(state -> Attack_TwoHand(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Head(0.2)))
					return;
			}
			changeState(state -> Attack_OneHand(blendTime));
		});
	}

	protected void Jump_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3039B3E3L /*Jump_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(801180298L /*JUMP_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300) {
				if (changeState(state -> Attack_OneHand(0.2)))
					return;
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if (changeState(state -> Attack_Head(0.2)))
					return;
			}
			changeState(state -> Attack_TwoHand(blendTime));
		});
	}

	protected void Jump_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9EB99287L /*Jump_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2159179354L /*JUMP_BACK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Breath(blendTime)));
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
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
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

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000 && target != null && getDistanceToTarget(target) > 800) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Position_Str(0.2)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 700 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Jump_Back(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500 && target != null && getAngleToTarget(target) >= -25 && target != null && getAngleToTarget(target) <= 25) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Jump_Left(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500 && target != null && getAngleToTarget(target) >= -25 && target != null && getAngleToTarget(target) <= 25) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Jump_Right(0.2)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Breath(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 250 && getVariable(0x3F487035L /*_HP*/) > 20) {
			if(Rnd.getChance(33)) {
				if (changeState(state -> Attack_TwoHand(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 250 && getVariable(0x3F487035L /*_HP*/) > 20) {
			if (changeState(state -> Attack_OneHand(0.2)))
				return;
		}
		changeState(state -> Move_Chaser(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, 800, 800, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Right_Attack_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Right_Attack_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -75 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Left_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 75 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Turn_Right_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -75) {
			if (changeState(state -> Turn_Left(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 75) {
			if (changeState(state -> Turn_Right(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179) {
			if (changeState(state -> Turn_Right_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 180) {
			if (changeState(state -> Turn_Right_180(0.2)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 700) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Chaser_Run(0.4)))
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 6) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000 && target != null && getDistanceToTarget(target) > 800) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Position_Str(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
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
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 6) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 2000 && target != null && getDistanceToTarget(target) > 800) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Position_Str(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().NextMove(getActor(), null));
		changeState(state -> Die(blendTime));
	}

	protected void Attack_OneHand(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8739DEAAL /*Attack_OneHand*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4123063841L /*ATTACK_ONEHAND*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_TwoHand(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6735F109L /*Attack_TwoHand*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(303994299L /*ATTACK_TWOHAND*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Position_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7A63A5BBL /*Attack_Position_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1086656395L /*ATTACK_POSITION_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Position_Ing(blendTime), 500));
	}

	protected void Attack_Position_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x51F9BF8FL /*Attack_Position_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack_Position_End(0.2)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Attack_Position_End(0.2)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		doAction(1015773015L /*ATTACK_POSITION_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Attack_Position_Ing(blendTime), 100)));
	}

	protected void Attack_Position_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E9B83EEL /*Attack_Position_End*/);
		doAction(1689777296L /*ATTACK_POSITION_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D9470D7L /*Attack_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3779359920L /*ATTACK_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Head(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1239D668L /*Attack_Head*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2670648904L /*ATTACK_HEAD*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x460B4E3AL /*Attack_Move*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2547671729L /*ATTACK_MOVE*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Battle_Wait(0.2)))
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
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
