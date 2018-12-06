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
@IAIName("npc_labor_media_type1")
public class Ai_npc_labor_media_type1 extends CreatureAI {
	public Ai_npc_labor_media_type1(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(15));
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 20) {
			if (changeState(state -> Go_Type1(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 21) {
			if (changeState(state -> Go_Type2(blendTime)))
				return;
		}
		if (getVariable(0x15D7B726L /*WaypointRouteValue*/) == 22) {
			if (changeState(state -> Go_Type3(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8921 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8923 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8930 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8933 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8936 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8946 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8952 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8957 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8960 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8966 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8970 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8974 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8979 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 8982 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
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

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 50, 100, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_for_Talk(blendTime), 5000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 5000));
	}

	protected void Go_Type1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFB906767L /*Go_Type1*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_016", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Clear_Stuff_1(blendTime), 1000)));
	}

	protected void Clear_Stuff_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2BEC74BCL /*Clear_Stuff_1*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack_1(blendTime), 1000));
	}

	protected void GoBack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC3E1F099L /*GoBack_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_020", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack_1(blendTime), 1000)));
	}

	protected void Rest_In_GoBack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x345DCBCDL /*Rest_In_GoBack_1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack1_1(blendTime), 1000));
	}

	protected void GoBack1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1F8AF3A6L /*GoBack1_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_027", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack1_1(blendTime), 1000)));
	}

	protected void Rest_In_GoBack1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x93F72C3BL /*Rest_In_GoBack1_1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack2_1(blendTime), 1000));
	}

	protected void GoBack2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x18A93D82L /*GoBack2_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_033", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack2_1(blendTime), 1000)));
	}

	protected void Rest_In_GoBack2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x392A4664L /*Rest_In_GoBack2_1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack3_1(blendTime), 1000));
	}

	protected void GoBack3_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAE6AEE27L /*GoBack3_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_035", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack3_1(blendTime), 1000)));
	}

	protected void Rest_In_GoBack3_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF3323250L /*Rest_In_GoBack3_1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack4_1(blendTime), 1000));
	}

	protected void GoBack4_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6A0E392CL /*GoBack4_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_01_001", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Load_Stuff_1(blendTime), 1000)));
	}

	protected void Load_Stuff_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD064532CL /*Load_Stuff_1*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Type1(blendTime), 1000));
	}

	protected void Go_Type2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2357B84DL /*Go_Type2*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_017", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Clear_Stuff_2(blendTime), 1000)));
	}

	protected void Clear_Stuff_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC26AC7A9L /*Clear_Stuff_2*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack_2(blendTime), 1000));
	}

	protected void GoBack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAE554187L /*GoBack_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_021", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack_2(blendTime), 1000)));
	}

	protected void Rest_In_GoBack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDCAAF950L /*Rest_In_GoBack_2*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack1_2(blendTime), 1000));
	}

	protected void GoBack1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8CA3CEAEL /*GoBack1_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_025", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack1_2(blendTime), 1000)));
	}

	protected void Rest_In_GoBack1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x190466E3L /*Rest_In_GoBack1_2*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack2_2(blendTime), 1000));
	}

	protected void GoBack2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7AA571C9L /*GoBack2_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_031", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack2_2(blendTime), 1000)));
	}

	protected void Rest_In_GoBack2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x86150701L /*Rest_In_GoBack2_2*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack3_2(blendTime), 1000));
	}

	protected void GoBack3_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF94569CCL /*GoBack3_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_038", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack3_2(blendTime), 1000)));
	}

	protected void Rest_In_GoBack3_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9EBB6ADEL /*Rest_In_GoBack3_2*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack4_2(blendTime), 1000));
	}

	protected void GoBack4_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11EF7EA2L /*GoBack4_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_02_001", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Load_Stuff_2(blendTime), 1000)));
	}

	protected void Load_Stuff_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5824A430L /*Load_Stuff_2*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Type2(blendTime), 1000));
	}

	protected void Go_Type3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9AA5BE4AL /*Go_Type3*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_022", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Clear_Stuff_3(blendTime), 1000)));
	}

	protected void Clear_Stuff_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE787C9ADL /*Clear_Stuff_3*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack_3(blendTime), 1000));
	}

	protected void GoBack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE595F435L /*GoBack_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_025", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack_3(blendTime), 1000)));
	}

	protected void Rest_In_GoBack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x26EC272L /*Rest_In_GoBack_3*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack1_3(blendTime), 1000));
	}

	protected void GoBack1_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E5409F6L /*GoBack1_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_028", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack1_3(blendTime), 1000)));
	}

	protected void Rest_In_GoBack1_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDEE6451AL /*Rest_In_GoBack1_3*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack2_3(blendTime), 1000));
	}

	protected void GoBack2_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x24BC80EBL /*GoBack2_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_029", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack2_3(blendTime), 1000)));
	}

	protected void Rest_In_GoBack2_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x248A76E7L /*Rest_In_GoBack2_3*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack3_3(blendTime), 1000));
	}

	protected void GoBack3_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5696C60BL /*GoBack3_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_033", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack3_3(blendTime), 1000)));
	}

	protected void Rest_In_GoBack3_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x64BD3C2FL /*Rest_In_GoBack3_3*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack4_3(blendTime), 1000));
	}

	protected void GoBack4_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFA5DBE6AL /*GoBack4_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Media_Labor_03_037", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Load_Stuff_3(blendTime), 1000)));
	}

	protected void Load_Stuff_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x52D96637L /*Load_Stuff_3*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go_Type3(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
