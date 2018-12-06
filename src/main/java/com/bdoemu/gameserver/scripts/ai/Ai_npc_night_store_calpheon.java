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
@IAIName("npc_night_store_calpheon")
public class Ai_npc_night_store_calpheon extends CreatureAI {
	public Ai_npc_night_store_calpheon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
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
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xE60F92ADL /*_MinMove*/, 50);
		setVariable(0xF7AC801L /*_MaxMove*/, 200);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Select_Position(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Char_Hide(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Select_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC5E05D2L /*Select_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(107));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13879 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13899 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13919 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13939 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13959 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13980 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14000 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14020 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14050 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14070 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14090 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14110 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14130 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14177 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14307 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14327 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14347 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14376 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14400 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14421 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14441 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14510 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14534 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14556 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14590 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14615 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14635 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14655 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14690 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14710 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14737 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14757 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14777 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14797 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14941 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14986 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15007 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15027 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15047 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15067 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15087 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15107 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15127 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15147 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15167 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15187 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15207 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15227 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15258 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15289 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15321 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15341 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15426 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 17474 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 17494 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 17515 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 17535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 17555 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18309 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18329 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 60 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 61) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18407 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 61 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 62) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18427 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 62 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 63) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18447 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 63 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 64) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18538 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 64 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 65) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18558 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 65 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 66) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18639 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 66 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 67) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18659 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 67 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 68) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18679 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 68 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 69) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18699 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 69 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 70) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18719 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 70 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 71) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18739 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 71 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 72) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18759 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 72 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 73) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18779 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 73 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 74) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18799 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 74 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 75) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18819 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 75 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 76) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18839 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 76 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 77) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18859 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 77 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 78) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18879 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 78 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 79) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18899 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 79 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 80) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18962 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 80 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 81) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18982 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 81 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 82) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19002 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 82 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 83) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19114 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 83 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 84) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19134 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 84 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 85) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19154 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 85 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 86) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19174 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 86 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 87) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19194 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 87 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 88) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19214 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 88 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 89) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 19234 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 89 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 90) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 21318 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 90 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 91) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 22095 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 91 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 92) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 22748 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 92 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 93) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 22778 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 93 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 94) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 22896 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 94 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 95) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 22916 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 95 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 96) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25526 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 96 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 97) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25546 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 97 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 98) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25566 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 98 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 99) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25586 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 99 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 100) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25606 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 100 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 101) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25690 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 101 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 102) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25710 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 102 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 103) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25730 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 103 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 104) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 25750 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 104 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 105) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 26143 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 105 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 106) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 26163 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 106 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 107) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 33206 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		if(Rnd.getChance(20)) {
			if (changeState(state -> Wait_for_Talk(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Char_Hide(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 20000));
	}

	protected void Char_Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC407B856L /*Char_Hide*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Char_Hide(blendTime)))
				return;
		}
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> Char_Hide(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
