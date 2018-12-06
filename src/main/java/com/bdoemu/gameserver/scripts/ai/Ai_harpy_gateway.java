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
@IAIName("harpy_gateway")
public class Ai_harpy_gateway extends CreatureAI {
	public Ai_harpy_gateway(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x5FA18DB2L /*_AttackPoint*/, 0);
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 1000));
	}

	protected void Select_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC5E05D2L /*Select_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(42));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6437 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6438 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6439 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6440 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6441 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6442 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6443 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6444 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6445 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6446 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6447 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6448 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6449 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6450 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6452 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6453 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6454 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6460 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6461 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6462 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6463 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6464 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6465 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6466 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6467 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6468 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6469 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6476 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6477 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6478 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6479 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6480 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6481 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6482 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6483 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6484 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6485 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6486 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6487 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6488 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6489 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 6490 });
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Move_Position(blendTime)))
				return;
		}
		if (changeState(state -> MoveFast_Position(blendTime)))
			return;
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 2000 + Rnd.get(-500,500)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		doAction(1468881993L /*FLYING_BATTLEMOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_1(blendTime), 5000)));
	}

	protected void MoveFast_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9D21B173L /*MoveFast_Position*/);
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_1(blendTime), 5000)));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		setVariable(0x5FA18DB2L /*_AttackPoint*/, getRandom(5));
		if (getVariable(0x5FA18DB2L /*_AttackPoint*/) <= 1) {
			if (changeState(state -> Attack_Type_1(blendTime)))
				return;
		}
		if (getVariable(0x5FA18DB2L /*_AttackPoint*/) > 1 && getVariable(0x5FA18DB2L /*_AttackPoint*/) <= 2) {
			if (changeState(state -> Attack_Type_2(blendTime)))
				return;
		}
		if (getVariable(0x5FA18DB2L /*_AttackPoint*/) > 2 && getVariable(0x5FA18DB2L /*_AttackPoint*/) <= 3) {
			if (changeState(state -> Attack_Type_3(blendTime)))
				return;
		}
		if (getVariable(0x5FA18DB2L /*_AttackPoint*/) > 3 && getVariable(0x5FA18DB2L /*_AttackPoint*/) <= 4) {
			if (changeState(state -> Attack_Type_4(blendTime)))
				return;
		}
		if (getVariable(0x5FA18DB2L /*_AttackPoint*/) > 4 && getVariable(0x5FA18DB2L /*_AttackPoint*/) <= 5) {
			if (changeState(state -> Attack_Type_5(blendTime)))
				return;
		}
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 1000));
	}

	protected void Attack_Type_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19463B5CL /*Attack_Type_1*/);
		doAction(3216443173L /*ATTACK_TYPE_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Stone_Throw(blendTime)));
	}

	protected void Attack_Stone_Throw(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA59A4A7FL /*Attack_Stone_Throw*/);
		doAction(2332900584L /*ATTACK_STONE_THROW_READY*/, blendTime, onDoActionEnd -> changeState(state -> Select_Position(blendTime)));
	}

	protected void Attack_Type_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4E61D0DL /*Attack_Type_2*/);
		doAction(2534352963L /*ATTACK_TYPE_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000));
	}

	protected void Attack_Type_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3CC99FL /*Attack_Type_3*/);
		doAction(3454512047L /*ATTACK_TYPE_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000));
	}

	protected void Attack_Type_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE4143B9L /*Attack_Type_4*/);
		doAction(3032428707L /*ATTACK_TYPE_4*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000));
	}

	protected void Attack_Type_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F18D06DL /*Attack_Type_5*/);
		doAction(86107960L /*ATTACK_TYPE_5*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Select_Position(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 200));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Flying_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9526A4B9L /*Flying_Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -135 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if (changeState(state -> Flying_BattleTurn_Left_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 180) {
			if (changeState(state -> Flying_BattleTurn_Right_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Flying_BattleTurn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Flying_BattleTurn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Flying_BattleAround(0.4)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Attack_Move(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_AirRush(0.4)))
				return;
		}
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 500));
	}

	protected void Flying_BattleMove_Faster(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x7826CDC8L /*Flying_BattleMove_Faster*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Move(0.4)))
					return;
			}
		}
		if(Rnd.getChance(7)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove_Faster(blendTime), 100)));
	}

	protected void Flying_BattleMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5E29CD02L /*Flying_BattleMove*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 550) {
			if (changeState(state -> Flying_BattleMove_Faster(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Move(0.4)))
					return;
			}
		}
		doAction(1468881993L /*FLYING_BATTLEMOVE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove(blendTime), 100)));
	}

	protected void Flying_BattleMove_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x73D555B5L /*Flying_BattleMove_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if(Rnd.getChance(13)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		if(Rnd.getChance(8)) {
			if (changeState(state -> Attack_AirRush(0.4)))
				return;
		}
		doAction(3202605488L /*FLYING_BATTLEMOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove_Back(blendTime), 100)));
	}

	protected void Flying_BattleAround(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x43C5D4E6L /*Flying_BattleAround*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Move(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		doAction(948381961L /*FLYING_BATTLEAROUND*/, blendTime, onDoActionEnd -> moveAround(150 + Rnd.get(100, 250), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 100)));
	}

	protected void Flying_BattleTurn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9188E3DL /*Flying_BattleTurn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(2985732940L /*FLYING_BATTLETURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD4FA4B26L /*Flying_BattleTurn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(792653298L /*FLYING_BATTLETURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B613920L /*Flying_BattleTurn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(177101538L /*FLYING_BATTLETURN_180_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x383DA6E9L /*Flying_BattleTurn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(3419311486L /*FLYING_BATTLETURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x460B4E3AL /*Attack_Move*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2547671729L /*ATTACK_MOVE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Move_FlyingAway(blendTime)));
	}

	protected void Attack_Move_FlyingAway(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4B01B9A0L /*Attack_Move_FlyingAway*/);
		if(getCallCount() == 3) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 400, 500, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_Move_FlyingAway(blendTime), 1000)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3674084903L /*ATTACK_RANGE*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_AirRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94A873ABL /*Attack_AirRush*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(530541300L /*ATTACK_AIR_RUSH*/, blendTime, onDoActionEnd -> changeState(state -> Attack_AirRush_GoTarget(blendTime)));
	}

	protected void Attack_AirRush_GoTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5995B300L /*Attack_AirRush_GoTarget*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Attack_AirRush_Attack(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Attack_AirRush_Attack(0.3)))
				return;
		}
		doAction(3552497786L /*ATTACK_AIR_RUSH_ING*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_AirRush_GoTarget(blendTime), 1000)));
	}

	protected void Attack_AirRush_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x83545B9AL /*Attack_AirRush_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(856317372L /*ATTACK_AIR_RUSH_END*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xFF3E58ABL /*Move_Position*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x9D21B173L /*MoveFast_Position*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x96AB8779L /*Wait_1*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x19463B5CL /*Attack_Type_1*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xA59A4A7FL /*Attack_Stone_Throw*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x4E61D0DL /*Attack_Type_2*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xEC3CC99FL /*Attack_Type_3*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xAE4143B9L /*Attack_Type_4*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x6F18D06DL /*Attack_Type_5*/) {
			if (changeState(state -> Flying_Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Rigid(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
