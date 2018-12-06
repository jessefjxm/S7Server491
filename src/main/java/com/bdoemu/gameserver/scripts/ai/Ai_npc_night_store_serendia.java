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
@IAIName("npc_night_store_serendia")
public class Ai_npc_night_store_serendia extends CreatureAI {
	public Ai_npc_night_store_serendia(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(77));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12017 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12027 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12037 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12047 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12057 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12067 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12077 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12087 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12097 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12107 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12117 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12127 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12137 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12147 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12157 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12216 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12226 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12236 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12246 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12256 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12340 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12350 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12360 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12370 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12380 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12390 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12402 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12413 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12423 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12433 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12443 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12453 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12463 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12473 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12483 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12493 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12503 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12513 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12523 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12533 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12553 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12563 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12573 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12583 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12593 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12603 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12613 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12624 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12634 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12644 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12661 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12671 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12681 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12691 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12701 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12711 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12751 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 60 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 61) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12761 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 61 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 62) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12771 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 62 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 63) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12781 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 63 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 64) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12791 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 64 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 65) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12801 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 65 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 66) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12811 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 66 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 67) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12821 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 67 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 68) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12831 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 68 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 69) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12841 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 69 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 70) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12851 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 70 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 71) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 71 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 72) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12871 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 72 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 73) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12882 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 73 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 74) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13032 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 74 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 75) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13094 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 75 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 76) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13116 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 76 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 77) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13503 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 77 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 78) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13513 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 78 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 79) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13523 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 79 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 80) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13533 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 80 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 81) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13543 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 81 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 82) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13553 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 82 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 83) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13580 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 83 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 84) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13590 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 84 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 85) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13600 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 85 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 86) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13610 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 86 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 87) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13620 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 87 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 88) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13630 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 88 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 89) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13662 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 89 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 90) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13672 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 90 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 91) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13682 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 91 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 92) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13692 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 92 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 93) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13702 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 93 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 94) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13718 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 94 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 95) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13728 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 95 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 96) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13738 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 96 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 97) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13748 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 97 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 98) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 13758 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 98 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 99) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 16298 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 99 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 100) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18568 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 100 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 101) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18578 });
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
