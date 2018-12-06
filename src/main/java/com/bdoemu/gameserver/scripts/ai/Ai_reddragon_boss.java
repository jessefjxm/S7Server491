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
@IAIName("reddragon_boss")
public class Ai_reddragon_boss extends CreatureAI {
	public Ai_reddragon_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 1);
		setVariable(0xEE3C0202L /*_isWingDamage*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Fly(blendTime), 1000));
	}

	protected void Wait_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9B7BFA94L /*Wait_Fly*/);
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= getVariable(0xD1A2EF4FL /*_WayPointCount*/)) {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> MovePoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> MovePoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 5) {
			if (changeState(state -> MovePoint5(blendTime)))
				return;
		}
		changeState(state -> Wait_Fly(blendTime));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void MovePoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4B7E7FAL /*MovePoint4*/);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void MovePoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F460A97L /*MovePoint5*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void FailFindPath_FlyingLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDDBBEEACL /*FailFindPath_FlyingLogic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1) {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2) {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		}
							if (getVariable(0x5D97A0C3L /*_isFlyMode*/) == 1) {
				if (changeState(state -> Battle_Wait_Fly(0.3)))
					return;
			}
			changeState(state -> Wait_Fly(blendTime));
	}

	protected void FailFindPath_Flying(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE2E8BC7L /*FailFindPath_Flying*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		scheduleState(state -> Wait_Fly(blendTime), 1500);
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x5D97A0C3L /*_isFlyMode*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Logic_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAB61EA28L /*Logic_Fly*/);
		if (getVariable(0x5D97A0C3L /*_isFlyMode*/) == 1 && getVariable(0x4EAF441FL /*AI_FirstAttack*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_Fly(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return_Fly(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Wait_Fly(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
							if (getVariable(0x5D97A0C3L /*_isFlyMode*/) == 1) {
				if (changeState(state -> Battle_Wait_Fly(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		setVariable(0xEE3C0202L /*_isWingDamage*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (getSelfCombinePoint() >= 13) {
			if (changeState(state -> Damage_Stun(0.3)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Lost_Target_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1241502L /*Lost_Target_Fly*/);
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic_Fly(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		setVariable(0xEE3C0202L /*_isWingDamage*/, 0);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x163A2D9CL /*Move_Return_Fly*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		setVariable(0xEE3C0202L /*_isWingDamage*/, 0);
		doAction(3440250122L /*FLY_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Fly_Down(blendTime), 1000)));
	}

	protected void Fly_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6FF0D53FL /*Fly_Down*/);
		doAction(211507737L /*FLYDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0xE04DD125L /*_FlyCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 0 && getVariable(0xEE3C0202L /*_isWingDamage*/) == 0) {
			if (changeState(state -> Fly_Up(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Battle_Walk_Back(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1400) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Battle_TurnL(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Battle_TurnR(0.3)))
				return;
		}
		if (getVariable(0x5450C921L /*_BreathAtt2CountDown*/) >= 30 && target != null && getDistanceToTarget(target) <= 2500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Battle_Breath2_Str(0.3)))
					return;
			}
		}
		if (getVariable(0xAE8980A2L /*_BreathAtt1CountDown*/) >= 15 && target != null && getDistanceToTarget(target) <= 2500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Battle_Breath1_Str(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 900 && getDistanceToTarget(target, false) <= 1400)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackR(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -100 && target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 1100)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_TurnL(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 170 && target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 1100)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_TurnL(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -120 && target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 1050)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_TurnR(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 175 && target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 1050)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_TurnR(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45 && target != null && (getDistanceToTarget(target, false) >= 1600 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_F(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 1700)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_B(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 135 && target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 1700)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_B(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 900 && getDistanceToTarget(target, false) <= 1400)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackR(0.3)))
					return;
			}
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1300) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45 && target != null && (getDistanceToTarget(target, false) >= 1600 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_F(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 1700)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_B(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 135 && target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 1700)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Jump_B(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 900 && getDistanceToTarget(target, false) <= 1400)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackR(0.3)))
					return;
			}
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 900 && getDistanceToTarget(target, false) <= 1400)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 900) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_AttackR(0.3)))
					return;
			}
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
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
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Fly_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x14570A65L /*Fly_Up*/);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 1);
		doAction(2635306089L /*FLYUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Fly(blendTime), 1000));
	}

	protected void Battle_Wait_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD63B64BL /*Battle_Wait_Fly*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic_Fly(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return_Fly(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Fly_Chaser(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Fly_TurnL(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Fly_TurnR(0.3)))
				return;
		}
		if (getVariable(0x5450C921L /*_BreathAtt2CountDown*/) >= 30 && target != null && getDistanceToTarget(target) > 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Battle_Fly_Breath(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 0) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Battle_Fly_RangeAttack(0.4)))
					return;
			}
		}
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Fly(blendTime), 100));
	}

	protected void Fly_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x10A0ACA6L /*Fly_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Fly_TurnL(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Fly_TurnR(0.3)))
				return;
		}
		if (getVariable(0x5450C921L /*_BreathAtt2CountDown*/) >= 30 && target != null && getDistanceToTarget(target) > 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Battle_Fly_Breath(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 0) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Battle_Fly_RangeAttack(0.4)))
					return;
			}
		}
		doAction(3440250122L /*FLY_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return_Fly(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Fly_Chaser(blendTime), 100)));
	}

	protected void Battle_TurnL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7DC9BE50L /*Battle_TurnL*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3486373415L /*BATTLE_TURNL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_TurnR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6D431B7L /*Battle_TurnR*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1248855864L /*BATTLE_TURNR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Breath2_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88DC3C75L /*Battle_Breath2_Str*/);
		doAction(2242812364L /*BATTLE_BREATH2_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Breath2(blendTime), 1000));
	}

	protected void Battle_Breath2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFB55C97FL /*Battle_Breath2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		doAction(791389243L /*BATTLE_BREATH2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Breath2_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1B99C70FL /*Battle_Breath2_Cancel*/);
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		doAction(1994807051L /*BATTLE_BREATH_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Breath1_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x470BCCDL /*Battle_Breath1_Str*/);
		doAction(811060020L /*BATTLE_BREATH_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Breath1(blendTime), 1000));
	}

	protected void Battle_Breath1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x20353EE8L /*Battle_Breath1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		doAction(1010536687L /*BATTLE_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Breath1_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4A992C58L /*Battle_Breath1_Cancel*/);
		setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, 0);
		doAction(1994807051L /*BATTLE_BREATH_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Jump_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x99C1BDCBL /*Battle_Jump_F*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3872114358L /*BATTLE_JUMP_F*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Jump_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD3A387A5L /*Battle_Jump_B*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1664194426L /*BATTLE_JUMP_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_AttackL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x87085FE1L /*Battle_AttackL*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(553295030L /*BATTLE_ATTACK_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_AttackR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x30F89B0L /*Battle_AttackR*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1450156184L /*BATTLE_ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_TurnL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1362FB7AL /*Battle_Attack_TurnL*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(8553849L /*BATTLE_ATTACK_TURNL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_TurnR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A38CE44L /*Battle_Attack_TurnR*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(545568805L /*BATTLE_ATTACK_TURNR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Fly_TurnL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B3429D3L /*Fly_TurnL*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		doAction(4222501436L /*FLYTURN_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Fly_TurnR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7D067A47L /*Fly_TurnR*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		doAction(303893802L /*FLYTURN_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Battle_Fly_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8E45A0AL /*Battle_Fly_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		setVariable(0x5450C921L /*_BreathAtt2CountDown*/, 0);
		doAction(2504041737L /*BATTLE_FLY_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Battle_Fly_RangeAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x546FE4E5L /*Battle_Fly_RangeAttack*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		doAction(1516537527L /*BATTLE_FLY_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB19503F8L /*Damage_Fall*/);
		setVariable(0xEE3C0202L /*_isWingDamage*/, 1);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		doAction(3037383091L /*FALL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	protected void Combine_Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x49366CA2L /*Combine_Fall*/);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		doAction(3037383091L /*FALL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xE04DD125L /*_FlyCountDown*/) < 51) {
			setVariable(0xE04DD125L /*_FlyCountDown*/, getVariable(0xE04DD125L /*_FlyCountDown*/) + 1);
		}
		if (getVariable(0xAE8980A2L /*_BreathAtt1CountDown*/) < 16) {
			setVariable(0xAE8980A2L /*_BreathAtt1CountDown*/, getVariable(0xAE8980A2L /*_BreathAtt1CountDown*/) + 1);
		}
		if (getVariable(0x5450C921L /*_BreathAtt2CountDown*/) < 31) {
			setVariable(0x5450C921L /*_BreathAtt2CountDown*/, getVariable(0x5450C921L /*_BreathAtt2CountDown*/) + 1);
		}
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(1) <= 0 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 1 && getVariable(0xEE3C0202L /*_isWingDamage*/) == 0) {
			if (changeState(state -> Damage_Fall(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getSelfCombinePoint() == 13 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 0 && getState() == 0x88DC3C75L /*Battle_Breath2_Str*/) {
			if (changeState(state -> Battle_Breath2_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 13 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 0 && getState() == 0x470BCCDL /*Battle_Breath1_Str*/) {
			if (changeState(state -> Battle_Breath1_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 13 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x71F28994L /*Battle_Wait*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 13 && getVariable(0x5D97A0C3L /*_isFlyMode*/) == 1 && (getState() == 0xD63B64BL /*Battle_Wait_Fly*/ || getState() == 0x10A0ACA6L /*Fly_Chaser*/ || getState() == 0x1B3429D3L /*Fly_TurnL*/ || getState() == 0x7D067A47L /*Fly_TurnR*/ || getState() == 0xC8E45A0AL /*Battle_Fly_Breath*/ || getState() == 0x546FE4E5L /*Battle_Fly_RangeAttack*/)) {
			if (changeState(state -> Combine_Fall(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
