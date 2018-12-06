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
@IAIName("npc_altinova_roaming")
public class Ai_npc_altinova_roaming extends CreatureAI {
	public Ai_npc_altinova_roaming(Creature actor, Map<Long, Integer> aiVariables) {
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
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30276 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30278 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30280 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30288 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30290 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30292 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30294 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30296 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30298 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30300 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30302 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30304 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30306 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30308 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30310 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30312 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30314 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30347 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30349 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30351 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30353 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30355 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30357 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30359 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30361 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30363 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30365 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30367 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30374 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30376 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30383 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30385 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30387 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30389 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30391 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30393 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30395 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30397 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30399 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30401 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30403 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30405 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30407 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30422 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30424 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30426 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30428 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30430 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30432 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30434 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30436 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30438 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30440 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30442 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30444 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30446 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30448 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31218 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31220 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31228 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 60 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 61) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31230 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 61 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 62) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31232 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 62 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 63) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31234 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 63 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 64) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31236 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 64 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 65) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31238 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 65 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 66) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31240 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 66 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 67) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31242 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 67 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 68) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31244 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 68 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 69) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31246 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 69 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 70) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31248 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 70 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 71) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31251 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 71 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 72) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31254 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 72 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 73) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31256 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 73 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 74) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31258 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 74 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 75) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31260 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 75 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 76) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31262 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 76 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 77) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31264 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 77 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 78) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31266 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 78 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 79) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31268 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 79 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 80) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31355 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 80 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 81) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31357 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 81 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 82) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31359 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 82 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 83) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31361 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 83 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 84) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31363 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 84 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 85) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31365 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 85 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 86) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31367 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 86 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 87) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31369 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 87 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 88) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31371 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 88 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 89) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31373 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 89 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 90) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31375 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 90 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 91) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31377 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 91 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 92) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31379 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 92 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 93) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31381 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 93 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 94) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31383 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 94 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 95) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31385 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 95 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 96) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31387 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 96 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 97) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31389 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 97 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 98) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31391 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 98 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 99) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31393 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 99 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 100) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31395 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 100 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 101) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31397 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 101 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 102) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31399 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 102 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 103) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31401 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 103 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 104) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31403 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 104 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 105) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31405 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 105 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 106) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31411 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 106 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 107) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31413 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 107 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 108) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31416 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 108 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 109) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31418 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 109 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 110) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31420 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 110 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 111) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31422 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 111 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 112) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31424 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 112 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 113) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31436 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 113 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 114) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31438 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 114 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 115) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31440 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 115 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 116) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31442 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 116 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 117) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31444 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 117 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 118) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31446 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 118 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 119) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31448 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 119 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 120) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31450 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 120 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 121) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31452 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 121 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 122) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31454 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 122 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 123) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31456 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 123 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 124) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31458 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 124 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 125) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31460 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 125 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 126) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31462 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 126 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 127) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31464 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 127 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 128) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31466 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 128 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 129) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31468 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 129 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 130) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31470 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 130 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 131) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31472 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 131 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 132) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31474 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 132 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 133) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31476 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 133 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 134) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31479 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 134 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 135) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31481 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 135 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 136) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31483 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 136 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 137) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31485 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 137 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 138) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31487 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 138 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 139) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31489 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 139 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 140) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31491 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 140 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 141) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31493 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 141 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 142) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31495 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 142 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 143) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31497 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 143 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 144) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31499 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 144 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 145) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31501 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 145 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 146) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31503 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 146 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 147) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31505 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 147 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 148) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31507 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 148 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 149) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31509 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 149 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 150) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31511 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 150 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 151) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31513 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 151 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 152) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31515 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 152 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 153) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31517 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 153 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 154) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31519 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 154 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 155) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31521 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 155 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 156) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31523 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 156 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 157) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31525 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 157 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 158) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31527 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 158 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 159) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31529 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 159 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 160) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31531 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 160 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 161) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31533 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 161 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 162) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 162 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 163) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31537 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 163 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 164) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31539 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 164 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 165) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31541 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 165 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 166) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31543 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 166 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 167) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31545 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 167 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 168) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31547 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 168 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 169) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31549 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 169 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 170) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31551 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 170 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 171) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31553 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 171 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 172) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31555 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 172 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 173) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31557 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 173 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 174) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31559 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 174 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 175) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31561 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 175 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 176) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31563 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 176 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 177) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31565 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 177 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 178) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31567 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 178 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 179) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31569 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 179 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 180) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31571 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 180 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 181) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31573 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 181 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 182) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31584 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 182 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 183) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31633 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 183 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 184) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31635 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 184 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 185) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31637 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 185 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 186) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31639 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 186 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 187) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31642 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 187 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 188) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31644 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 188 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 189) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31651 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 189 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 190) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31653 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 190 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 191) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31655 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 191 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 192) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31657 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 192 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 193) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31659 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 193 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 194) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31661 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 194 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 195) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31663 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 195 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 196) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31665 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 196 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 197) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31667 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 197 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 198) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31669 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 198 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 199) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31671 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 199 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 200) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31673 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 200 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 201) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31675 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 201 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 202) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31677 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 202 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 203) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31679 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 203 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 204) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31681 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 204 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 205) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31683 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 205 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 206) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31685 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 206 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 207) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31687 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 207 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 208) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31689 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 208 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 209) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31691 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 209 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 210) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31693 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 210 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 211) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 211 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 212) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31697 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 212 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 213) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31699 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 213 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 214) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31701 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 214 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 215) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31703 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 215 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 216) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 216 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 217) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31707 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 217 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 218) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31710 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 218 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 219) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31714 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 219 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 220) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31716 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 220 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 221) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31718 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 221 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 222) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31720 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 222 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 223) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31723 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 223 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 224) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 224 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 225) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31729 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 225 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 226) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 226 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 227) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31733 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 227 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 228) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 228 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 229) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31737 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 229 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 230) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31739 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 230 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 231) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 231 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 232) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31743 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 232 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 233) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31745 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 233 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 234) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31747 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 234 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 235) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31749 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 235 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 236) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31751 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 236 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 237) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31753 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 237 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 238) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31755 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 238 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 239) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31757 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 239 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 240) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31759 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 240 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 241) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31761 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 241 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 242) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31763 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 242 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 243) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31765 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 243 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 244) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31767 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 244 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 245) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31769 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 245 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 246) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31771 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 246 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 247) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31773 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 247 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 248) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31775 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 248 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 249) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31779 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 249 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 250) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31784 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 250 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 251) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31786 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 251 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 252) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31788 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 252 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 253) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31790 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 253 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 254) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31792 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 254 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 255) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31794 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 255 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 256) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31796 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 256 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 257) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31798 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 257 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 258) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31800 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 258 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 259) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31802 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 259 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 260) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31804 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 260 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 261) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31806 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 261 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 262) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31808 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 262 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 263) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31810 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 263 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 264) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31812 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 264 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 265) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31816 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 265 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 266) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31818 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 266 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 267) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31820 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 267 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 268) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31822 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 268 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 269) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31824 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 269 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 270) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31826 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 270 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 271) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31828 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 271 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 272) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31830 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 272 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 273) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31832 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 273 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 274) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31834 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 274 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 275) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31836 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 275 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 276) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31838 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 276 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 277) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31840 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 277 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 278) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31844 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 278 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 279) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31846 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 279 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 280) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31848 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 280 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 281) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31850 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 281 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 282) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31852 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 282 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 283) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31854 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 283 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 284) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31858 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 284 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 285) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31860 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 285 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 286) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31862 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 286 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 287) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31864 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 287 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 288) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31866 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 288 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 289) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31868 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 289 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 290) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31870 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 290 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 291) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31872 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 291 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 292) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31874 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 292 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 293) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31876 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 293 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 294) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31878 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 294 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 295) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31880 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 295 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 296) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31882 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 296 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 297) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31884 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 297 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 298) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31886 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 298 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 299) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31888 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 299 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 300) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31890 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 300 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 301) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31892 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 301 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 302) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31894 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 302 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 303) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31896 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 303 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 304) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31898 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 304 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 305) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31900 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 305 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 306) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31902 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 306 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 307) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31904 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 307 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 308) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31906 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 308 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 309) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31908 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 309 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 310) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31910 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 310 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 311) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31912 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 311 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 312) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31914 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 312 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 313) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31916 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 313 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 314) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31918 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 314 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 315) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31920 });
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
