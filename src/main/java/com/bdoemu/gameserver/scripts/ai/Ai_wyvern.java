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
@IAIName("wyvern")
public class Ai_wyvern extends CreatureAI {
	public Ai_wyvern(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE4FBB1E7L /*_FindTarget*/, getVariable(0xA130948EL /*AI_FindTarget*/));
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		setVariable(0x80F9A984L /*_FallCountDown*/, 0);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariable(0x19EB970L /*_WayPointLoopCount*/, 3);
		setVariable(0xBE464C69L /*_isFA*/, getVariable(0x4EAF441FL /*AI_FirstAttack*/));
		setVariable(0x8FD772BBL /*_WayPointDownCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { 4, 11537 });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { 4, 11540 });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { 4, 11534 });
		setVariableArray(0xCCEB4815L /*_WayDownPointValue1*/, new Integer[] { 4, 12365 });
		setVariableArray(0xAD496DCAL /*_WayDownPointValue2*/, new Integer[] { 4, 12366 });
		setVariableArray(0xD2AEF2B4L /*_WayDownPointValue3*/, new Integer[] { 4, 12367 });
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic(blendTime), 1000));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1 && getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> MovePoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2 && getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3 && getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		changeState(state -> Wait_Fly(blendTime));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(1566678649L /*MOVE_FLY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MoveDownPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3F9E3560L /*MoveDownPoint1*/);
		setVariable(0x8FD772BBL /*_WayPointDownCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(196725669L /*FLYDOWN_A*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xCCEB4815L /*_WayDownPointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fly_Down_End(blendTime), 1000)));
	}

	protected void MoveDownPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2D2CCADEL /*MoveDownPoint2*/);
		setVariable(0x8FD772BBL /*_WayPointDownCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(196725669L /*FLYDOWN_A*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xAD496DCAL /*_WayDownPointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fly_Down_End(blendTime), 1000)));
	}

	protected void MoveDownPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9BB72D3L /*MoveDownPoint3*/);
		setVariable(0x8FD772BBL /*_WayPointDownCount*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(196725669L /*FLYDOWN_A*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xD2AEF2B4L /*_WayDownPointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fly_Down_End(blendTime), 1000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(0.1)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Fly_Up(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Wait_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9B7BFA94L /*Wait_Fly*/);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 1);
		if (getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
				if (changeState(state -> Battle_Wait_Fly(0.2)))
					return;
			}
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Loop_Logic(0.2)))
				return;
		}
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Fly(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		if (getSelfCombinePoint() >= 13) {
			if (changeState(state -> Damage_Stun(0.2)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x67695F37L /*Lost_Target*/);
		if (getVariable(0xBE464C69L /*_isFA*/) == 0) {
			if (changeState(state -> Lost_Target_Ground(0.1)))
				return;
		}
		if (getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> Lost_Target_Fly(0.1)))
				return;
		}
		changeState(state -> Lost_Target_Ground(blendTime));
	}

	protected void Lost_Target_Ground(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6AC0C2A3L /*Lost_Target_Ground*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Lost_Target_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1241502L /*Lost_Target_Fly*/);
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_Fly(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		if (getVariable(0xBE464C69L /*_isFA*/) == 0) {
			if (changeState(state -> Move_Return_Ground(0.1)))
				return;
		}
		if (getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> Move_Return_Fly(0.1)))
				return;
		}
		changeState(state -> Move_Return_Ground(blendTime));
	}

	protected void Move_Return_Ground(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x7045FF45L /*Move_Return_Ground*/);
		clearAggro(true);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x163A2D9CL /*Move_Return_Fly*/);
		clearAggro(true);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 1);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1260000894L /*MOVE_RETURN_FLY*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Fly(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 0) {
			if (changeState(state -> Battle_Wait(0.1)))
				return;
		}
		if (getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 1) {
			if (changeState(state -> Battle_Wait_Fly(0.1)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x80CB99B0L /*FailFindPath*/);
		if (getVariable(0xBE464C69L /*_isFA*/) == 0) {
			if (changeState(state -> FailFindPath_Ground(0.1)))
				return;
		}
		if (getVariable(0xBE464C69L /*_isFA*/) == 1) {
			if (changeState(state -> FailFindPath_Air(0.1)))
				return;
		}
		changeState(state -> FailFindPath_Ground(blendTime));
	}

	protected void FailFindPath_Ground(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x149CDA59L /*FailFindPath_Ground*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 0);
		setVariable(0x80F9A984L /*_FallCountDown*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void FailFindPath_Air(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD4B76ECCL /*FailFindPath_Air*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5D97A0C3L /*_isFlyMode*/, 1);
		setVariable(0x80F9A984L /*_FallCountDown*/, 0);
		setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_Fly(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		setVariable(0x5D97A0C3L /*_IsFlyMode*/, 0);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.2)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.2)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0xE04DD125L /*_FlyCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 0) {
			if (changeState(state -> Fly_Up(0.2)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0xE04DD125L /*_FlyCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 0) {
			if (changeState(state -> Fly_Up(0.2)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 25 && getVariable(0xE04DD125L /*_FlyCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 0) {
			if (changeState(state -> Fly_Up(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack1(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack2(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 2300)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack3(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1400) && target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= 135) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack4(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2400)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_RangeAttack1(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2700) && getVariable(0x3F487035L /*_Hp*/) <= 50) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_RangeAttack2(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1300) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Fly_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x14570A65L /*Fly_Up*/);
		doAction(2635306089L /*FLYUP*/, blendTime, onDoActionEnd -> {
			setVariable(0xE04DD125L /*_FlyCountDown*/, 0);
			scheduleState(state -> Battle_Wait_Fly(blendTime), 1000);
		});
	}

	protected void Fly_Down_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x53E84396L /*Fly_Down_End*/);
		doAction(1745964477L /*FLYDOWN_B*/, blendTime, onDoActionEnd -> {
			setVariable(0x5D97A0C3L /*_IsFlyMode*/, 1);
			changeState(state -> Wait(blendTime));
		});
	}

	protected void Battle_Wait_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD63B64BL /*Battle_Wait_Fly*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target_Fly(0.2)))
				return;
		}
		if (getDistanceToSpawn() >= 7000) {
			if (changeState(state -> Move_Return_Fly(0.2)))
				return;
		}
		if (getVariable(0x80F9A984L /*_FallCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 1) {
			if (changeState(state -> Fall_Down(0.2)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 600 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_FlyAttack1(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_FlyAttack2(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> Chaser_Fly(0.2)))
				return;
		}
		doAction(1627856917L /*BATTLE_WAIT_FLY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_Fly(blendTime), 100));
	}

	protected void Fall_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x39346EB9L /*Fall_Down*/);
		doAction(3700281990L /*FLYUP_B*/, blendTime, onDoActionEnd -> {
			setVariable(0x80F9A984L /*_FallCountDown*/, 0);
			scheduleState(state -> Battle_Wait(blendTime), 5000);
		});
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
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack1(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack2(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 2300)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack3(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1400) && target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= 135) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack4(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2400)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_RangeAttack1(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2700) && getVariable(0x3F487035L /*_Hp*/) <= 40) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_RangeAttack2(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 1300) {
			if (changeState(state -> Chaser_Run(0.3)))
				return;
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
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
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
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack1(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Battle_Attack2(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 1300 && getDistanceToTarget(target, false) <= 2300)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack3(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 1400) && target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= 135) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack4(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2400)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_RangeAttack1(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2700) && getVariable(0x3F487035L /*_Hp*/) <= 40) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_RangeAttack2(0.2)))
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
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Chaser_Fly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xF29B5BE1L /*Chaser_Fly*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target_Fly(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return_Fly(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 700 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_FlyAttack1(0.2)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2000)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_FlyAttack2(0.2)))
					return;
			}
		}
		if (getVariable(0x80F9A984L /*_FallCountDown*/) >= 50 && getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 1) {
			if (changeState(state -> Fall_Down(0.2)))
				return;
		}
		doAction(2221527650L /*CHASER_FLY*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return_Fly(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Fly(blendTime), 1000)));
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

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF05721A5L /*Battle_Attack4*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_FlyAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDF165162L /*Battle_FlyAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		doAction(293048019L /*BATTLE_ATTACK_FLY1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Battle_FlyAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x834F1F96L /*Battle_FlyAttack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target_Fly(blendTime)))
				return;
		}
		doAction(392461258L /*BATTLE_ATTACK_FLY2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Fly(blendTime)));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetHp(target) > 0 && getState() == 0x9B7BFA94L /*Wait_Fly*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait_Fly(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 1 && getVariable(0x80F9A984L /*_FallCountDown*/) < 51) {
			setVariable(0x80F9A984L /*_FallCountDown*/, getVariable(0x80F9A984L /*_FallCountDown*/) + 1);
		}
		if (getVariable(0x5D97A0C3L /*_IsFlyMode*/) == 0) {
			setVariable(0xE04DD125L /*_FlyCountDown*/, getVariable(0xE04DD125L /*_FlyCountDown*/) + 1);
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x9B7BFA94L /*Wait_Fly*/) {
			if (changeState(state -> Battle_Wait_Fly(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
