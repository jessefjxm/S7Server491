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
@IAIName("npc_neutral_zone_moving_store")
public class Ai_npc_neutral_zone_moving_store extends CreatureAI {
	public Ai_npc_neutral_zone_moving_store(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xD43EFDE8L /*_RandomMovePoint*/, 0);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 0);
		setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, 0);
		setVariable(0xBA6091EBL /*_FailFindPathCount01*/, 0);
		setVariable(0x6FB96667L /*_isFindPathCompleted02*/, 0);
		setVariable(0x2E7219D7L /*_FailFindPathCount02*/, 0);
		setVariable(0xE5352AC4L /*_isFindPathCompleted03*/, 0);
		setVariable(0x604BC89L /*_FailFindPathCount03*/, 0);
		setVariable(0x92B3F040L /*_isFindPathCompleted04*/, 0);
		setVariable(0x5D35261DL /*_FailFindPathCount04*/, 0);
		setVariable(0xB9A20464L /*_isFindPathCompleted05*/, 0);
		setVariable(0x16217E0FL /*_FailFindPathCount05*/, 0);
		setVariable(0x4B24F60DL /*_isFindPathCompleted06*/, 0);
		setVariable(0x6F38AC53L /*_FailFindPathCount06*/, 0);
		setVariable(0x8E7BCB7FL /*_isFindPathCompleted07*/, 0);
		setVariable(0x5C6C08E9L /*_FailFindPathCount07*/, 0);
		setVariable(0x81C9288AL /*_isFindPathCompleted08*/, 0);
		setVariable(0xAE8F6B45L /*_FailFindPathCount08*/, 0);
		setVariable(0x1A2F6B5FL /*_isFindPathCompleted09*/, 0);
		setVariable(0x77859E83L /*_FailFindPathCount09*/, 0);
		setVariable(0x9CB75743L /*_isFindPathCompleted10*/, 0);
		setVariable(0x4FCD6D4CL /*_FailFindPathCount10*/, 0);
		setVariable(0x7D1A69L /*_isFindPathCompleted11*/, 0);
		setVariable(0x828616DEL /*_FailFindPathCount11*/, 0);
		setVariable(0xD7D4593FL /*_isFindPathCompleted12*/, 0);
		setVariable(0xF2963EAFL /*_FailFindPathCount12*/, 0);
		setVariable(0xCD37A291L /*_isFindPathCompleted13*/, 0);
		setVariable(0x45D3A83DL /*_FailFindPathCount13*/, 0);
		setVariable(0x35E4EF98L /*_isFindPathCompleted14*/, 0);
		setVariable(0xCDEF6BE5L /*_FailFindPathCount14*/, 0);
		setVariable(0x4E30E63FL /*_isFindPathCompleted15*/, 0);
		setVariable(0x8BE027AAL /*_FailFindPathCount15*/, 0);
		setVariable(0x5247A340L /*_isFindPathCompleted16*/, 0);
		setVariable(0x1427D731L /*_FailFindPathCount16*/, 0);
		setVariable(0x3CF9E17CL /*_isFindPathCompleted17*/, 0);
		setVariable(0xF4FAD659L /*_FailFindPathCount17*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_01(blendTime), 100000));
	}

	protected void Wait_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7C416C47L /*Wait_01*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_01(blendTime), 100000));
	}

	protected void Wait_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8684E98CL /*Wait_02*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_02(blendTime), 100000));
	}

	protected void Wait_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8BC6E2FL /*Wait_03*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_03(blendTime), 100000));
	}

	protected void Wait_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1F9F3D8L /*Wait_04*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_04(blendTime), 100000));
	}

	protected void Wait_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x403B1BB4L /*Wait_05*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_05(blendTime), 100000));
	}

	protected void Wait_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF27A15F5L /*Wait_06*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_06(blendTime), 100000));
	}

	protected void Wait_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x746024CL /*Wait_07*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_07(blendTime), 100000));
	}

	protected void Wait_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBA6F4B91L /*Wait_08*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_08(blendTime), 100000));
	}

	protected void Wait_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60F4A423L /*Wait_09*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_09(blendTime), 100000));
	}

	protected void Wait_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF85DF084L /*Wait_10*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_10(blendTime), 100000));
	}

	protected void Wait_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x187333E2L /*Wait_11*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_11(blendTime), 100000));
	}

	protected void Wait_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x14B935A9L /*Wait_12*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_12(blendTime), 100000));
	}

	protected void Wait_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17C6C60BL /*Wait_13*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_13(blendTime), 100000));
	}

	protected void Wait_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDC38D969L /*Wait_14*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_14(blendTime), 100000));
	}

	protected void Wait_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEBE25C18L /*Wait_15*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_15(blendTime), 100000));
	}

	protected void Wait_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF2EE669AL /*Wait_16*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_16(blendTime), 100000));
	}

	protected void Wait_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9CB5151EL /*Wait_17*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Waypoint_17(blendTime), 100000));
	}

	protected void Wait_Idle01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x20A30389L /*Wait_Idle01*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_02(blendTime), 100000));
	}

	protected void Wait_Idle02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1BA29E4FL /*Wait_Idle02*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_03(blendTime), 100000));
	}

	protected void Wait_Idle03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7BD99AC4L /*Wait_Idle03*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_04(blendTime), 100000));
	}

	protected void Wait_Idle04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x41C19FEBL /*Wait_Idle04*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_05(blendTime), 100000));
	}

	protected void Wait_Idle05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB0D45752L /*Wait_Idle05*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_06(blendTime), 100000));
	}

	protected void Wait_Idle06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD2DFFA55L /*Wait_Idle06*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_07(blendTime), 100000));
	}

	protected void Wait_Idle07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75A0DE5L /*Wait_Idle07*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_08(blendTime), 100000));
	}

	protected void Wait_Idle08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x61B7C2E6L /*Wait_Idle08*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_09(blendTime), 100000));
	}

	protected void Wait_Idle09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC104763BL /*Wait_Idle09*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_10(blendTime), 100000));
	}

	protected void Wait_Idle10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA5212947L /*Wait_Idle10*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_11(blendTime), 100000));
	}

	protected void Wait_Idle11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF9A69864L /*Wait_Idle11*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_12(blendTime), 100000));
	}

	protected void Wait_Idle12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF2EF22D2L /*Wait_Idle12*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_13(blendTime), 100000));
	}

	protected void Wait_Idle13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17EC3D1DL /*Wait_Idle13*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_14(blendTime), 100000));
	}

	protected void Wait_Idle14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4FE5631CL /*Wait_Idle14*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_15(blendTime), 100000));
	}

	protected void Wait_Idle15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3B7687E8L /*Wait_Idle15*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_16(blendTime), 100000));
	}

	protected void Wait_Idle16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBEEEC7DCL /*Wait_Idle16*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_17(blendTime), 100000));
	}

	protected void Wait_Idle17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4DD73E69L /*Wait_Idle17*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_01(blendTime), 100000));
	}

	protected void Go_Waypoint_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB12C9AEL /*Go_Waypoint_01*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 1);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_145", ENaviType.ground, () -> {
			setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, isFindPathCompleted());
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0 && getVariable(0xBA6091EBL /*_FailFindPathCount01*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0) {
				if (changeState(state -> FailFindPath_Logic01(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle01(blendTime), 5000)));
	}

	protected void Go_Waypoint_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7FDB5A6EL /*Go_Waypoint_02*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 2);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_173", ENaviType.ground, () -> {
			setVariable(0x6FB96667L /*_isFindPathCompleted02*/, isFindPathCompleted());
			if (getVariable(0x6FB96667L /*_isFindPathCompleted02*/) == 0 && getVariable(0x2E7219D7L /*_FailFindPathCount02*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x6FB96667L /*_isFindPathCompleted02*/) == 0) {
				if (changeState(state -> FailFindPath_Logic02(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle02(blendTime), 5000)));
	}

	protected void Go_Waypoint_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEAB9553L /*Go_Waypoint_03*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 3);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_155", ENaviType.ground, () -> {
			setVariable(0xE5352AC4L /*_isFindPathCompleted03*/, isFindPathCompleted());
			if (getVariable(0xE5352AC4L /*_isFindPathCompleted03*/) == 0 && getVariable(0x604BC89L /*_FailFindPathCount03*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0xE5352AC4L /*_isFindPathCompleted03*/) == 0) {
				if (changeState(state -> FailFindPath_Logic03(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle03(blendTime), 5000)));
	}

	protected void Go_Waypoint_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x86CC3B79L /*Go_Waypoint_04*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 4);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_137", ENaviType.ground, () -> {
			setVariable(0x92B3F040L /*_isFindPathCompleted04*/, isFindPathCompleted());
			if (getVariable(0x92B3F040L /*_isFindPathCompleted04*/) == 0 && getVariable(0x5D35261DL /*_FailFindPathCount04*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x92B3F040L /*_isFindPathCompleted04*/) == 0) {
				if (changeState(state -> FailFindPath_Logic04(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle04(blendTime), 5000)));
	}

	protected void Go_Waypoint_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xABA453E0L /*Go_Waypoint_05*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 5);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_098", ENaviType.ground, () -> {
			setVariable(0xB9A20464L /*_isFindPathCompleted05*/, isFindPathCompleted());
			if (getVariable(0xB9A20464L /*_isFindPathCompleted05*/) == 0 && getVariable(0x16217E0FL /*_FailFindPathCount05*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0xB9A20464L /*_isFindPathCompleted05*/) == 0) {
				if (changeState(state -> FailFindPath_Logic05(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle05(blendTime), 5000)));
	}

	protected void Go_Waypoint_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x480FD4CL /*Go_Waypoint_06*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 6);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_558", ENaviType.ground, () -> {
			setVariable(0x4B24F60DL /*_isFindPathCompleted06*/, isFindPathCompleted());
			if (getVariable(0x4B24F60DL /*_isFindPathCompleted06*/) == 0 && getVariable(0x6F38AC53L /*_FailFindPathCount06*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x4B24F60DL /*_isFindPathCompleted06*/) == 0) {
				if (changeState(state -> FailFindPath_Logic06(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle06(blendTime), 5000)));
	}

	protected void Go_Waypoint_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB0D1DE06L /*Go_Waypoint_07*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 7);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_518", ENaviType.ground, () -> {
			setVariable(0x8E7BCB7FL /*_isFindPathCompleted07*/, isFindPathCompleted());
			if (getVariable(0x8E7BCB7FL /*_isFindPathCompleted07*/) == 0 && getVariable(0x5C6C08E9L /*_FailFindPathCount07*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x8E7BCB7FL /*_isFindPathCompleted07*/) == 0) {
				if (changeState(state -> FailFindPath_Logic07(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle07(blendTime), 5000)));
	}

	protected void Go_Waypoint_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1455083FL /*Go_Waypoint_08*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 8);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_001", ENaviType.ground, () -> {
			setVariable(0x81C9288AL /*_isFindPathCompleted08*/, isFindPathCompleted());
			if (getVariable(0x81C9288AL /*_isFindPathCompleted08*/) == 0 && getVariable(0xAE8F6B45L /*_FailFindPathCount08*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x81C9288AL /*_isFindPathCompleted08*/) == 0) {
				if (changeState(state -> FailFindPath_Logic08(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle08(blendTime), 5000)));
	}

	protected void Go_Waypoint_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x44D06656L /*Go_Waypoint_09*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 9);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_009", ENaviType.ground, () -> {
			setVariable(0x1A2F6B5FL /*_isFindPathCompleted09*/, isFindPathCompleted());
			if (getVariable(0x1A2F6B5FL /*_isFindPathCompleted09*/) == 0 && getVariable(0x77859E83L /*_FailFindPathCount09*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x1A2F6B5FL /*_isFindPathCompleted09*/) == 0) {
				if (changeState(state -> FailFindPath_Logic09(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle09(blendTime), 5000)));
	}

	protected void Go_Waypoint_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7AFC0DD6L /*Go_Waypoint_10*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 10);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_037", ENaviType.ground, () -> {
			setVariable(0x9CB75743L /*_isFindPathCompleted10*/, isFindPathCompleted());
			if (getVariable(0x9CB75743L /*_isFindPathCompleted10*/) == 0 && getVariable(0x4FCD6D4CL /*_FailFindPathCount10*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x9CB75743L /*_isFindPathCompleted10*/) == 0) {
				if (changeState(state -> FailFindPath_Logic10(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle10(blendTime), 5000)));
	}

	protected void Go_Waypoint_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDE562249L /*Go_Waypoint_11*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 11);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_025", ENaviType.ground, () -> {
			setVariable(0x7D1A69L /*_isFindPathCompleted11*/, isFindPathCompleted());
			if (getVariable(0x7D1A69L /*_isFindPathCompleted11*/) == 0 && getVariable(0x828616DEL /*_FailFindPathCount11*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x7D1A69L /*_isFindPathCompleted11*/) == 0) {
				if (changeState(state -> FailFindPath_Logic11(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle11(blendTime), 5000)));
	}

	protected void Go_Waypoint_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x346C040DL /*Go_Waypoint_12*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 12);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_009", ENaviType.ground, () -> {
			setVariable(0xD7D4593FL /*_isFindPathCompleted12*/, isFindPathCompleted());
			if (getVariable(0xD7D4593FL /*_isFindPathCompleted12*/) == 0 && getVariable(0xF2963EAFL /*_FailFindPathCount12*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0xD7D4593FL /*_isFindPathCompleted12*/) == 0) {
				if (changeState(state -> FailFindPath_Logic12(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle12(blendTime), 5000)));
	}

	protected void Go_Waypoint_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F9B33E9L /*Go_Waypoint_13*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 13);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_001", ENaviType.ground, () -> {
			setVariable(0xCD37A291L /*_isFindPathCompleted13*/, isFindPathCompleted());
			if (getVariable(0xCD37A291L /*_isFindPathCompleted13*/) == 0 && getVariable(0x45D3A83DL /*_FailFindPathCount13*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0xCD37A291L /*_isFindPathCompleted13*/) == 0) {
				if (changeState(state -> FailFindPath_Logic13(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle13(blendTime), 5000)));
	}

	protected void Go_Waypoint_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE26F41BL /*Go_Waypoint_14*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 14);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_518", ENaviType.ground, () -> {
			setVariable(0x35E4EF98L /*_isFindPathCompleted14*/, isFindPathCompleted());
			if (getVariable(0x35E4EF98L /*_isFindPathCompleted14*/) == 0 && getVariable(0xCDEF6BE5L /*_FailFindPathCount14*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x35E4EF98L /*_isFindPathCompleted14*/) == 0) {
				if (changeState(state -> FailFindPath_Logic14(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle14(blendTime), 5000)));
	}

	protected void Go_Waypoint_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2EE9AD0FL /*Go_Waypoint_15*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 15);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_558", ENaviType.ground, () -> {
			setVariable(0x4E30E63FL /*_isFindPathCompleted15*/, isFindPathCompleted());
			if (getVariable(0x4E30E63FL /*_isFindPathCompleted15*/) == 0 && getVariable(0x8BE027AAL /*_FailFindPathCount15*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x4E30E63FL /*_isFindPathCompleted15*/) == 0) {
				if (changeState(state -> FailFindPath_Logic15(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle15(blendTime), 5000)));
	}

	protected void Go_Waypoint_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8C64759FL /*Go_Waypoint_16*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 16);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_098", ENaviType.ground, () -> {
			setVariable(0x5247A340L /*_isFindPathCompleted16*/, isFindPathCompleted());
			if (getVariable(0x5247A340L /*_isFindPathCompleted16*/) == 0 && getVariable(0x1427D731L /*_FailFindPathCount16*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x5247A340L /*_isFindPathCompleted16*/) == 0) {
				if (changeState(state -> FailFindPath_Logic16(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle16(blendTime), 5000)));
	}

	protected void Go_Waypoint_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x86A5E46L /*Go_Waypoint_17*/);
		setVariable(0x43493AE5L /*_SelectedWaypointValue*/, 17);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(neutral_zone)_137", ENaviType.ground, () -> {
			setVariable(0x3CF9E17CL /*_isFindPathCompleted17*/, isFindPathCompleted());
			if (getVariable(0x3CF9E17CL /*_isFindPathCompleted17*/) == 0 && getVariable(0xF4FAD659L /*_FailFindPathCount17*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x3CF9E17CL /*_isFindPathCompleted17*/) == 0) {
				if (changeState(state -> FailFindPath_Logic17(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle17(blendTime), 5000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 700, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_for_Talk(blendTime), 15000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Continue_Move(blendTime), 100000));
	}

	protected void Continue_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1633919DL /*Continue_Move*/);
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 1) {
			if (changeState(state -> Go_Waypoint_01(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 2) {
			if (changeState(state -> Go_Waypoint_02(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 3) {
			if (changeState(state -> Go_Waypoint_03(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 4) {
			if (changeState(state -> Go_Waypoint_04(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 5) {
			if (changeState(state -> Go_Waypoint_05(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 6) {
			if (changeState(state -> Go_Waypoint_06(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 7) {
			if (changeState(state -> Go_Waypoint_07(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 8) {
			if (changeState(state -> Go_Waypoint_08(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 9) {
			if (changeState(state -> Go_Waypoint_09(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 10) {
			if (changeState(state -> Go_Waypoint_10(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 11) {
			if (changeState(state -> Go_Waypoint_11(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 12) {
			if (changeState(state -> Go_Waypoint_12(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 13) {
			if (changeState(state -> Go_Waypoint_13(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 14) {
			if (changeState(state -> Go_Waypoint_14(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 15) {
			if (changeState(state -> Go_Waypoint_15(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 16) {
			if (changeState(state -> Go_Waypoint_16(blendTime)))
				return;
		}
		if (getVariable(0x43493AE5L /*_SelectedWaypointValue*/) == 17) {
			if (changeState(state -> Go_Waypoint_17(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Continue_Move(blendTime), 2000));
	}

	protected void Summon_Servant(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x89DB3AA9L /*Summon_Servant*/);
		doAction(2701506524L /*SUMMON_SERVANT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetCharacterKey(target) == 60014) {
				createParty(1, 1);
			}
			changeState(state -> Wait(blendTime));
		});
	}

	protected void FailFindPath_Logic01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3F8799ECL /*FailFindPath_Logic01*/);
		setVariable(0xBA6091EBL /*_FailFindPathCount01*/, getVariable(0xBA6091EBL /*_FailFindPathCount01*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_01(blendTime)));
	}

	protected void FailFindPath_Logic02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC4874394L /*FailFindPath_Logic02*/);
		setVariable(0x2E7219D7L /*_FailFindPathCount02*/, getVariable(0x2E7219D7L /*_FailFindPathCount02*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_02(blendTime)));
	}

	protected void FailFindPath_Logic03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4DB79FBDL /*FailFindPath_Logic03*/);
		setVariable(0x604BC89L /*_FailFindPathCount03*/, getVariable(0x604BC89L /*_FailFindPathCount03*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_03(blendTime)));
	}

	protected void FailFindPath_Logic04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x21137833L /*FailFindPath_Logic04*/);
		setVariable(0x5D35261DL /*_FailFindPathCount04*/, getVariable(0x5D35261DL /*_FailFindPathCount04*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_04(blendTime)));
	}

	protected void FailFindPath_Logic05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6A565C9L /*FailFindPath_Logic05*/);
		setVariable(0x16217E0FL /*_FailFindPathCount05*/, getVariable(0x16217E0FL /*_FailFindPathCount05*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_05(blendTime)));
	}

	protected void FailFindPath_Logic06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEB868B8EL /*FailFindPath_Logic06*/);
		setVariable(0x6F38AC53L /*_FailFindPathCount06*/, getVariable(0x6F38AC53L /*_FailFindPathCount06*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_06(blendTime)));
	}

	protected void FailFindPath_Logic07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x624274F5L /*FailFindPath_Logic07*/);
		setVariable(0x5C6C08E9L /*_FailFindPathCount07*/, getVariable(0x5C6C08E9L /*_FailFindPathCount07*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_07(blendTime)));
	}

	protected void FailFindPath_Logic08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x18647B69L /*FailFindPath_Logic08*/);
		setVariable(0xAE8F6B45L /*_FailFindPathCount08*/, getVariable(0xAE8F6B45L /*_FailFindPathCount08*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_08(blendTime)));
	}

	protected void FailFindPath_Logic09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1E5D5913L /*FailFindPath_Logic09*/);
		setVariable(0x77859E83L /*_FailFindPathCount09*/, getVariable(0x77859E83L /*_FailFindPathCount09*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_09(blendTime)));
	}

	protected void FailFindPath_Logic10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D6DC885L /*FailFindPath_Logic10*/);
		setVariable(0x4FCD6D4CL /*_FailFindPathCount10*/, getVariable(0x4FCD6D4CL /*_FailFindPathCount10*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_10(blendTime)));
	}

	protected void FailFindPath_Logic11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB1836FCBL /*FailFindPath_Logic11*/);
		setVariable(0x828616DEL /*_FailFindPathCount11*/, getVariable(0x828616DEL /*_FailFindPathCount11*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_11(blendTime)));
	}

	protected void FailFindPath_Logic12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7453B191L /*FailFindPath_Logic12*/);
		setVariable(0xF2963EAFL /*_FailFindPathCount12*/, getVariable(0xF2963EAFL /*_FailFindPathCount12*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_12(blendTime)));
	}

	protected void FailFindPath_Logic13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB4DF2FF4L /*FailFindPath_Logic13*/);
		setVariable(0x45D3A83DL /*_FailFindPathCount13*/, getVariable(0x45D3A83DL /*_FailFindPathCount13*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_13(blendTime)));
	}

	protected void FailFindPath_Logic14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC5EEFBF6L /*FailFindPath_Logic14*/);
		setVariable(0xCDEF6BE5L /*_FailFindPathCount14*/, getVariable(0xCDEF6BE5L /*_FailFindPathCount14*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_14(blendTime)));
	}

	protected void FailFindPath_Logic15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x28B00C8EL /*FailFindPath_Logic15*/);
		setVariable(0x8BE027AAL /*_FailFindPathCount15*/, getVariable(0x8BE027AAL /*_FailFindPathCount15*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_15(blendTime)));
	}

	protected void FailFindPath_Logic16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD47BD349L /*FailFindPath_Logic16*/);
		setVariable(0x1427D731L /*_FailFindPathCount16*/, getVariable(0x1427D731L /*_FailFindPathCount16*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_16(blendTime)));
	}

	protected void FailFindPath_Logic17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3A031A3BL /*FailFindPath_Logic17*/);
		setVariable(0xF4FAD659L /*_FailFindPathCount17*/, getVariable(0xF4FAD659L /*_FailFindPathCount17*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_17(blendTime)));
	}

	protected void MeshOffState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE73CD5DEL /*MeshOffState*/);
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> DeadNPC(blendTime), 100));
	}

	protected void DeadNPC(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xA215D08AL /*DeadNPC*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> DeadNPC(blendTime), 100));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
