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
@IAIName("m0431_kam2bronzegiant")
public class Ai_m0431_kam2bronzegiant extends CreatureAI {
	public Ai_m0431_kam2bronzegiant(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x2064B391L /*_isDoubleSwordMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC141FFFAL /*Start_Action2*/);
		doAction(463565147L /*START_ACTION2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 6000));
	}

	protected void Summon_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xECE60874L /*Summon_Logic*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		changeState(state -> Start_Action2(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Logic2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD1DCE2AFL /*Logic2*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1200 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait2(0.3)))
				return;
		}
		changeState(state -> Move_Return2(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Stance_Change3(blendTime), 1000)));
	}

	protected void Move_Return2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xC1F2362FL /*Move_Return2*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(3277917394L /*MOVE_RETURN2*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath2(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_End(blendTime), 1000)));
	}

	protected void Turn_Spawn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE67FFF90L /*Turn_Spawn*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x2064B391L /*_isDoubleSwordMode*/, 0);
		clearAggro(true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 1200 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1200)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void FailFindPath_Logic2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6BED310CL /*FailFindPath_Logic2*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void FailFindPath2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1ED2E51L /*FailFindPath2*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return2(blendTime), 1500));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Stance_Change1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x90927BB0L /*Stance_Change1*/);
		setVariable(0x2064B391L /*_isDoubleSwordMode*/, 1);
		doAction(1286692637L /*DETECT_ENEMY2*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().StandUpFriend(getActor(), null));
			scheduleState(state -> Battle_Wait2(blendTime), 1000);
		});
	}

	protected void Stance_Change2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA2CF709EL /*Stance_Change2*/);
		setVariable(0x2064B391L /*_isDoubleSwordMode*/, 2);
		doAction(765776908L /*WEAPON_IN2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8C9101E8L /*Battle_End*/);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Spawn(blendTime), 1000));
	}

	protected void Stance_Change3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x615C625AL /*Stance_Change3*/);
		doAction(1891792052L /*WEAPON_IN*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Spawn(blendTime), 1000));
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

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Left2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD32FE456L /*Turn_Left2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(1749754050L /*TURN_LEFT2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC9EA28D5L /*Turn_Right2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(3193476201L /*TURN_RIGHT2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEEC8B2A6L /*Turn_180_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(3521642395L /*TURN_180_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x2064B391L /*_isDoubleSwordMode*/) == 0) {
			if (changeState(state -> Stance_Change1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 500) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Spear_Str(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= 500 && target != null && getDistanceToTarget(target) <= 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Spear_Thorw(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 1 && target != null && getAngleToTarget(target) < 90 && target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) < 1 && target != null && getAngleToTarget(target) > -90 && target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack_Normal2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 100) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC2226D5L /*Battle_Wait2*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 20 && getVariable(0x2064B391L /*_isDoubleSwordMode*/) == 1) {
			if (changeState(state -> Stance_Change2(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800 && target != null && getDistanceToTarget(target) >= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack2_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack2_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Battle_Run2(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 100) {
			if (changeState(state -> Battle_Walk2(0.3)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 1000));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Run2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xFA01DED4L /*Battle_Run2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack2_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait2(0.4)))
				return;
		}
		doAction(1724771102L /*BATTLE_RUN2*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return2(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic2(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Run2(blendTime), 100)));
	}

	protected void Battle_Walk2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x85B5DB1L /*Battle_Walk2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return2(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800 && target != null && getDistanceToTarget(target) >= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Dash(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Attack2_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Battle_Run2(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Battle_Wait2(0.4)))
				return;
		}
		doAction(2321231283L /*BATTLE_WALK2*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return2(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic2(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk2(blendTime), 100)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2957630933L /*ATTACK_NORMAL3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5CA8868L /*Attack_Normal4*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1678640124L /*ATTACK_NORMAL4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Spear_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1C611169L /*Attack_Spear_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1919151718L /*ATTACK_SPEAR_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Spear_Ing(blendTime)));
	}

	protected void Attack_Spear_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7DF91F2EL /*Attack_Spear_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1951015218L /*ATTACK_SPEAR_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Spear_End(blendTime)));
	}

	protected void Attack_Spear_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCE4A63D2L /*Attack_Spear_End*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4018989192L /*ATTACK_SPEAR_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Spear_Thorw(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5D0DE482L /*Attack_Spear_Thorw*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2937341833L /*ATTACK_SPEAR_THROW*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E8D0930L /*Attack_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3567810684L /*ATTACK_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9B11BF9L /*Attack_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1597155294L /*ATTACK_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack2_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7B9B7AD9L /*Attack2_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(1704870163L /*ATTACK2_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Attack2_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC89F956FL /*Attack2_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(1955664401L /*ATTACK2_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD719C96L /*Dash*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(3424435352L /*ATTACK2_DASH_STR*/, blendTime, onDoActionEnd -> changeState(state -> Dash2(blendTime)));
	}

	protected void Dash2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB7DBEB2BL /*Dash2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(3036020226L /*ATTACK2_DASH_ING*/, blendTime, onDoActionEnd -> changeState(state -> Dash3(blendTime)));
	}

	protected void Dash3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60C44FF4L /*Dash3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(2881287293L /*ATTACK2_DASH_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Attack_Turn_Left2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD29A5CA1L /*Attack_Turn_Left2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(2838258461L /*ATTACK_TURN_LEFT2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Attack_Turn_Right2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3CD0B59EL /*Attack_Turn_Right2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic2(blendTime)))
				return;
		}
		doAction(1444304978L /*ATTACK_TURN_RIGHT2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Damage_Die1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x5689F1D3L /*Damage_Die1*/);
		doAction(3344111505L /*DIE1*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Damage_Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x3690D301L /*Damage_Die2*/);
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x2064B391L /*_isDoubleSwordMode*/) == 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Damage_Die(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getVariable(0x2064B391L /*_isDoubleSwordMode*/) == 0) {
			if (changeState(state -> Damage_Die1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x2064B391L /*_isDoubleSwordMode*/) == 1) {
			if (changeState(state -> Damage_Die2(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult SummonForFight(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Summon_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
