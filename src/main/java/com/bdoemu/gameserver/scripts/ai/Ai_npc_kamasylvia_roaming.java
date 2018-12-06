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
@IAIName("npc_kamasylvia_roaming")
public class Ai_npc_kamasylvia_roaming extends CreatureAI {
	public Ai_npc_kamasylvia_roaming(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0xD43EFDE8L /*_RandomMovePoint*/, 0);
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> Die_Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xE60F92ADL /*_MinMove*/, 100);
		setVariable(0xF7AC801L /*_MaxMove*/, 400);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 10000));
	}

	protected void Select_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC5E05D2L /*Select_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(315));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1324 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1338 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1339 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1354 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1355 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1356 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1534 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1536 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1537 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1538 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1539 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1540 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1544 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1545 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1546 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1551 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 1552 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40671 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40681 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40691 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40701 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40711 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40751 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40761 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40771 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40781 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40791 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40801 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40811 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40821 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40831 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40841 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40851 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40871 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40881 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40891 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40901 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40911 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40921 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40931 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40951 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40961 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40971 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40981 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40991 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41001 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41011 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41021 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41031 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41041 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41051 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41061 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41071 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41081 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 60 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 61) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41091 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 61 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 62) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41101 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 62 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 63) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41111 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 63 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 64) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41121 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 64 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 65) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41131 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 65 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 66) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41141 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 66 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 67) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41151 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 67 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 68) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41161 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 68 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 69) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41171 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 69 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 70) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45391 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 70 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 71) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45401 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 71 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 72) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45411 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 72 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 73) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45421 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 73 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 74) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45431 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 74 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 75) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45441 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 75 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 76) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45451 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 76 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 77) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45461 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 77 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 78) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45471 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 78 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 79) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45481 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 79 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 80) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45491 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 80 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 81) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45501 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 81 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 82) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45511 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 82 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 83) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45521 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 83 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 84) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45531 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 84 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 85) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45541 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 85 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 86) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45551 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 86 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 87) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45561 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 87 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 88) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45571 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 88 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 89) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45581 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 89 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 90) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45591 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 90 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 91) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45601 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 91 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 92) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45611 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 92 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 93) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45621 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 93 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 94) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45631 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 94 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 95) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45641 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 95 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 96) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45651 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 96 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 97) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45661 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 97 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 98) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45671 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 98 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 99) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45681 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 99 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 100) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45691 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 100 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 101) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45701 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 101 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 102) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45711 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 102 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 103) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 103 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 104) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 104 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 105) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 105 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 106) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45751 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 106 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 107) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45761 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 107 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 108) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45771 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 108 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 109) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45781 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 109 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 110) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45791 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 110 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 111) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45801 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 111 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 112) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45811 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 112 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 113) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45821 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 113 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 114) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45831 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 114 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 115) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45841 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 115 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 116) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45851 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 116 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 117) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 117 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 118) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45871 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 118 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 119) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45881 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 119 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 120) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45891 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 120 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 121) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45901 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 121 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 122) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45911 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 122 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 123) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45921 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 123 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 124) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45931 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 124 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 125) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 125 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 126) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45951 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 126 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 127) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45961 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 127 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 128) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45971 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 128 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 129) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45981 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 129 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 130) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45991 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 130 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 131) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46001 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 131 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 132) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46011 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 132 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 133) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46021 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 133 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 134) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46031 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 134 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 135) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46041 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 135 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 136) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46051 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 136 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 137) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46061 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 137 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 138) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46071 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 138 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 139) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46081 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 139 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 140) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46091 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 140 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 141) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46101 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 141 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 142) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46111 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 142 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 143) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46121 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 143 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 144) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46131 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 144 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 145) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46141 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 145 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 146) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46151 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 146 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 147) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46161 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 147 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 148) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46171 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 148 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 149) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46181 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 149 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 150) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46191 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 150 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 151) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46201 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 151 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 152) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46211 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 152 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 153) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46221 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 153 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 154) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46231 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 154 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 155) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46241 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 155 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 156) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46251 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 156 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 157) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46261 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 157 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 158) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46271 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 158 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 159) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46281 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 159 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 160) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46291 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 160 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 161) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46301 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 161 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 162) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46311 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 162 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 163) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46321 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 163 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 164) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46331 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 164 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 165) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46341 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 165 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 166) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46351 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 166 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 167) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46361 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 167 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 168) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46371 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 168 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 169) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46381 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 169 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 170) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46391 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 170 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 171) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46401 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 171 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 172) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46411 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 172 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 173) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46421 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 173 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 174) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46431 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 174 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 175) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46441 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 175 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 176) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46451 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 176 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 177) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46461 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 177 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 178) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46471 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 178 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 179) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46481 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 179 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 180) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46491 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 180 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 181) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46501 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 181 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 182) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46511 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 182 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 183) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46521 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 183 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 184) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46531 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 184 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 185) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46541 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 185 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 186) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46551 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 186 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 187) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46561 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 187 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 188) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46571 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 188 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 189) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46581 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 189 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 190) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46591 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 190 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 191) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46601 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 191 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 192) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46611 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 192 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 193) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46621 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 193 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 194) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46631 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 194 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 195) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46641 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 195 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 196) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46651 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 196 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 197) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46661 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 197 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 198) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46671 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 198 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 199) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46681 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 199 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 200) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46691 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 200 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 201) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46701 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 201 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 202) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46711 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 202 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 203) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 203 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 204) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 204 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 205) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 205 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 206) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46751 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 206 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 207) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46761 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 207 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 208) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46771 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 208 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 209) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46781 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 209 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 210) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46791 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 210 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 211) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46801 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 211 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 212) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46811 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 212 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 213) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46821 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 213 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 214) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46831 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 214 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 215) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46841 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 215 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 216) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46851 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 216 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 217) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 217 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 218) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46871 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 218 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 219) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46881 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 219 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 220) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46891 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 220 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 221) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46901 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 221 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 222) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46911 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 222 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 223) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46921 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 223 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 224) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46931 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 224 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 225) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 225 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 226) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46951 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 226 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 227) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46961 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 227 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 228) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46971 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 228 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 229) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46981 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 229 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 230) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46991 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 230 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 231) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47001 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 231 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 232) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47011 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 232 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 233) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47021 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 233 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 234) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47051 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 234 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 235) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47061 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 235 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 236) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47071 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 236 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 237) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47081 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 237 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 238) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47091 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 238 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 239) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47101 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 239 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 240) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47111 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 240 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 241) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47121 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 241 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 242) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47131 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 242 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 243) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47141 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 243 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 244) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47151 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 244 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 245) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47161 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 245 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 246) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47171 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 246 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 247) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47181 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 247 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 248) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47191 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 248 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 249) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47201 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 249 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 250) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47211 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 250 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 251) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47221 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 251 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 252) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47231 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 252 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 253) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47901 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 253 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 254) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47911 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 254 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 255) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47921 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 255 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 256) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47931 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 256 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 257) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 257 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 258) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47951 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 258 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 259) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47961 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 259 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 260) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47971 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 260 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 261) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47981 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 261 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 262) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47991 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 262 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 263) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 48001 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 263 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 264) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 48011 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 264 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 265) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 55731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 265 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 266) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56171 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 266 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 267) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56181 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 267 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 268) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56191 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000 + Rnd.get(-500,500)));
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
				if (changeState(state -> Die_Wait(0.3)))
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 400, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Die_Wait(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_for_Talk(blendTime), 15000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
